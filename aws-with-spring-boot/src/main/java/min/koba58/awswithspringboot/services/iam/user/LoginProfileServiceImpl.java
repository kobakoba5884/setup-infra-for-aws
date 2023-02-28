package min.koba58.awswithspringboot.services.iam.user;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.ChangePasswordRequest;
import software.amazon.awssdk.services.iam.model.ChangePasswordResponse;
import software.amazon.awssdk.services.iam.model.CreateLoginProfileRequest;
import software.amazon.awssdk.services.iam.model.CreateLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.DeleteLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.GetLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.LoginProfile;
import software.amazon.awssdk.services.iam.model.UpdateLoginProfileRequest;
import software.amazon.awssdk.services.iam.model.UpdateLoginProfileResponse;

@Service
@RequiredArgsConstructor
public class LoginProfileServiceImpl implements LoginProfileService {
    private final IamClient iamClient;

    @Override
    public CreateLoginProfileResponse enableConsoleAccessForUser(String userName, String password,
            boolean passwordResetRequired) throws IamException {
        CreateLoginProfileRequest createLoginProfileRequest = CreateLoginProfileRequest.builder()
                .userName(userName)
                .password(password)
                .passwordResetRequired(passwordResetRequired)
                .build();

        try {
            CreateLoginProfileResponse createLoginProfileResponse = iamClient
                    .createLoginProfile(createLoginProfileRequest);

            LoginProfile loginProfile = createLoginProfileResponse.loginProfile();

            Instant instant = loginProfile.createDate();

            System.out.println(
                    "complete %s's login setting at %s".formatted(loginProfile.userName(), instant.toString()));

            return createLoginProfileResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public DeleteLoginProfileResponse disableConsoleAccessForUser(String userName) throws IamException {
        try {
            DeleteLoginProfileResponse deleteLoginProfileResponse = iamClient
                    .deleteLoginProfile(request -> request.userName(userName).build());

            System.out.println(
                    "be completely disable %s's console access".formatted(userName));

            return deleteLoginProfileResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public LoginProfile getLoginProfileByName(String userName) throws IamException {
        try {
            GetLoginProfileResponse getLoginProfileResponse = iamClient
                    .getLoginProfile(request -> request.userName(userName).build());

            LoginProfile loginProfile = getLoginProfileResponse.loginProfile();

            System.out.println("found login profile's %s".formatted(userName));

            return loginProfile;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public UpdateLoginProfileResponse updateLoginProfile(String userName, String password,
            boolean passwordResetRequired) throws IamException {
        UpdateLoginProfileRequest updateLoginProfileRequest = UpdateLoginProfileRequest.builder()
                .userName(userName)
                .password(password)
                .passwordResetRequired(passwordResetRequired)
                .build();

        try {
            UpdateLoginProfileResponse updateLoginProfileResponse = iamClient
                    .updateLoginProfile(updateLoginProfileRequest);

            System.out.println("IAM user's login profile updated successfully!!");

            return updateLoginProfileResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public ChangePasswordResponse changeIamUserPassword(String userName, String currentPassword, String newPassword)
            throws IamException {

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
                .oldPassword(currentPassword)
                .newPassword(newPassword)
                .build();
        try {
            ChangePasswordResponse changePasswordResponse = iamClient.changePassword(changePasswordRequest);

            System.out.printf("IAM user %s password changed successfully!\n", userName);

            return changePasswordResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

}
