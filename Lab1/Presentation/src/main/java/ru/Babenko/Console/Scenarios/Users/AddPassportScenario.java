package ru.babenko.console.scenarios.users;

import ru.babenko.contracts.users.UserOperationsResultType;
import ru.babenko.contracts.users.UserService;

import ru.babenko.console.Scenario;

import java.util.Scanner;

public class AddPassportScenario implements Scenario {
    private final UserService userService;

    public AddPassportScenario(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getScenarioName() {
        return "Add passport";
    }

    @Override
    public void run() {
        System.out.println("Enter your passport series: ");
        Integer passportSeries = new Scanner(System.in).nextInt();

        System.out.println("Enter your passport number: ");
        Integer passportNumber = new Scanner(System.in).nextInt();

        var result = userService.addPassport(passportSeries, passportNumber);

        if (result instanceof UserOperationsResultType.Success) {
            System.out.println("Passport added successfully");
        } else if (result instanceof UserOperationsResultType.Failure failure) {
            System.out.println("Passport addition failed: " + (failure.message()));
        } else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
