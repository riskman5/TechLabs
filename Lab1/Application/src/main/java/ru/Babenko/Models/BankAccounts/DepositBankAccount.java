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


public class DepositBankAccount implements BankAccount {
    private final BigInteger accountNumber;
    private final User user;
    private final Bank bank;
    private BigDecimal amount;
    private StatusState statusState;
    private BigDecimal yearlyInterestRate;
    private BigDecimal minDailyAmount;
    private BigDecimal unpaidInterest;
    private int daysWithoutPayingInterest;

    public DepositBankAccount(BigInteger accountNumber,
                              User user,
                              Bank bank,
                              BigDecimal yearlyInterestRate,
                              BigDecimal unverifiedAccountWithdrawLimit,
                              BigDecimal unverifiedAccountTransferLimit) {
        this.accountNumber = accountNumber;
        this.user = user;
        this.bank = bank;
        amount = BigDecimal.ZERO;
        statusState = user.isVerified()
                ? new VerifiedState(this)
                : new UnverifiedState(this, unverifiedAccountWithdrawLimit, unverifiedAccountTransferLimit);
        this.yearlyInterestRate = yearlyInterestRate;
        minDailyAmount = BigDecimal.ZERO;
        unpaidInterest = BigDecimal.ZERO;
        daysWithoutPayingInterest = 0;
    }

    @Override
    public BankAccountOperationsResultType deposit(BigDecimal amount) {
        return statusState.deposit(amount);
    }

    @Override
    public BankAccountOperationsResultType withdraw(BigDecimal amount) {
        return new BankAccountOperationsResultType.Failure("You can't withdraw from deposit account");
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
        return null;
    }

    public void setYearlyInterestRate(BigDecimal yearlyInterestRate) {
        this.yearlyInterestRate = yearlyInterestRate;
    }

    private void addDailyInterest() {
        BigDecimal interest = minDailyAmount.multiply(yearlyInterestRate).divide(BigDecimal.valueOf(365));
        unpaidInterest = unpaidInterest.add(interest);
        minDailyAmount = amount;
    }

    public void payInterest() {
        amount = amount.add(unpaidInterest);
        unpaidInterest = BigDecimal.ZERO;
    }

    public BankAccountOperationsResultType skipDays(int days) {
        if (days < 0)
            return new BankAccountOperationsResultType.Failure("Days count can't be negative");

        for (int i = 0; i < days; i++) {
            addDailyInterest();
            daysWithoutPayingInterest++;
            if (daysWithoutPayingInterest == 30) {
                payInterest();
                daysWithoutPayingInterest = 0;
            }
        }

        return new BankAccountOperationsResultType.SuccessWithAmount(amount);
    }
}
