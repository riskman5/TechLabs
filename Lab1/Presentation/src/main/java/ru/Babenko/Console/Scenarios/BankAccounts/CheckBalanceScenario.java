package ru.babenko.console.scenarios.bankAccounts;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.console.Scenario;

public class CheckBalanceScenario implements Scenario {
    private final BankAccountService bankAccountService;

    public CheckBalanceScenario(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String getScenarioName() {
        return "Check balance";
    }

    @Override
    public void run() {
        var result = bankAccountService.checkBalance();

        if (result instanceof BankAccountOperationsResultType.SuccessWithAmount successfulResult) {
            System.out.println("Balance: " + successfulResult.balance());
        }
        else if (result instanceof BankAccountOperationsResultType.Failure failureResult) {
            System.out.println("Check balance failed: " + failureResult.message());
        }
        else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
