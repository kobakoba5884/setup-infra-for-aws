package min.koba58.awswithspringboot.services.iam.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.iam.IamSharedTest;
import software.amazon.awssdk.services.iam.model.CreateLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.DeleteLoginProfileResponse;

public class LoginProfileServiceImplTest extends IamSharedTest{
    @Test
    void testEnableConsoleAccessForUser_normal() {
        String password = "Password-for-%s-123".formatted(userName);
        CreateLoginProfileResponse result = loginProfileService.enableConsoleAccessForUser(userName, password, false);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDisableConsoleAccessForUser_normal() {
        DeleteLoginProfileResponse result = loginProfileService.disableConsoleAccessForUser(userName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
