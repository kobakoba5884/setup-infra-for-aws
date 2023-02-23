package min.koba58.awswithspringboot.services.budgets;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import software.amazon.awssdk.services.budgets.BudgetsClient;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BudgetsSharedTest {
    @Autowired
    protected BudgetsClient budgetsClient;

    @Autowired
    protected BudgetService budgetsService;

    protected String budgetName = "test-budget";

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
