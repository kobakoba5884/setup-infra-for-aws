package min.koba58.awswithspringboot.services.ec2.securityGroup;

import java.util.List;

import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.IpPermission;

public interface Ec2BoundRuleService {
    AuthorizeSecurityGroupIngressResponse addInboundRulesToSecurityGroup(String securityGroupName,
            List<IpPermission> ipPermissions) throws Ec2Exception;
}
