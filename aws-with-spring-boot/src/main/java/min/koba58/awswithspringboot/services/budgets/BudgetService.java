package min.koba58.awswithspringboot.services.budgets;

import software.amazon.awssdk.services.budgets.model.Budget;
import software.amazon.awssdk.services.budgets.model.BudgetsException;
import software.amazon.awssdk.services.budgets.model.CreateBudgetResponse;
import software.amazon.awssdk.services.budgets.model.DeleteBudgetResponse;

public interface BudgetService {
    CreateBudgetResponse createBudget(String accountId, Budget input) throws BudgetsException;

    DeleteBudgetResponse deleteBudget(String accountId, String budgetName) throws BudgetsException;
}
