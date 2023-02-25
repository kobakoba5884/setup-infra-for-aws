package min.koba58.awswithspringboot.services.vpc.vpcs;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import min.koba58.awswithspringboot.utils.SharedHandler;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateVpcRequest;
import software.amazon.awssdk.services.ec2.model.CreateVpcResponse;
import software.amazon.awssdk.services.ec2.model.DeleteVpcResponse;
import software.amazon.awssdk.services.ec2.model.DescribeVpcsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeVpcsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.Vpc;

@Service
@RequiredArgsConstructor
public class VpcServiceImpl implements VpcService {
    private final Ec2Client ec2Client;

    private final Ec2TagService tagService;

    private final SharedHandler sharedHandler;

    // create vpc
    public String createVpc(String vpcName, String sidrBlock) throws Ec2Exception {

        CreateVpcRequest request = CreateVpcRequest.builder()
                .cidrBlock(sidrBlock)
                .build();

        try {
            CreateVpcResponse response = ec2Client.createVpc(request);

            String vpcId = response.vpc().vpcId();

            waitUntilVpcAvailable(vpcId);

            tagService.createNameTag(vpcId, vpcName);

            System.out.printf("Created VPC with ID %s\n", vpcId);

            return vpcId;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    private void waitUntilVpcAvailable(String vpcId) throws Ec2Exception {
        DescribeVpcsRequest request = DescribeVpcsRequest.builder()
                .vpcIds(vpcId)
                .build();

        WaiterResponse<DescribeVpcsResponse> waiter = ec2Client.waiter().waitUntilVpcAvailable(request);

        Optional<DescribeVpcsResponse> response = waiter.matched().response();

        if (response.isEmpty()) {
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                    .errorMessage("waitUntilVpcAvailable method is failed.").build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }

        String state = response.get().vpcs().get(0).stateAsString();

        System.out.println("vpc's (%s) state is %s.".formatted(vpcId, state));
    }

    @Override
    public DeleteVpcResponse deleteVpcById(String vpcId) throws Ec2Exception {
        try {
            DeleteVpcResponse deleteVpcResponse = ec2Client.deleteVpc(request -> request.vpcId(vpcId).build());

            System.out.println("successfully deleted vpc (%s)".formatted(vpcId));

            return deleteVpcResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public DeleteVpcResponse deleteVpcByName(String vpcName) throws Ec2Exception {
        String vpcId = getVpcIdByName(vpcName);

        return deleteVpcById(vpcId);
    }

    @Override
    public String getVpcIdByName(String vpcName) throws Ec2Exception {
        Filter nameFilter = Filter.builder()
            .name("tag:Name")
            .values(vpcName)
            .build();

        DescribeVpcsRequest describeVpcsRequest = DescribeVpcsRequest.builder()
            .filters(nameFilter)
            .build();

        try{
            DescribeVpcsResponse describeVpcsResponse = ec2Client.describeVpcs(describeVpcsRequest);
        
            List<Vpc> vpcs = describeVpcsResponse.vpcs();

            sharedHandler.verifyIfResponseIsCorrect(vpcs, vpcName);

            return vpcs.get(0).vpcId();
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public List<Vpc> getVpcs() throws Ec2Exception {
        try {
            DescribeVpcsResponse describeVpcsResponse = ec2Client.describeVpcs();

            List<Vpc> vpcs = describeVpcsResponse.vpcs();

            vpcs.stream().forEach(vpc -> {
                System.out.println(vpc.toString());
            });

            return vpcs;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

}
