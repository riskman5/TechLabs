package ru.babenko.console.scenarios.bankAccounts;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.bankAccounts.BankAccountService;

public class SkipDaysScenario implements Scenario {
    private final BankAccountService bankAccountService;

    public SkipDaysScenario(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String getScenarioName() {
        return "Skip days";
    }

    @Override
    public void run() {
        System.out.println("Enter days count:");
        var days = Integer.parseInt(System.console().readLine());
        var result = bankAccountService.skipDays(days);

        if (result instanceof BankAccountOperationsResultType.Success) {
            System.out.println("Days skipped successfully");
        } else if (result instanceof BankAccountOperationsResultType.Failure failureResult) {
            System.out.println("Failed to skip days: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
