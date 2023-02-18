package min.koba58.awswithspringboot.services.iam.user;

import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.DeleteUserResponse;
import software.amazon.awssdk.services.iam.model.User;

public interface IamUserService {
    Optional<CreateUserResponse> createIamUser(String userName);

    Optional<DeleteUserResponse> deleteIamUser(String userName);

    Optional<User> getIamUserByName(String userName);

    List<User> getIamUsers();
}
