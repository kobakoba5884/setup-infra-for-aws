package min.koba58.awswithspringboot.services.vpc.subnet;

import software.amazon.awssdk.services.ec2.model.CreateSubnetResponse;
import software.amazon.awssdk.services.ec2.model.DeleteSubnetResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface SubnetService {
    CreateSubnetResponse createSubnet(String subnetName, String cidrBlock, String vpcId, String availabilityZoneName) throws Ec2Exception;

    DeleteSubnetResponse deleteSubnet(String subnetId) throws Ec2Exception;

    String getSubnetIdByName(String subnetName) throws Ec2Exception;
}
