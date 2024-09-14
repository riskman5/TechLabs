package ru.babenko.models.bankAccounts.builders;

import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.bankAccounts.DepositBankAccount;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.users.BankUser;
import ru.babenko.models.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DepositBankAccountBuilder {
    private BigInteger accountNumber;
    private User user;
    private Bank bank;
    private BigDecimal yearlyInterestRate;;
    private BigDecimal unverifiedAccountWithdrawLimit;
    private BigDecimal unverifiedAccountTransferLimit;

    public DepositBankAccountBuilder withAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public DepositBankAccountBuilder withBankUser(BankUser bankUser) {
        user = bankUser;
        return this;
    }

    public DepositBankAccountBuilder withBank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public DepositBankAccountBuilder withUnverifiedAccountWithdrawLimit(BigDecimal limit) {
        unverifiedAccountWithdrawLimit = limit;
        return this;
    }

    public DepositBankAccountBuilder withUnverifiedAccountTransferLimit(BigDecimal limit) {
        unverifiedAccountTransferLimit = limit;
        return this;
    }

    public DepositBankAccountBuilder withYearlyInterestRate(BigDecimal yearlyInterestRate) {
        this.yearlyInterestRate = yearlyInterestRate;
        return this;
    }

    public BankAccount build() {
        return new DepositBankAccount(accountNumber, user, bank, yearlyInterestRate, unverifiedAccountWithdrawLimit, unverifiedAccountTransferLimit);
    }
}
