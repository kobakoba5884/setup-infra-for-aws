package min.koba58.awswithspringboot.services.ec2.securityGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressResponse;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupResponse;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.IpPermission;
import software.amazon.awssdk.services.ec2.model.IpRange;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;
import min.koba58.awswithspringboot.utils.IpAddressHandler;

@Service
@RequiredArgsConstructor
public class Ec2SecurityGroupServiceImpl implements Ec2SecurityGroupService {
    private final Ec2Client ec2Client;

    private final IpAddressHandler IpAddressHandler;

    // create security group for ec2 instance
    @Override
    public String createEc2SecurityGroup(String groupName, String groupDesc, String vpcId) throws Ec2Exception{

        try {
            CreateSecurityGroupRequest createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
                    .groupName(groupName)
                    .description(groupDesc)
                    .vpcId(vpcId)
                    .build();

            CreateSecurityGroupResponse createSecurityGroupResponse = ec2Client
                    .createSecurityGroup(createSecurityGroupRequest);

            String securityGroupId = createSecurityGroupResponse.groupId();
            List<IpPermission> ipPermissions = createDefaultBoundRules();

            AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest
                    .builder()
                    .groupName(groupName)
                    .ipPermissions(ipPermissions)
                    .build();

            @SuppressWarnings("unused")
            AuthorizeSecurityGroupIngressResponse authorizeSecurityGroupIngressResponse = ec2Client
                    .authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

            System.out.printf("Successfully added ingress policy to Security Group %s\n", groupName);

            // createTag(ec2Client, securityGroupId, groupName);

            return securityGroupId;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // get security group by name
    @Override
    public Optional<SecurityGroup> getEc2SecurityGroupByName(String securityGroupName){
        try {
            DescribeSecurityGroupsRequest describeSecurityGroupsRequest = DescribeSecurityGroupsRequest.builder()
                .groupNames(securityGroupName)
                .build();

            DescribeSecurityGroupsResponse describeSecurityGroupsResponse = ec2Client.describeSecurityGroups(describeSecurityGroupsRequest);
            
            List<SecurityGroup> securityGroups = describeSecurityGroupsResponse.securityGroups();

            System.out.printf("found securityGroup by %s !!\n", securityGroupName);

            return Optional.of(securityGroups.get(0));
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

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
