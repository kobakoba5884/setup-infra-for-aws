package min.koba58.awswithspringboot.services.iam.group;

import software.amazon.awssdk.services.iam.model.AddUserToGroupResponse;
import software.amazon.awssdk.services.iam.model.AttachGroupPolicyResponse;
import software.amazon.awssdk.services.iam.model.DetachGroupPolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.RemoveUserFromGroupResponse;

public interface IamGroupUpdateService {
    AttachGroupPolicyResponse attachPolicyToIamGroup(String groupName, String policyArn) throws IamException;

    DetachGroupPolicyResponse removePolicyFromIamGroup(String groupName, String policyArn) throws IamException;

    AddUserToGroupResponse addUserToIamGroup(String groupName, String userName) throws IamException;

    RemoveUserFromGroupResponse removeUserFromIamGroup(String groupName, String userName) throws IamException;
}
