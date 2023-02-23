package min.koba58.awswithspringboot.services.budgets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.budgets.model.Budget;
import software.amazon.awssdk.services.budgets.model.BudgetType;
import software.amazon.awssdk.services.budgets.model.CreateBudgetResponse;
import software.amazon.awssdk.services.budgets.model.DeleteBudgetResponse;
import software.amazon.awssdk.services.budgets.model.Spend;
import software.amazon.awssdk.services.budgets.model.TimeUnit;

import static min.koba58.awswithspringboot.configs.GlobalConfig.AWS_ACCOUNT_ID;

public class BudgetsServiceImplTest extends BudgetsSharedTest {
    @Test
    void testCreateBudget_normal() {
        Spend budgetLimit = Spend.builder()
                .amount(BigDecimal.ONE)
                .unit("USD")
                .build();

        Budget budget = Budget.builder()
                .budgetName(budgetName)
                .budgetType(BudgetType.COST)
                .timeUnit(TimeUnit.MONTHLY)
                .budgetLimit(budgetLimit)
                .build();

        CreateBudgetResponse result = budgetsService.createBudget(AWS_ACCOUNT_ID, budget);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDeleteBudget_normal() {
        DeleteBudgetResponse result = budgetsService.deleteBudget(AWS_ACCOUNT_ID, budgetName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
