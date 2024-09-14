package ru.babenko.models.bankAccounts.states;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;

public class UnverifiedState implements StatusState {
    private final BankAccount bankAccount;

    private final BigDecimal withdrawLimit;

    private final BigDecimal transferLimit;

    public UnverifiedState(BankAccount bankAccount, BigDecimal withdrawLimit, BigDecimal transferLimit) {
        this.bankAccount = bankAccount;
        this.withdrawLimit = withdrawLimit;
        this.transferLimit = transferLimit;
    }

    @Override
    public BankAccountOperationsResultType deposit(BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return new BankAccountOperationsResultType.Failure("Amount should be greater than 0");

        return bankAccount.setAmount(bankAccount.getAmount().add(amount));
    }

    @Override
    public BankAccountOperationsResultType withdraw(BigDecimal amount) {
        if (amount.compareTo(withdrawLimit) > 0)
            return new BankAccountOperationsResultType.Failure("Withdrawal limit exceeded");

        return bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
    }

    @Override
    public BankAccountOperationsResultType send(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BankAccountOperationsResultType.Failure("Amount should be greater than 0");
        }

        if (amount.compareTo(transferLimit) > 0)
            return new BankAccountOperationsResultType.Failure("Transfer limit exceeded");

        return bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
    }

    @Override
    public BankAccountOperationsResultType receive(BigDecimal amount) {
        return bankAccount.setAmount(bankAccount.getAmount().add(amount));
    }
}
