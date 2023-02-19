package min.koba58.awswithspringboot.services.iam.role;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreateRoleRequest;
import software.amazon.awssdk.services.iam.model.CreateRoleResponse;
import software.amazon.awssdk.services.iam.model.DeleteRoleResponse;
import software.amazon.awssdk.services.iam.model.GetRoleResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListRolesResponse;
import software.amazon.awssdk.services.iam.model.Role;
import software.amazon.awssdk.services.iam.waiters.IamWaiter;

@Service
@RequiredArgsConstructor
public class IamRoleServiceImpl implements IamRoleService {
    private final IamClient iamClient;

    // create IAM role
    @Override
    public CreateRoleResponse createIamRole(String roleName, String policyDocument) throws IamException{
        CreateRoleRequest createRoleRequest = CreateRoleRequest.builder()
                .roleName(roleName)
                .assumeRolePolicyDocument(policyDocument)
                .build();

        try {
            CreateRoleResponse createRoleResponse = iamClient.createRole(createRoleRequest);

            String createdRoleName = createRoleResponse.role().roleName();

            waitUntilIamRoleAvailable(createdRoleName);

            System.out.printf("The new role (%s) was successfully created!!\n", createdRoleName);

            return createRoleResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            
            throw e;
        }
    }

    // get IAM role
    @Override
    public Role getIamRoleByName(String roleName) throws IamException{
        try {
            GetRoleResponse getRoleResponse = iamClient.getRole(request -> request.roleName(roleName).build());

            Role iamRole = getRoleResponse.role();

            System.out.printf("Found IAM role (%s): %s\n", iamRole.roleName(), iamRole.arn());

            return iamRole;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    // get all IAM roles
    @Override
    public List<Role> getIamRoles() throws IamException{
        try {
            ListRolesResponse listRolesResponse = iamClient.listRoles();

            List<Role> roles = listRolesResponse.roles();

            String output = "roleName: %s, roleDesc: %s, roleArn: %s";

            roles.stream().forEach(
                    role -> System.out.println(output.formatted(role.roleName(), role.description(), role.arn())));

            return roles;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }

    }

    // delete IAM role
    @Override
    public DeleteRoleResponse deleteIamRole(String roleName) throws IamException{
        try {
            DeleteRoleResponse deleteRoleResponse = iamClient.deleteRole(request -> request.roleName(roleName).build());

            System.out.println("successfully delete IAM role %s!".formatted(roleName));

            return deleteRoleResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    private Optional<GetRoleResponse> waitUntilIamRoleAvailable(String roleName) {
        IamWaiter iamWaiter = iamClient.waiter();

        try {
            // WaiterOverrideConfiguration.builder().waitTimeout(Duration.ofSeconds(60)).build();
            // default time out = 30 seconds
            WaiterResponse<GetRoleResponse> waiterResponse = iamWaiter
                    .waitUntilRoleExists(request -> request.roleName(roleName).build());

            Optional<GetRoleResponse> getRoleResponse = waiterResponse.matched().response();

            getRoleResponse.ifPresent(System.out::println);

            return getRoleResponse;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
    }
}
