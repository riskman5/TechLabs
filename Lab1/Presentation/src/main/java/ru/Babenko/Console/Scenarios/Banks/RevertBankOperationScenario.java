package ru.babenko.console.scenarios.banks;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.banks.BankService;

import java.util.Scanner;
import java.util.UUID;

public class RevertBankOperationScenario implements Scenario {
    private final BankService bankService;

    public RevertBankOperationScenario(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public String getScenarioName() {
        return "Revert bank operation";
    }

    @Override
    public void run() {
        System.out.println("Enter operation uuid:");
        UUID operationUuid = UUID.fromString(new Scanner(System.in).nextLine());

        bankService.declineOperationById(operationUuid);
    }
}
