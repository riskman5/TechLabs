package ru.babenko.console.scenarios.users;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.users.UserOperationsResultType;
import ru.babenko.contracts.users.UserService;

import java.util.Scanner;

public class AddAddressScenario implements Scenario {
    private final UserService userService;

    public AddAddressScenario(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String getScenarioName() {
        return "Add address";
    }

    @Override
    public void run() {
        System.out.println("Enter your full address: ");
        String address = new Scanner(System.in).nextLine();

        var result = userService.addAddress(address);

        if (result instanceof UserOperationsResultType.Success) {
            System.out.println("Address added successfully");
        }
        else if (result instanceof UserOperationsResultType.Failure failure) {
            System.out.println("Address addition failed: " + (failure.message()));
        }
        else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
