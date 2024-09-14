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

public class CreditBankAccount implements BankAccount {
    private final BigInteger accountNumber;
    private final User user;
    private final Bank bank;
    private BigDecimal amount;
    private StatusState statusState;
    private BigDecimal yearlyCommission;

    public CreditBankAccount(BigInteger accountNumber,
                             User user,
                             Bank bank,
                             BigDecimal yearlyCommission,
                             BigDecimal unverifiedAccountWithdrawLimit,
                             BigDecimal unverifiedAccountTransferLimit) {
        this.accountNumber = accountNumber;
        this.user = user;
        amount = BigDecimal.ZERO;
        statusState = user.isVerified()
                ? new VerifiedState(this)
                : new UnverifiedState(this, unverifiedAccountWithdrawLimit, unverifiedAccountTransferLimit);
        this.bank = bank;
        this.yearlyCommission = yearlyCommission;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Bank getBank() {
        return bank;
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
    public BigDecimal getAmount() {
        return amount;
    }

    public void setYearlyCommission(BigDecimal yearlyCommission) {
        this.yearlyCommission = yearlyCommission;
    }

    public void addCommission(BigDecimal commission) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            return;
        }

        amount = amount.subtract(amount.multiply(yearlyCommission).divide(BigDecimal.valueOf(100)));
    }
    public BankAccountOperationsResultType setAmount(BigDecimal amount) {
        this.amount = amount;
        return new BankAccountOperationsResultType.Success();
    };
}
