package min.koba58.awswithspringboot.services.iam.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreateGroupRequest;
import software.amazon.awssdk.services.iam.model.CreateGroupResponse;
import software.amazon.awssdk.services.iam.model.DeleteGroupRequest;
import software.amazon.awssdk.services.iam.model.DeleteGroupResponse;
import software.amazon.awssdk.services.iam.model.GetGroupRequest;
import software.amazon.awssdk.services.iam.model.GetGroupResponse;
import software.amazon.awssdk.services.iam.model.Group;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListGroupsRequest;
import software.amazon.awssdk.services.iam.model.ListGroupsResponse;

@Service
@RequiredArgsConstructor
public class IamGroupServiceImpl implements IamGroupService {
    private final IamClient iamClient;

    // Create a new IAM group
    @Override
    public CreateGroupResponse createIamGroup(String groupName) throws IamException {
        CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
                .groupName(groupName)
                .build();
        try {
            CreateGroupResponse createGroupResponse = iamClient.createGroup(createGroupRequest);

            Group group = createGroupResponse.group();

            System.out.printf("The new group (%s) was successfully created!!\n", group.groupName());

            return createGroupResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // Delete an existing IAM group
    @Override
    public DeleteGroupResponse deleteIamGroup(String groupName) throws IamException {
        DeleteGroupRequest deleteGroupRequest = DeleteGroupRequest.builder()
                .groupName(groupName)
                .build();
        try {
            DeleteGroupResponse deleteGroupResponse = iamClient.deleteGroup(deleteGroupRequest);

            System.out.printf("Group %s successfully deleted!!\n", groupName);

            return deleteGroupResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // Get an existing IAM group
    @Override
    public Group getIamGroup(String groupName) throws IamException {
        GetGroupRequest getGroupRequest = GetGroupRequest.builder()
                .groupName(groupName)
                .build();
        try {
            GetGroupResponse getGroupResponse = iamClient.getGroup(getGroupRequest);

            Group group = getGroupResponse.group();

            System.out.printf("Found IAM group (%s): %s\n", group.groupName(), group.arn());

            return group;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // Get all IAM groups
    @Override
    public List<Group> getIamGroups() throws IamException{
        ListGroupsRequest listGroupsRequest = ListGroupsRequest.builder()
                .build();

        List<Group> groups = new ArrayList<>();
        try {
            ListGroupsResponse listGroupsResponse = iamClient.listGroups(listGroupsRequest);

            groups = listGroupsResponse.groups();

            groups.stream().forEach(group -> {
                System.out.printf("Found IAM group (%s): %s\n", group.groupName(), group.arn());
            });

            return groups;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            
            throw e;
        }
    }
}