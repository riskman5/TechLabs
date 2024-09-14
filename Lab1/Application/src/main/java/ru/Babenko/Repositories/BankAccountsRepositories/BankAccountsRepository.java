package ru.babenko.repositories.bankAccountsRepositories;

import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigInteger;
import java.util.Optional;

public interface BankAccountsRepository {
    BankAccountsRepositoriesResultType addBankAccount(BankAccount bankAccount);

    Optional<BankAccount> findBankAccountByNumber(BigInteger accountNumber);

    void removeBankAccount(BigInteger accountNumber);

    BigInteger getCurrentFreeAccountNumber();
}
