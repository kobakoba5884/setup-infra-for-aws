package min.koba58.awswithspringboot.services.vpc.subnet;

import software.amazon.awssdk.services.ec2.model.CreateSubnetResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

public interface SubnetService {
    CreateSubnetResponse createSubnet(TagSpecification tagSpecification, String cidrBlock, String vpcId, String availabilityZoneId) throws Ec2Exception;
}
