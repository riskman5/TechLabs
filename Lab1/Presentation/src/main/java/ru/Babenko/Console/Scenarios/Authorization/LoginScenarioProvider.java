package ru.babenko.console.scenarios.authorization;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.contracts.authorization.AuthorizationService;

import java.util.Optional;

@Component
@Scope("prototype")
public class LoginScenarioProvider implements ScenarioProvider {
    private final AuthorizationService authorizationService;
    private final CurrentUserService currentUser;

    public LoginScenarioProvider(AuthorizationService authorizationService, CurrentUserService currentUser) {
        this.authorizationService = authorizationService;
        this.currentUser = currentUser;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentUser.getUser().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new LoginScenario(authorizationService));
    }
}
