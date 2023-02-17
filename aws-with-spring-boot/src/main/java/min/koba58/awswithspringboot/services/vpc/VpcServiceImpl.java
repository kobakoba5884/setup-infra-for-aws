package min.koba58.awswithspringboot.services.vpc;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.ec2.Ec2TagService;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateVpcRequest;
import software.amazon.awssdk.services.ec2.model.CreateVpcResponse;
import software.amazon.awssdk.services.ec2.model.DescribeVpcsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeVpcsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Service
@RequiredArgsConstructor
public class VpcServiceImpl implements VpcService {
    private final Ec2Client ec2Client;

    private final Ec2TagService tagService;

    // create vpc
    public String createVpc(String vpcName) {
        String sidrBlock = "10.0.0.0/16";

        CreateVpcRequest request = CreateVpcRequest.builder()
                .cidrBlock(sidrBlock)
                .build();

        try {
            CreateVpcResponse response = ec2Client.createVpc(request);

            String vpcId = response.vpc().vpcId();

            waitUntilVpcAvailable(vpcId);

            tagService.createTag(vpcId, vpcName);

            System.out.printf("Created VPC with ID %s\n", vpcId);

            return vpcId;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return "";
        }
    }

    private void waitUntilVpcAvailable(String vpcId) throws Ec2Exception{
        DescribeVpcsRequest request = DescribeVpcsRequest.builder()
                .vpcIds(vpcId)
                .build();
        
        WaiterResponse<DescribeVpcsResponse> waiter = ec2Client.waiter().waitUntilVpcAvailable(request);


        Optional<DescribeVpcsResponse> response = waiter.matched().response();

        if(response.isEmpty()){
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                .errorMessage("waitUntilVpcAvailable method is failed.").build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }

        String state = response.get().vpcs().get(0).stateAsString();

        System.out.println("vpc's (%s) state is %s.".formatted(vpcId, state));
    }
}
