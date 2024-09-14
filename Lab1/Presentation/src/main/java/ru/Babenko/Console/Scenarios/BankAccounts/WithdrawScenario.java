package ru.babenko.console.scenarios.bankAccounts;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;

public class WithdrawScenario implements Scenario {
    private final BankAccountService bankAccountService;
    public WithdrawScenario(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String getScenarioName() {
        return "Withdraw";
    }

    @Override
    public void run() {
        System.out.println("Enter amount to withdraw:");
        BigDecimal amount = new Scanner(System.in).nextBigDecimal();

        var result = bankAccountService.withdraw(amount);

        if (result instanceof BankAccountOperationsResultType.Success) {
            System.out.println("Withdraw successful");
        } else if (result instanceof BankAccountOperationsResultType.Failure failureResult) {
            System.out.println("Withdraw failed: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
