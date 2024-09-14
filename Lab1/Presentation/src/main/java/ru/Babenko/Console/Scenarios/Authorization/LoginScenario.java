package ru.babenko.console.scenarios.authorization;

import ru.babenko.contracts.authorization.AuthorizationResultType;
import ru.babenko.contracts.authorization.AuthorizationService;
import ru.babenko.console.Scenario;

import java.util.Scanner;

public class LoginScenario implements Scenario {
    private final AuthorizationService authorizationService;

    public LoginScenario(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public String getScenarioName() {
        return "Login";
    }

    @Override
    public void run() {
        System.out.println("Enter name:");
        String name = new Scanner(System.in).nextLine();

        System.out.println("Enter surname:");
        String surname = new Scanner(System.in).nextLine();

        AuthorizationResultType result = authorizationService.login(name, surname);

        if (result instanceof AuthorizationResultType.Success) {
            System.out.println("Login successful");
        }
        else if (result instanceof AuthorizationResultType.NotFound) {
            System.out.println("User not found");
        }
        else {
            System.out.println("Something went wrong. Please try again.");
        }
    }
}
