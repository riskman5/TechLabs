package ru.babenko.console.scenarios.authorization;

import ru.babenko.console.Scenario;
import ru.babenko.contracts.authorization.AuthorizationService;

public class LogoutScenario implements Scenario {
    private final AuthorizationService authorizationService;

    public LogoutScenario(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public String getScenarioName() {
        return "Logout";
    }

    @Override
    public void run() {
        authorizationService.logout();
        System.out.println("Logout successful");
    }
}
