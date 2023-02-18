package min.koba58.awswithspringboot.services.iam.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
public class IamGroupServiceImpl implements IamGroupService{
    private final IamClient iamClient;
    
    // Create a new IAM group
    @Override
    public Optional<CreateGroupResponse> createIamGroup(String groupName) {
        CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
                .groupName(groupName)
                .build();
        try {
            CreateGroupResponse createGroupResponse = iamClient.createGroup(createGroupRequest);
            
            Group group = createGroupResponse.group();

            System.out.printf("The new group (%s) was successfully created!!\n", group.groupName());

            return Optional.of(createGroupResponse);
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // Delete an existing IAM group
    @Override
    public Optional<DeleteGroupResponse> deleteIamGroup(String groupName) {
        DeleteGroupRequest deleteGroupRequest = DeleteGroupRequest.builder()
                .groupName(groupName)
                .build();
        try {
            DeleteGroupResponse deleteGroupResponse = iamClient.deleteGroup(deleteGroupRequest);

            System.out.printf("Group %s successfully deleted!!\n", groupName);

            return Optional.of(deleteGroupResponse);
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
    }

    // Get an existing IAM group
    @Override
    public Optional<Group> getIamGroup(String groupName) {
        GetGroupRequest getGroupRequest = GetGroupRequest.builder()
                .groupName(groupName)
                .build();
        try {
            GetGroupResponse getGroupResponse = iamClient.getGroup(getGroupRequest);

            Group group = getGroupResponse.group();

            System.out.printf("Found IAM group (%s): %s\n",group.groupName(), group.arn());

            return Optional.of(group);
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
    }

    // Get all IAM groups
    @Override
    public List<Group> getIamGroups() {
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
            return Collections.emptyList();
        }
    }
}
