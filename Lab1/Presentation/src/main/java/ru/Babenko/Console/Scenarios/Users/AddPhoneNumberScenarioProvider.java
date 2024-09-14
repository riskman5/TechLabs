package ru.babenko.console.scenarios.users;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.contracts.users.UserService;

import java.util.Optional;

@Component
@Scope("prototype")
public class AddPhoneNumberScenarioProvider implements ScenarioProvider {
    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final CurrentBankAccountService currentBankAccountService;

    public AddPhoneNumberScenarioProvider(UserService userService, CurrentUserService currentUserService, CurrentBankAccountService currentBankAccountService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
        this.currentBankAccountService = currentBankAccountService;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentUserService.getUser().isEmpty()
                || currentBankAccountService.getBankAccount().isPresent()) {
            return Optional.empty();
        }

        if (currentUserService.getUser().get().getPhoneNumber().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new AddPhoneNumberScenario(userService));
    }
}
