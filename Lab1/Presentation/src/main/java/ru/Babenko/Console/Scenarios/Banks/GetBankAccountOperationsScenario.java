package ru.babenko.console.scenarios.banks;

import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.banks.BankService;
import ru.babenko.console.Scenario;

import java.math.BigInteger;
import java.util.Scanner;

public class GetBankAccountOperationsScenario implements Scenario {
    private final BankService bankService;

    public GetBankAccountOperationsScenario(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public String getScenarioName() {
        return "Get bank account operations";
    }

    @Override
    public void run() {
        System.out.println("Enter bank account number:");
        BigInteger bankAccountNumber = new Scanner(System.in).nextBigInteger();
        var result = bankService.getBankAccountOperationsByBankAccountNumber(bankAccountNumber);

        if (result instanceof BankOperationsResultType.SuccessWithOperations successResult) {
            System.out.println("Operations:");
            successResult.operations().forEach(System.out::println);
        } else if (result instanceof BankOperationsResultType.Failure failureResult) {
            System.out.println("Failed to get operations: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
