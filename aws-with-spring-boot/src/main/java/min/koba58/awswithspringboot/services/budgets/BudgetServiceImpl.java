package min.koba58.awswithspringboot.services.budgets;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.budgets.BudgetsClient;
import software.amazon.awssdk.services.budgets.model.Budget;
import software.amazon.awssdk.services.budgets.model.BudgetsException;
import software.amazon.awssdk.services.budgets.model.CreateBudgetRequest;
import software.amazon.awssdk.services.budgets.model.CreateBudgetResponse;
import software.amazon.awssdk.services.budgets.model.DeleteBudgetRequest;
import software.amazon.awssdk.services.budgets.model.DeleteBudgetResponse;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetsClient budgetsClient;

    @Override
    public CreateBudgetResponse createBudget(String accountId, Budget budget) throws BudgetsException {

        CreateBudgetRequest createBudgetRequest = CreateBudgetRequest.builder()
                .accountId(accountId)
                .budget(budget)
                .build();

        try {
            CreateBudgetResponse createBudgetResponse = budgetsClient.createBudget(createBudgetRequest);

            System.out.printf("Budget %s created in account ID %s.%n", budget.budgetName(), accountId);

            return createBudgetResponse;
        } catch (BudgetsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public DeleteBudgetResponse deleteBudget(String accountId, String budgetName) throws BudgetsException {
        DeleteBudgetRequest deleteBudgetRequest = DeleteBudgetRequest.builder()
                .budgetName(budgetName)
                .accountId(accountId)
                .build();
        try {
            DeleteBudgetResponse deleteBudgetResponse = budgetsClient
                    .deleteBudget(deleteBudgetRequest);

            System.out.printf("Budget %s was deleted.\n", budgetName);

            return deleteBudgetResponse;
        } catch (BudgetsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

}
