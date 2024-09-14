package ru.babenko.models.bankAccounts.states;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;

import java.math.BigDecimal;

public interface StatusState {
    BankAccountOperationsResultType deposit(BigDecimal amount);

    BankAccountOperationsResultType withdraw(BigDecimal amount);

    BankAccountOperationsResultType send(BigDecimal amount);

    BankAccountOperationsResultType receive(BigDecimal amount);
}
