package min.koba58.awswithspringboot.services.iam.group;

import java.util.List;

import software.amazon.awssdk.services.iam.model.CreateGroupResponse;
import software.amazon.awssdk.services.iam.model.DeleteGroupResponse;
import software.amazon.awssdk.services.iam.model.Group;
import software.amazon.awssdk.services.iam.model.IamException;

public interface IamGroupService {
    CreateGroupResponse createIamGroup(String groupName) throws IamException;

    DeleteGroupResponse deleteIamGroup(String groupName) throws IamException;

    Group getIamGroup(String groupName) throws IamException;

    List<Group> getIamGroups() throws IamException;
}
