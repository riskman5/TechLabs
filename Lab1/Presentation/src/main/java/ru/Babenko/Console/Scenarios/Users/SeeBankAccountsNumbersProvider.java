package ru.babenko.console.scenarios.users;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.contracts.users.UserService;
import ru.babenko.models.users.BankUser;

import java.util.Optional;

@Component
@Scope("prototype")
public class SeeBankAccountsNumbersProvider implements ScenarioProvider {
    private final UserService userService;
    private final CurrentUserService currentUserService;

    public SeeBankAccountsNumbersProvider(UserService userService, CurrentUserService currentUserService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentUserService.getUser().isEmpty() ||
                !(currentUserService.getUser().get() instanceof BankUser)) {
            return Optional.empty();
        }

        return Optional.of(new SeeBankAccountsNumbers(userService));
    }
}
