package ru.babenko.console.scenarios.centralBank;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.contracts.centralBank.CentralBankService;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.users.CentralBankWorker;

import java.util.Optional;

@Component
@Scope("prototype")
public class CreateNewBankScenarioProvider implements ScenarioProvider {
    private final CentralBankService centralBankService;
    private final CurrentUserService currentUser;

    public CreateNewBankScenarioProvider(CentralBankService centralBankService, CurrentUserService currentUser) {
        this.centralBankService = centralBankService;
        this.currentUser = currentUser;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentUser.getUser().isEmpty()
                || !(currentUser.getUser().get() instanceof CentralBankWorker)) {
            return Optional.empty();
        }

        return Optional.of(new CreateNewBankScenario(centralBankService));
    }
}
