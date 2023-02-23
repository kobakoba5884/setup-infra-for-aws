package min.koba58.awswithspringboot.services.day1;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.iam.group.IamGroupService;
import min.koba58.awswithspringboot.services.iam.group.IamGroupUpdateService;
import min.koba58.awswithspringboot.services.iam.policy.IamPolicyService;
import min.koba58.awswithspringboot.services.iam.user.IamUserService;
import min.koba58.awswithspringboot.services.iam.user.LoginProfileService;
import software.amazon.awssdk.services.iam.model.IamException;

@Service
@RequiredArgsConstructor
public class Day1ServiceImpl implements Day1Service {
    private final IamUserService iamUserService;
    private final IamGroupService iamGroupService;
    private final IamGroupUpdateService iamGroupUpdateService;
    private final IamPolicyService iamPolicyService;
    private final LoginProfileService loginProfileService;

    @Override
    public void createUserForDay1(String userName, String groupName, String policyName) throws IamException {
        String policyArn = iamPolicyService.getPolicyArnByPolicyName(policyName);

        String password = "Password-for-%s-123".formatted(userName);

        iamUserService.createIamUser(userName);

        iamGroupService.createIamGroup(groupName);

        iamGroupUpdateService.attachPolicyToIamGroup(groupName, policyArn);

        iamGroupUpdateService.addUserToIamGroup(groupName, userName);

        loginProfileService.enableConsoleAccessForUser(userName, password, true);
    }

    @Override
    public void deleteUserAndGroupForDay1(String userName, String groupName) throws IamException {
        loginProfileService.disableConsoleAccessForUser(userName);

        iamGroupUpdateService.removeUserFromIamGroup(groupName, userName);

        iamUserService.deleteIamUser(userName);

        iamGroupUpdateService.detachAllGroupPoliciesFromIamGroup(groupName);

        iamGroupService.deleteIamGroup(groupName);
    }

}
