package min.koba58.awswithspringboot.services.iam.group;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;
import software.amazon.awssdk.services.iam.model.AddUserToGroupResponse;
import software.amazon.awssdk.services.iam.model.AttachGroupPolicyResponse;
import software.amazon.awssdk.services.iam.model.DetachGroupPolicyResponse;
import software.amazon.awssdk.services.iam.model.RemoveUserFromGroupResponse;

public class IamGroupUpdateServiceImplTest extends IamSharedTest{
    @Test
    void testAttachPolicyToIamGroup_normal() {
        String policyArn = iamPolicyService.getPolicyArnByPolicyName(policyName);

        AttachGroupPolicyResponse result = iamGroupUpdateService.attachPolicyToIamGroup(groupName, policyArn);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDeletePolicyFromIamGroup_normal() {
        String policyArn = iamPolicyService.getPolicyArnByPolicyName(policyName);
        
        DetachGroupPolicyResponse result = iamGroupUpdateService.removePolicyFromIamGroup(groupName, policyArn);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testAddUserToIamGroup_normal() {
        AddUserToGroupResponse result = iamGroupUpdateService.addUserToIamGroup(groupName, userName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testRemovePolicyFromIamGroup_normal() {
        RemoveUserFromGroupResponse result = iamGroupUpdateService.removeUserFromIamGroup(groupName, userName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
