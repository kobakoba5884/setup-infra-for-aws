package min.koba58.awswithspringboot.services.ec2.securityGroup;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupResponse;
import software.amazon.awssdk.services.ec2.model.DeleteSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.DeleteSecurityGroupResponse;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;

@Service
@RequiredArgsConstructor
public class Ec2SecurityGroupServiceImpl implements Ec2SecurityGroupService {
    private final Ec2Client ec2Client;

    // create security group for ec2 instance
    @Override
    public String createEc2SecurityGroup(String groupName, String groupDesc, String vpcId) throws Ec2Exception {

        try {
            CreateSecurityGroupRequest createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
                    .groupName(groupName)
                    .description(groupDesc)
                    .vpcId(vpcId)
                    .build();

            CreateSecurityGroupResponse createSecurityGroupResponse = ec2Client
                    .createSecurityGroup(createSecurityGroupRequest);

            String securityGroupId = createSecurityGroupResponse.groupId();

            System.out.printf("Successfully created Security Group %s (id: %s)\n", groupName, securityGroupId);

            // createTag(ec2Client, securityGroupId, groupName);

            return securityGroupId;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // get security group by name
    // todo maybe, throw IndexOutOfBoundsException
    @Override
    public SecurityGroup getEc2SecurityGroupByName(String securityGroupName) throws Ec2Exception {
        try {
            DescribeSecurityGroupsRequest describeSecurityGroupsRequest = DescribeSecurityGroupsRequest.builder()
                    .groupNames(securityGroupName)
                    .build();

            DescribeSecurityGroupsResponse describeSecurityGroupsResponse = ec2Client
                    .describeSecurityGroups(describeSecurityGroupsRequest);

            List<SecurityGroup> securityGroups = describeSecurityGroupsResponse.securityGroups();

            System.out.printf("found securityGroup by %s !!\n", securityGroupName);

            return securityGroups.get(0);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // get security group by id
    @Override
    public SecurityGroup getEC2SecurityGroupById(String securityGroupId) throws Ec2Exception {
        try {
            DescribeSecurityGroupsRequest describeSecurityGroupsRequest = DescribeSecurityGroupsRequest.builder()
                    .groupIds(securityGroupId)
                    .build();

            DescribeSecurityGroupsResponse describeSecurityGroupsResponse = ec2Client
                    .describeSecurityGroups(describeSecurityGroupsRequest);

            List<SecurityGroup> securityGroups = describeSecurityGroupsResponse.securityGroups();

            System.out.printf("found securityGroup by %s !!\n", securityGroupId);

            return securityGroups.get(0);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // delete securityGroup
    @Override
    public DeleteSecurityGroupResponse deleteEC2SecurityGroup(String groupId) throws Ec2Exception {
        try {
            DeleteSecurityGroupRequest deleteSecurityGroupRequest = DeleteSecurityGroupRequest.builder()
                    .groupId(groupId)
                    .build();

            DeleteSecurityGroupResponse deleteSecurityGroupResponse = ec2Client
                    .deleteSecurityGroup(deleteSecurityGroupRequest);

            System.out.printf("Successfully deleted Security Group with id %s\n", groupId);

            return deleteSecurityGroupResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }
}
