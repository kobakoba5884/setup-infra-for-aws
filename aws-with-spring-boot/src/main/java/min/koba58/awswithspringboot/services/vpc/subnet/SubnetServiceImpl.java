package min.koba58.awswithspringboot.services.vpc.subnet;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.utils.SharedHandler;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateSubnetRequest;
import software.amazon.awssdk.services.ec2.model.CreateSubnetResponse;
import software.amazon.awssdk.services.ec2.model.DeleteSubnetRequest;
import software.amazon.awssdk.services.ec2.model.DeleteSubnetResponse;
import software.amazon.awssdk.services.ec2.model.DescribeSubnetsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeSubnetsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.Subnet;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

@Service
@RequiredArgsConstructor
public class SubnetServiceImpl implements SubnetService{
    private final Ec2Client ec2Client;

    private final SharedHandler sharedHandler;

    @Override
    public CreateSubnetResponse createSubnet(TagSpecification tagSpecification, String cidrBlock, String vpcId,
            String availabilityZoneName) throws Ec2Exception {

        CreateSubnetRequest createSubnetRequest = CreateSubnetRequest.builder()
            .cidrBlock(cidrBlock)
            .vpcId(vpcId)
            .tagSpecifications(tagSpecification)
            .availabilityZone(availabilityZoneName)
            .build();

        try{
            CreateSubnetResponse createSubnetResponse = ec2Client.createSubnet(createSubnetRequest);

            Subnet subnet = createSubnetResponse.subnet();

            String subnetId = subnet.subnetId();

            waitUntilSubnetAvailable(subnetId);

            System.out.println("successfully created subnet (%s)!!".formatted(subnetId));

            System.out.println(subnet);

            return createSubnetResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    private void waitUntilSubnetAvailable(String subnetId) throws Ec2Exception {
        DescribeSubnetsRequest describeSubnetsRequest = DescribeSubnetsRequest.builder()
            .subnetIds(subnetId)
            .build();

        WaiterResponse<DescribeSubnetsResponse> waiter = ec2Client.waiter().waitUntilSubnetAvailable(describeSubnetsRequest);

        Optional<DescribeSubnetsResponse> response = waiter.matched().response();

        if (response.isEmpty()) {
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                    .errorMessage("waitUntilSubnetAvailable method is failed.").build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }

        String state = response.get().subnets().get(0).stateAsString();

        System.out.println("vpc's (%s) state is %s.".formatted(subnetId, state));
    }

    @Override
    public DeleteSubnetResponse deleteSubnet(String subnetId) throws Ec2Exception {
        DeleteSubnetRequest deleteSubnetRequest = DeleteSubnetRequest.builder()
            .subnetId(subnetId)
            .build();

        try{
            DeleteSubnetResponse deleteSubnetResponse = ec2Client.deleteSubnet(deleteSubnetRequest);

            System.out.println("successfully deleted subnet by Id");

            return deleteSubnetResponse;
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public String getSubnetIdByName(String subnetName) throws Ec2Exception {
        Filter filter = Filter.builder()
            .name("tag:Name")
            .values(subnetName)
            .build();

        DescribeSubnetsRequest describeSubnetsRequest = DescribeSubnetsRequest.builder()
            .filters(filter)
            .build();

        try{
            DescribeSubnetsResponse describeSubnetsResponse = ec2Client.describeSubnets(describeSubnetsRequest);

            List<Subnet> subnets = describeSubnetsResponse.subnets();

            sharedHandler.verifyIfResponseIsCorrect(subnets, subnetName);

            return subnets.get(0).subnetId();
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }
}
