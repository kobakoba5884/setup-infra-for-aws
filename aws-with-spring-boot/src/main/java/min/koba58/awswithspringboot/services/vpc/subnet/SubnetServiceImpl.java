package min.koba58.awswithspringboot.services.vpc.subnet;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateSubnetRequest;
import software.amazon.awssdk.services.ec2.model.CreateSubnetResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

@Service
@RequiredArgsConstructor
public class SubnetServiceImpl implements SubnetService{
    private final Ec2Client ec2Client;

    @Override
    public CreateSubnetResponse createSubnet(TagSpecification tagSpecification, String cidrBlock, String vpcId,
            String availabilityZoneId) throws Ec2Exception {

        CreateSubnetRequest createSubnetRequest = CreateSubnetRequest.builder()
            .cidrBlock(cidrBlock)
            .vpcId(vpcId)
            .tagSpecifications(tagSpecification)
            .availabilityZoneId(availabilityZoneId)
            .build();

        try{
            CreateSubnetResponse createSubnetResponse = ec2Client.createSubnet(createSubnetRequest);

            return createSubnetResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }


}
