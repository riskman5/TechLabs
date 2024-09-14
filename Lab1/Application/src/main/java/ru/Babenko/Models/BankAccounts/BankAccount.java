package ru.babenko.models.bankAccounts;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface BankAccount {
    BankAccountOperationsResultType deposit(BigDecimal amount);

    BankAccountOperationsResultType withdraw(BigDecimal amount);

    BankAccountOperationsResultType sendMoney(BigDecimal amount);

    BankAccountOperationsResultType receiveMoney(BigDecimal amount);

    BigInteger getAccountNumber();

    void verify();

    void block();

    User getUser();

    Bank getBank();

    BigDecimal getAmount();

    BankAccountOperationsResultType setAmount(BigDecimal amount);
}
