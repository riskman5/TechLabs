package ru.babenko.console.scenarios.bankAccounts;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;

public class DepositScenario implements Scenario {
    private final BankAccountService bankAccountService;
    public DepositScenario(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String getScenarioName() {
        return "Deposit";
    }

    @Override
    public void run() {
        System.out.println("Enter amount to deposit:");
        BigDecimal amount = new Scanner(System.in).nextBigDecimal();

        var result = bankAccountService.deposit(amount);

        if (result instanceof BankAccountOperationsResultType.Success) {
            System.out.println("Deposit successful");
        } else if (result instanceof BankAccountOperationsResultType.Failure failureResult) {
            System.out.println("Deposit failed: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
