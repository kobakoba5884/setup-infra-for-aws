package min.koba58.awswithspringboot.services.day1;

import software.amazon.awssdk.services.iam.model.IamException;

public interface Day1Service {
    void createUserForDay1(String userName, String groupName, String policyName) throws IamException;

    void deleteUserAndGroupForDay1(String userName, String groupName) throws IamException;
}
