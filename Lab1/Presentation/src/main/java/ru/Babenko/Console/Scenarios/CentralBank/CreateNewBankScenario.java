package ru.babenko.console.scenarios.centralBank;

import ru.babenko.contracts.centralBank.CentralBankService;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.console.Scenario;

import java.util.Scanner;

public class CreateNewBankScenario implements Scenario {
    private final CentralBankService centralBankService;

    public CreateNewBankScenario(CentralBankService centralBankService) {
        this.centralBankService = centralBankService;
    }

    @Override
    public String getScenarioName() {
        return "Create new bank";
    }

    @Override
    public void run() {
        System.out.println("Enter bank name:");
        String bankName = new Scanner(System.in).nextLine();

        BankOperationsResultType result = centralBankService.createBank(bankName);

        if (result instanceof BankOperationsResultType.Success) {
            System.out.println("Bank created successfully");
        }
        else if (result instanceof BankOperationsResultType.Failure failureResult) {
            System.out.println("Bank creation failed: " + failureResult.message());
        }
        else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
