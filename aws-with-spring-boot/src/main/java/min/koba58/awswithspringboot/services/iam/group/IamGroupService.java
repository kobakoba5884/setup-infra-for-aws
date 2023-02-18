package min.koba58.awswithspringboot.services.iam.group;

import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.iam.model.CreateGroupResponse;
import software.amazon.awssdk.services.iam.model.DeleteGroupResponse;
import software.amazon.awssdk.services.iam.model.Group;

public interface IamGroupService {
    Optional<CreateGroupResponse> createIamGroup(String groupName);

    Optional<DeleteGroupResponse> deleteIamGroup(String groupName);

    Optional<Group> getIamGroup(String groupName);

    List<Group> getIamGroups();
}
