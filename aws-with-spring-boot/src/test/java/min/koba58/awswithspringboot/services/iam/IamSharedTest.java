package min.koba58.awswithspringboot.services.iam;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import min.koba58.awswithspringboot.services.iam.group.IamGroupService;
import min.koba58.awswithspringboot.services.iam.role.IamRoleService;
import min.koba58.awswithspringboot.services.iam.user.IamUserService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IamSharedTest {
    @Autowired
    protected IamUserService iamUserService;

    @Autowired
    protected IamGroupService iamGroupService;

    @Autowired 
    protected IamRoleService iamRoleService;

    protected String userName = "test-user";

    protected String groupName = "test-group";

    protected String roleName = "test-role";

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: %s".formatted(testInfo.getDisplayName()));
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println("%s method was end!!".formatted(testInfo.getDisplayName()));
    }

    @BeforeAll
    void beforeAll() {
        System.out.println("all test method is starting!!");
    }

    @AfterAll
    void afterAll() {
        System.out.println("all test method is end!!");
    }
}
