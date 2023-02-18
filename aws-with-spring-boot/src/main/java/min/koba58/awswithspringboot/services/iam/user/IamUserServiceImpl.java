package min.koba58.awswithspringboot.services.iam.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreateUserRequest;
import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.DeleteUserRequest;
import software.amazon.awssdk.services.iam.model.DeleteUserResponse;
import software.amazon.awssdk.services.iam.model.GetUserRequest;
import software.amazon.awssdk.services.iam.model.GetUserResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListUsersRequest;
import software.amazon.awssdk.services.iam.model.ListUsersResponse;
import software.amazon.awssdk.services.iam.model.User;
import software.amazon.awssdk.services.iam.paginators.ListUsersIterable;
import software.amazon.awssdk.services.iam.waiters.IamWaiter;

@Service
@RequiredArgsConstructor
public class IamUserServiceImpl implements IamUserService{
    private final IamClient iamClient;

    // create iam user
    @Override
    public Optional<CreateUserResponse> createIamUser(String userName) {

        try {
            CreateUserRequest createUserRequest = CreateUserRequest.builder()
                    .userName(userName)
                    .build();
            // .permissionsBoundary()

            CreateUserResponse createUserResponse = iamClient.createUser(createUserRequest);

            String createdUserName = createUserResponse.user().userName();

            waitUntilIamUserAvailable(createdUserName);

            System.out.printf("The new user (%s) was successfully created!!\n", createdUserName);

            return Optional.of(createUserResponse);
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // delete iam user
    @Override
    public Optional<DeleteUserResponse> deleteIamUser(String userName) {
        DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
            .userName(userName)
            .build();

        try{
            DeleteUserResponse deleteUserResponse = iamClient.deleteUser(deleteUserRequest);

            System.out.println("successfully delete %s!".formatted(userName));

            return Optional.of(deleteUserResponse);
        }catch(IamException e){
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
    }

    // get iam user
    @Override
    public Optional<User> getIamUserByName(String userName) {
        GetUserRequest getUserRequest = GetUserRequest.builder()
            .userName(userName)
            .build();

        try{
            GetUserResponse getUserResponse = iamClient.getUser(getUserRequest);

            User iamUser = getUserResponse.user();

            System.out.printf("Found IAM user: %s\n", iamUser.arn());

            return Optional.of(iamUser);
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
    }

    // get all iam users
    @Override
    public List<User> getIamUsers() {
        ListUsersRequest listUsersRequest = ListUsersRequest.builder()
                .build();

        List<User> users = new ArrayList<>();

        try {
            ListUsersIterable listUsersResponses = iamClient.listUsersPaginator(listUsersRequest);

            for (ListUsersResponse listUsersResponse : listUsersResponses) {
                for (User user : listUsersResponse.users()) {
                    System.out.println("found iam user (%s)".formatted(user.userName()));

                    users.add(user);
                }
            }

            return users;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return Collections.emptyList();
        }
    }

    private Optional<GetUserResponse> waitUntilIamUserAvailable(String userName){
        IamWaiter iamWaiter = iamClient.waiter();

        GetUserRequest getUserRequest = GetUserRequest.builder()
            .userName(userName)
            .build();

        try{
            WaiterResponse<GetUserResponse> waiterResponse = iamWaiter.waitUntilUserExists(getUserRequest);

            Optional<GetUserResponse> getUserResponse = waiterResponse.matched().response();

            getUserResponse.ifPresent(System.out::println);

            return getUserResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
    }
}
