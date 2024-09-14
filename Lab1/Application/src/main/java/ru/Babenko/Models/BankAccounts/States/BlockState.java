package ru.babenko.models.bankAccounts.states;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;

public class BlockState implements StatusState{
    private final BankAccount bankAccount;

    public BlockState(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public BankAccountOperationsResultType deposit(BigDecimal amount) {
        return new BankAccountOperationsResultType.Failure("Account is blocked");
    }

    @Override
    public BankAccountOperationsResultType withdraw(BigDecimal amount) {
        return new BankAccountOperationsResultType.Failure("Account is blocked");
    }

    @Override
    public BankAccountOperationsResultType send(BigDecimal amount) {
        return new BankAccountOperationsResultType.Failure("Account is blocked");
    }

    @Override
    public BankAccountOperationsResultType receive(BigDecimal amount) {
        return new BankAccountOperationsResultType.Failure("Account is blocked");
    }
}
