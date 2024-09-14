package ru.babenko.console.scenarios.bankAccounts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.babenko.console.Scenario;
import ru.babenko.console.ScenarioProvider;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.models.bankAccounts.DepositBankAccount;

import java.util.Optional;

@Component
@Scope("prototype")
public class SkipDaysScenarioProvider implements ScenarioProvider {
    private final BankAccountService bankAccountService;
    private final CurrentBankAccountService currentBankAccountManager;

    public SkipDaysScenarioProvider(BankAccountService bankAccountService, CurrentBankAccountService currentBankAccountManager) {
        this.bankAccountService = bankAccountService;
        this.currentBankAccountManager = currentBankAccountManager;
    }

    @Override
    public Optional<Scenario> tryGetScenario() {
        if (currentBankAccountManager.getBankAccount().isEmpty()
        || !(currentBankAccountManager.getBankAccount().get() instanceof DepositBankAccount)) {
            return Optional.empty();
        }

        return Optional.of(new SkipDaysScenario(bankAccountService));
    }
}
