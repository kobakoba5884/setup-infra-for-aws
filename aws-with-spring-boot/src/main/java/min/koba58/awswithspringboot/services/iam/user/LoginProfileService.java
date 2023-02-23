package min.koba58.awswithspringboot.services.iam.user;

import software.amazon.awssdk.services.iam.model.ChangePasswordResponse;
import software.amazon.awssdk.services.iam.model.CreateLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.DeleteLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.LoginProfile;
import software.amazon.awssdk.services.iam.model.UpdateLoginProfileResponse;

public interface LoginProfileService {
    CreateLoginProfileResponse enableConsoleAccessForUser(String userName, String password, boolean passwordResetRequired) throws IamException;

    DeleteLoginProfileResponse disableConsoleAccessForUser(String userName) throws IamException;

    LoginProfile getLoginProfileByName(String userName) throws IamException;

    UpdateLoginProfileResponse updateLoginProfile(String userName, String password, boolean passwordResetRequired) throws IamException;

    ChangePasswordResponse changeIamUserPassword(String userName, String currentPassword, String newPassword) throws IamException;
}
