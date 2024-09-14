package ru.babenko.console.scenarios.bankAccounts;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.console.Scenario;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class TransferScenario implements Scenario {
    private final BankAccountService bankAccountService;

    public TransferScenario(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String getScenarioName() {
        return "Transfer to another Bank Account";
    }

    @Override
    public void run() {
        System.out.println("Enter another Bank Account number:");
        BigInteger anotherBankAccountNumber = new Scanner(System.in).nextBigInteger();

        System.out.println("Enter amount to transfer:");
        BigDecimal amount = new Scanner(System.in).nextBigDecimal();

        var result = bankAccountService.transfer(anotherBankAccountNumber, amount);

        if (result instanceof BankAccountOperationsResultType.Success) {
            System.out.println("Transfer successful");
        } else if (result instanceof BankAccountOperationsResultType.Failure failureResult) {
            System.out.println("Transfer failed: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
