package min.koba58.awswithspringboot.services.iam.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;
import software.amazon.awssdk.services.iam.model.DeleteUserResponse;
import software.amazon.awssdk.services.iam.model.User;

public class IamUserServiceImplTest extends IamSharedTest {
    @Test
    public void createIamUserTest() {
        iamUserService.createIamUser(userName);
    }

    @Test
    void testGetIamUser() {
        User result = iamUserService.getIamUserByName(userName);

        assertEquals(userName, result.userName());
    }

    @Test
    void testGetIamUsers() {
        iamUserService.getIamUsers();
    }

    @Test
    void testDeleteIamUser() {
        DeleteUserResponse result = iamUserService.deleteIamUser(userName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
