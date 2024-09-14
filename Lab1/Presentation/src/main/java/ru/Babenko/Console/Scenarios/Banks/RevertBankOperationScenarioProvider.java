package ru.babenko.console.scenarios.banks;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.banks.BankService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.users.BankWorker;

import java.util.Optional;

@Component
@Scope("prototype")
public class RevertBankOperationScenarioProvider implements ScenarioProvider {
    private final BankService bankService;
    private final CurrentUserService currentUser;

    public RevertBankOperationScenarioProvider(BankService bankService, CurrentUserService currentUser) {
        this.bankService = bankService;
        this.currentUser = currentUser;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentUser.getUser().isEmpty()
                || !(currentUser.getUser().get() instanceof BankWorker)) {
            return Optional.empty();
        }

        return Optional.of(new RevertBankOperationScenario(bankService));
    }
}
