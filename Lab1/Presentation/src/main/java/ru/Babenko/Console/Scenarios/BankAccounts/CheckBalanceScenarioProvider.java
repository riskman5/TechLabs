package ru.babenko.console.scenarios.bankAccounts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.current.CurrentBankAccountService;

import java.util.Optional;

@Component
@Scope("prototype")
public class CheckBalanceScenarioProvider implements ScenarioProvider {
    private final BankAccountService bankAccountService;
    private final CurrentBankAccountService currentBankAccountService;

    public CheckBalanceScenarioProvider(BankAccountService bankAccountService, CurrentBankAccountService currentBankAccountService) {
        this.bankAccountService = bankAccountService;
        this.currentBankAccountService = currentBankAccountService;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentBankAccountService.getBankAccount().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new CheckBalanceScenario(bankAccountService));
    }
}
