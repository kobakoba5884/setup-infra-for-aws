package min.koba58.awswithspringboot.services.ec2.securityGroup;

import software.amazon.awssdk.services.ec2.model.DeleteSecurityGroupResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;

public interface Ec2SecurityGroupService {
    String createEc2SecurityGroup(String groupName, String groupDesc, String vpcId);

    SecurityGroup getEc2SecurityGroupByName(String securityGroupName) throws Ec2Exception;

    SecurityGroup getEC2SecurityGroupById(String securityGroupId) throws Ec2Exception;

    DeleteSecurityGroupResponse deleteEC2SecurityGroup(String groupId) throws Ec2Exception;
}
