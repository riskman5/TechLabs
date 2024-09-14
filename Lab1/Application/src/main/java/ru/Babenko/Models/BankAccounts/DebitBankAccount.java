package ru.babenko.models.bankAccounts;

import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.models.bankAccounts.states.BlockState;
import ru.babenko.models.bankAccounts.states.StatusState;
import ru.babenko.models.bankAccounts.states.UnverifiedState;
import ru.babenko.models.bankAccounts.states.VerifiedState;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DebitBankAccount implements BankAccount {
    private final BigInteger accountNumber;
    private final User user;
    private final Bank bank;
    private BigDecimal amount;
    private StatusState statusState;

    public DebitBankAccount(BigInteger accountNumber,
                            User user,
                            Bank bank,
                            BigDecimal unverifiedAccountWithdrawLimit,
                            BigDecimal unverifiedAccountTransferLimit) {
        this.accountNumber = accountNumber;
        this.user = user;
        this.bank = bank;
        statusState = this.user.isVerified()
                ? new VerifiedState(this)
                : new UnverifiedState(this, unverifiedAccountWithdrawLimit, unverifiedAccountTransferLimit);
        amount = BigDecimal.ZERO;
    }

    @Override
    public BankAccountOperationsResultType deposit(BigDecimal amount) {
        return statusState.deposit(amount);
    }

    @Override
    public BankAccountOperationsResultType withdraw(BigDecimal amount) {
        return statusState.withdraw(amount);
    }

    @Override
    public BankAccountOperationsResultType sendMoney(BigDecimal amount) {
        return statusState.send(amount);
    }

    @Override
    public BankAccountOperationsResultType receiveMoney(BigDecimal amount) {
        return statusState.receive(amount);
    }

       @Override
    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void verify() {
        statusState = new VerifiedState(this);
    }

    @Override
    public void block() {
        statusState = new BlockState(this);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public BankAccountOperationsResultType setAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new BankAccountOperationsResultType.Failure("Amount should be greater than 0");
        }

        this.amount = amount;
        return new BankAccountOperationsResultType.Success();
    }
}
