package ru.babenko.console.scenarios.banks;

import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.banks.BankService;
import ru.babenko.console.Scenario;

import java.util.Scanner;

public class RegisterNewUserScenario implements Scenario {

    private final BankService bankService;

    public RegisterNewUserScenario(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public String getScenarioName() {
        return "Register new user";
    }

    @Override
    public void run() {
        System.out.println("Enter name:");
        String name = new Scanner(System.in).nextLine();

        System.out.println("Enter surname:");
        String surname = new Scanner(System.in).nextLine();

        BankOperationsResultType result = bankService.registerBankUser(name, surname);

        if (result instanceof BankOperationsResultType.Success) {
            System.out.println("Login successful");
        } else if (result instanceof BankOperationsResultType.Failure failureResult) {
            System.out.println("Login failed: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
