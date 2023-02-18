package min.koba58.awswithspringboot.services.vpc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VpcSharedTest {
    @Autowired
    protected VpcService vpcService;

    protected String vpcName = "test-vpc";
    
    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: %s".formatted(testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        System.out.println("%s method was end!!".formatted(testInfo.getDisplayName()));
    }
}
