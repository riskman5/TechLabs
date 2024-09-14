package ru.babenko.console.scenarios.users;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.users.UserOperationsResultType;
import ru.babenko.contracts.users.UserService;

public class CreateCreditBankAccountScenario implements Scenario {
    private final UserService userService;

    public CreateCreditBankAccountScenario(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getScenarioName() {
        return "Create debit account";
    }

    @Override
    public void run() {
        var result = userService.createCreditBankAccount();

        if (result instanceof UserOperationsResultType) {
            System.out.println("Account created");
        } else if (result instanceof UserOperationsResultType.Failure failureResult) {
            System.out.println("Failed to create account: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
