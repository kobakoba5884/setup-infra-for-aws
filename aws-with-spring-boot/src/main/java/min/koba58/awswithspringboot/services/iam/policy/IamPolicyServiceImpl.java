package min.koba58.awswithspringboot.services.iam.policy;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.GetPolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.Policy;

@Service
@RequiredArgsConstructor
public class IamPolicyServiceImpl implements IamPolicyService {
    private final IamClient iamClient;

    @Override
    public String getPolicyArnByPolicyName(String policyName) throws IamException{
        String convertedToArn = "arn:aws:iam::aws:policy/%s".formatted(policyName);

        System.out.println(convertedToArn.equals("arn:aws:iam::aws:policy/AdministratorAccess"));
        try{
            GetPolicyResponse response = iamClient.getPolicy(request -> 
                request.policyArn(convertedToArn).build());

            return response.policy().arn();
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public Policy getPolicyByPolicyArn(String policyArn) throws IamException {
        try {
            GetPolicyResponse response = iamClient.getPolicy(request -> 
                request.policyArn(policyArn).build());

                Policy policy = response.policy();

                System.out.println("found policy which is %s".formatted(policy.policyName()));

            return policy;
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

}
