package min.koba58.awswithspringboot.services.iam.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyResponse;

public class IamUserUpdateServiceImplTest extends IamSharedTest{

    @Test
    void testAttachPolicyToIamUser_normal() {
        String policyArn = iamPolicyService.getPolicyArnByPolicyName(policyName);

        AttachUserPolicyResponse result = iamUserUpdateService.attachPolicyToIamUser(userName, policyArn);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
