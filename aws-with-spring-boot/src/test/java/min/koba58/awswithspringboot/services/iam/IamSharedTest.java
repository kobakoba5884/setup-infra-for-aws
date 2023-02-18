package min.koba58.awswithspringboot.services.iam;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import min.koba58.awswithspringboot.services.iam.group.IamGroupService;
import min.koba58.awswithspringboot.services.iam.user.IamUserService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class IamSharedTest {
    @Autowired
    protected IamUserService iamUserService;

    @Autowired
    protected IamGroupService iamGroupService;

    protected String userName = "test-user";

    protected String groupName = "test-group";

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: %s".formatted(testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        System.out.println("%s method was end!!".formatted(testInfo.getDisplayName()));
    }
}
