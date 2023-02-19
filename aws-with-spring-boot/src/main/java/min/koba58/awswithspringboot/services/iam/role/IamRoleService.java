package min.koba58.awswithspringboot.services.iam.role;

import java.util.List;

import software.amazon.awssdk.services.iam.model.CreateRoleResponse;
import software.amazon.awssdk.services.iam.model.DeleteRoleResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.Role;

public interface IamRoleService {
    CreateRoleResponse createIamRole(String roleName, String policyDocument) throws IamException;

    Role getIamRoleByName(String roleName) throws IamException;

    List<Role> getIamRoles() throws IamException;

    DeleteRoleResponse deleteIamRole(String roleName) throws IamException;
}
