package min.koba58.awswithspringboot.services.iam.group;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;
import software.amazon.awssdk.services.iam.model.CreateGroupResponse;
import software.amazon.awssdk.services.iam.model.DeleteGroupResponse;
import software.amazon.awssdk.services.iam.model.Group;
import software.amazon.awssdk.services.iam.model.IamException;

public class IamGroupServiceImplTest extends IamSharedTest {

    @Test
    @Order(1)
    void testCreateIamGroup_normal() {
        try {
            iamGroupService.deleteIamGroup(groupName);

            CreateGroupResponse result = iamGroupService.createIamGroup(groupName);

            assertTrue(result.sdkHttpResponse().isSuccessful());
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    @Test
    @Order(2)
    void testGetIamGroup_normal() {
        Group result = iamGroupService.getIamGroup(groupName);

        assertEquals(groupName, result.groupName(), "groupName is a different something or nothing");
    }

    @Order(3)
    @ParameterizedTest
    @ValueSource(strings = { "Non-existentGroupName", "Invalid GroupName" })
    void testGetIamGroup_itShouldReturnException(String testGroupName) {
        assertThrows(IamException.class, () -> {
            iamGroupService.getIamGroup(testGroupName);
        });
    }

    @Test
    @Order(4)
    void testGetIamGroups_normal() {
        assertDoesNotThrow(() -> {
            List<Group> result = iamGroupService.getIamGroups();

            assertNotNull(result);
        });
    }

    @Test
    @Order(5)
    void testDeleteIamGroup_normal() {
        DeleteGroupResponse result = iamGroupService.deleteIamGroup(groupName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    @Order(6)
    void testDeleteIamGroup_itShouldReturnException_giveNonExistedGroupName() {
        assertThrows(IamException.class, () -> {
            iamGroupService.deleteIamGroup(groupName);
        });
    }
}
