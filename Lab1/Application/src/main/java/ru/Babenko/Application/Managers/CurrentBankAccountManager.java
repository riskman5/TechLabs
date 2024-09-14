package ru.babenko.application.managers;

import org.springframework.stereotype.Service;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.models.bankAccounts.BankAccount;

import java.util.Optional;

@Service
public class CurrentBankAccountManager implements CurrentBankAccountService {
    private Optional<BankAccount> currentBankAccount;

    public CurrentBankAccountManager() {
        currentBankAccount = Optional.empty();
    }

    @Override
    public Optional<BankAccount> getBankAccount() {
        return currentBankAccount;
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        if (bankAccount == null) {
            return;
        }
        currentBankAccount = Optional.of(bankAccount);
    }
}
