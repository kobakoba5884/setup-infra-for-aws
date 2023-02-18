package min.koba58.awswithspringboot.services.iam.group;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;

public class IamGroupServiceImplTest extends IamSharedTest{
    @Test
    void testCreateIamGroup() {
        iamGroupService.createIamGroup(groupName);
    }

    @Test
    void testDeleteIamGroup() {
        iamGroupService.deleteIamGroup(groupName);
    }
}
