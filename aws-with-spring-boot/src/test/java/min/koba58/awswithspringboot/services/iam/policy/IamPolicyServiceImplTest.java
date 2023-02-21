package min.koba58.awswithspringboot.services.iam.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;

public class IamPolicyServiceImplTest extends IamSharedTest {
    @Test
    void testGetPolicyArnByPolicyName_normal() {
        String policyName = "AdministratorAccess";

        assertDoesNotThrow(() -> {
            iamPolicyService.getPolicyArnByPolicyName(policyName);
        });
    }

    @Test
    void testGetPolicyByPolicyArn_normal() {
        assertDoesNotThrow(() -> {
            iamPolicyService.getPolicyByPolicyArn("arn:aws:iam::aws:policy/AdministratorAccess");
        });
    }
}
