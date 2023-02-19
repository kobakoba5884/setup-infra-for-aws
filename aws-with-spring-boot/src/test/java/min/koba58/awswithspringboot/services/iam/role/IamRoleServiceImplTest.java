package min.koba58.awswithspringboot.services.iam.role;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;

public class IamRoleServiceImplTest extends IamSharedTest{
    @Test
    void testCreateIamRole() {
        String policy = "";
        iamRoleService.createIamRole(roleName, policy);
    }

    @Test
    void testDeleteIamRole() {

    }

    @Test
    void testGetIamRoleByName() {

    }

    @Test
    void testGetIamRoles() {

    }
}
