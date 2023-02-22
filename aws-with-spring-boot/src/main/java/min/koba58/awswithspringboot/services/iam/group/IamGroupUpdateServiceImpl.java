package min.koba58.awswithspringboot.services.iam.group;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AddUserToGroupRequest;
import software.amazon.awssdk.services.iam.model.AddUserToGroupResponse;
import software.amazon.awssdk.services.iam.model.AttachGroupPolicyRequest;
import software.amazon.awssdk.services.iam.model.AttachGroupPolicyResponse;
import software.amazon.awssdk.services.iam.model.DetachGroupPolicyRequest;
import software.amazon.awssdk.services.iam.model.DetachGroupPolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.RemoveUserFromGroupRequest;
import software.amazon.awssdk.services.iam.model.RemoveUserFromGroupResponse;

@Service
@RequiredArgsConstructor
public class IamGroupUpdateServiceImpl implements IamGroupUpdateService {
    private final IamClient iamClient;

    @Override
    public AttachGroupPolicyResponse attachPolicyToIamGroup(String groupName, String policyArn) throws IamException {
        AttachGroupPolicyRequest attachPolicyRequest = AttachGroupPolicyRequest.builder()
                .groupName(groupName)
                .policyArn(policyArn)
                .build();
        try {
            AttachGroupPolicyResponse attachGroupPolicyResponse = iamClient.attachGroupPolicy(attachPolicyRequest);

            System.out.printf("Policy %s successfully attached to group %s!!\n", policyArn, groupName);

            return attachGroupPolicyResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public DetachGroupPolicyResponse removePolicyFromIamGroup(String groupName, String policyArn) throws IamException {
        DetachGroupPolicyRequest detachPolicyRequest = DetachGroupPolicyRequest.builder()
                .groupName(groupName)
                .policyArn(policyArn)
                .build();
        try {
            DetachGroupPolicyResponse detachGroupPolicyResponse = iamClient.detachGroupPolicy(detachPolicyRequest);
            
            System.out.printf("Policy %s successfully detached from group %s!!\n", policyArn, groupName);

            return detachGroupPolicyResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public AddUserToGroupResponse addUserToIamGroup(String groupName, String userName) throws IamException {
        AddUserToGroupRequest addUserToGroupRequest = AddUserToGroupRequest.builder()
            .groupName(groupName)
            .userName(userName)
            .build();

        try{
            AddUserToGroupResponse addUserToGroupResponse = iamClient.addUserToGroup(addUserToGroupRequest);

            System.out.printf("User %s successfully add to group %s!!\n", userName, groupName);

            return addUserToGroupResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public RemoveUserFromGroupResponse removeUserFromIamGroup(String groupName, String userName) throws IamException {
        RemoveUserFromGroupRequest removeUserFromGroupRequest = RemoveUserFromGroupRequest.builder()
            .groupName(groupName)
            .userName(userName)
            .build();

        try{
            RemoveUserFromGroupResponse removeUserFromGroupResponse = iamClient.removeUserFromGroup(removeUserFromGroupRequest);

            System.out.printf("User %s successfully remove from group %s!!\n", userName, groupName);

            return removeUserFromGroupResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }
}
