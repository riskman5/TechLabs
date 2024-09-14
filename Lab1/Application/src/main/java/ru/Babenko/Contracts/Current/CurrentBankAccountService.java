package ru.babenko.contracts.current;

import ru.babenko.models.bankAccounts.BankAccount;

import java.util.Optional;

public interface CurrentBankAccountService {
    Optional<BankAccount> getBankAccount();
    void setBankAccount(BankAccount bankAccount);
}
