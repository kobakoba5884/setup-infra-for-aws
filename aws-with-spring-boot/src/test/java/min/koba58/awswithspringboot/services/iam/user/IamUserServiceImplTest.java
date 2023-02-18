package min.koba58.awswithspringboot.services.iam.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;
import software.amazon.awssdk.services.iam.model.DeleteUserResponse;
import software.amazon.awssdk.services.iam.model.User;

public class IamUserServiceImplTest extends IamSharedTest{
    @Test
    public void createIamUserTest(){
        iamUserService.createIamUser(userName);
    }

    @Test
    void testGetIamUser() {
        Optional<User> result = iamUserService.getIamUserByName(userName);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetIamUsers() {
        iamUserService.getIamUsers();
    }

    @Test
    void testDeleteIamUser() {
        Optional<DeleteUserResponse> result = iamUserService.deleteIamUser(userName);

        assertTrue(result.isPresent());
    }
}
