package ru.babenko.console.scenarios.authorization;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.authorization.AuthorizationService;

import java.util.Optional;

@Component
@Scope("prototype")
public class LogoutScenarioProvider implements ScenarioProvider {
    private final AuthorizationService authorizationService;

    public LogoutScenarioProvider(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    @Override
    public Optional<Scenario> tryGetScenario() {
        return Optional.of(new LogoutScenario(authorizationService));
    }
}
