package min.koba58.awswithspringboot.services.iam.policy;

import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.Policy;

public interface IamPolicyService {
    String getPolicyArnByPolicyName(String policyName) throws IamException;
    
    Policy getPolicyByPolicyArn(String policyArn) throws IamException;
}
