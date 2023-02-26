package min.koba58.awswithspringboot.services.vpc.elasticIp;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import min.koba58.awswithspringboot.services.vpc.az.AvailabilityZoneService;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AllocateAddressRequest;
import software.amazon.awssdk.services.ec2.model.AllocateAddressResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

@Service
@RequiredArgsConstructor
public class ElasticIpServiceImpl implements ElasticIpService {
    private final Ec2Client ec2Client;

    private final Ec2TagService ec2TagService;

    private final AvailabilityZoneService availabilityZoneService;

    @Override
    public AllocateAddressResponse allocateElasticIp(String elasticIpName) throws Ec2Exception {
        TagSpecification tagSpecification = ec2TagService.buildNameTagSpecification(elasticIpName,
                ResourceType.ELASTIC_IP);

        String networkBorderGroup = availabilityZoneService.getNetworkBorderGroup();

        AllocateAddressRequest addressRequest = AllocateAddressRequest.builder()
                .tagSpecifications(tagSpecification)
                .networkBorderGroup(networkBorderGroup)
                .build();

        try {
            AllocateAddressResponse allocateAddressResponse = ec2Client.allocateAddress(addressRequest);

            System.out.println();

            return allocateAddressResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

}
