package min.koba58.awswithspringboot.services.iam.user;

import java.util.List;

import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.DeleteUserResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.User;

public interface IamUserService {
    CreateUserResponse createIamUser(String userName) throws IamException;

    DeleteUserResponse deleteIamUser(String userName) throws IamException;

    User getIamUserByName(String userName) throws IamException;

    List<User> getIamUsers() throws IamException;
}
