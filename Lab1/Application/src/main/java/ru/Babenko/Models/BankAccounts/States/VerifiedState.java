package ru.babenko.models.bankAccounts.states;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;

public class VerifiedState implements StatusState {
    private final BankAccount bankAccount;

    public VerifiedState(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public BankAccountOperationsResultType deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BankAccountOperationsResultType.Failure("Amount should be greater than 0");
        }

        return bankAccount.setAmount(bankAccount.getAmount().add(amount));
    }

    @Override
    public BankAccountOperationsResultType withdraw(BigDecimal amount) {
        return bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
    }

    @Override
    public BankAccountOperationsResultType send(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BankAccountOperationsResultType.Failure("Amount should be greater than 0");
        }

        return bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
    }

    @Override
    public BankAccountOperationsResultType receive(BigDecimal amount) {
        return null;
    }
}
