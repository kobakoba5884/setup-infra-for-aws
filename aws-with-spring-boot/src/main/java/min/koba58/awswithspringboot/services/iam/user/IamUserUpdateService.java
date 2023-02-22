package min.koba58.awswithspringboot.services.iam.user;

import software.amazon.awssdk.services.iam.model.AttachUserPolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;

public interface IamUserUpdateService {
    AttachUserPolicyResponse attachPolicyToIamUser(String userName, String policyArn) throws IamException;
}
