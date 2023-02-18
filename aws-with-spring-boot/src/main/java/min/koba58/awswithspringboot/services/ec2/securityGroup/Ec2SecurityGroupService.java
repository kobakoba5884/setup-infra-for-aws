package min.koba58.awswithspringboot.services.ec2.securityGroup;

import java.util.Optional;

import software.amazon.awssdk.services.ec2.model.SecurityGroup;

public interface Ec2SecurityGroupService {
    String createEc2SecurityGroup(String groupName, String groupDesc, String vpcId);

    Optional<SecurityGroup> getEc2SecurityGroupByName(String securityGroupName);
}
