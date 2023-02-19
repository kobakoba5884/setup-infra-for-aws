package min.koba58.awswithspringboot.services.ec2.securityGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.utils.IpAddressHandler;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.IpPermission;
import software.amazon.awssdk.services.ec2.model.IpRange;

@Service
@RequiredArgsConstructor
public class Ec2BoundRuleServiceImpl implements Ec2BoundRuleService {
    private final Ec2Client ec2Client;

    private final IpAddressHandler IpAddressHandler;

    @Override
    public AuthorizeSecurityGroupIngressResponse addInboundRuleToSecurityGroup(String securityGroupName,
            List<IpPermission> ipPermissions) throws Ec2Exception {

        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest
                .builder()
                .groupName(securityGroupName)
                .ipPermissions(ipPermissions)
                .build();

        try {
            AuthorizeSecurityGroupIngressResponse authorizeSecurityGroupIngressResponse = ec2Client
                    .authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

            return authorizeSecurityGroupIngressResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @SuppressWarnings("unused")
    private List<IpPermission> createDefaultBoundRules() {
        String targetIp = IpAddressHandler.getMyGlobalIpAddress();

        IpRange ipRange = IpRange.builder()
                .cidrIp(targetIp)
                .build();

        Map<Integer, String> initInfos = new HashMap<>();

        initInfos.put(80, "tcp");
        initInfos.put(22, "tcp");

        List<IpPermission> ipPermissions = initInfos.entrySet().stream().map(initInfo -> IpPermission.builder()
                .ipProtocol(initInfo.getValue())
                .toPort(initInfo.getKey())
                .fromPort(initInfo.getKey())
                .ipRanges(ipRange)
                .build()).toList();

        return ipPermissions;
    }
}
