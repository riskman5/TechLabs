package ru.babenko.console.scenarios.users;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.users.UserOperationsResultType;
import ru.babenko.contracts.users.UserService;

import java.util.Scanner;

public class AddPhoneNumberScenario implements Scenario {
    private final UserService userService;

    public AddPhoneNumberScenario(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getScenarioName() {
        return "Add phone number";
    }

    @Override
    public void run() {
        System.out.println("Enter your phone number: ");
        String phoneNumber = new Scanner(System.in).nextLine();

        var result = userService.addPhoneNumber(phoneNumber);

        if (result instanceof UserOperationsResultType.Success) {
            System.out.println("Phone number added successfully");
        }
        else if (result instanceof UserOperationsResultType.Failure failure) {
            System.out.println("Phone number addition failed: " + (failure.message()));
        }
        else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
