package ru.babenko.console.scenarios.users;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.users.UserOperationsResultType;
import ru.babenko.contracts.users.UserService;

public class SeeBankAccountsNumbers implements Scenario {
    private final UserService userService;

    public SeeBankAccountsNumbers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getScenarioName() {
        return "See accounts numbers";
    }

    @Override
    public void run() {
        var result = userService.getBankAccounts();

        if (result instanceof UserOperationsResultType.SuccessWithBankAccounts successResult) {
            System.out.println("Accounts:");
            successResult.Accounts().forEach(System.out::println);
        } else if (result instanceof UserOperationsResultType.Failure failureResult) {
            System.out.println("Failed to get accounts: " + failureResult.message());
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
