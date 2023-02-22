package min.koba58.awswithspringboot.services.iam.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyRequest;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;

@Service
@RequiredArgsConstructor
public class IamUserUpdateServiceImpl implements IamUserUpdateService{
    private final IamClient iamClient;

    @Override
    public AttachUserPolicyResponse attachPolicyToIamUser(String userName, String policyArn) throws IamException {
        AttachUserPolicyRequest attachPolicyRequest = AttachUserPolicyRequest.builder()
            .userName(userName)
            .policyArn(policyArn)
            .build();
    try {
        AttachUserPolicyResponse attachUserPolicyResponse = iamClient.attachUserPolicy(attachPolicyRequest);

        System.out.printf("Policy %s successfully attached to user %s!!\n", policyArn, userName);

        return attachUserPolicyResponse;
    } catch (IamException e) {
        System.err.println(e.awsErrorDetails().errorMessage());
        throw e;
    }
    }


    
}
