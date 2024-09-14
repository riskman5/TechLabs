package ru.babenko.models.bankAccounts.builders;

import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.bankAccounts.CreditBankAccount;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.users.BankUser;
import ru.babenko.models.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CreditBankAccountBuilder {
    private BigInteger accountNumber;
    private User user;
    private Bank bank;
    private BigDecimal yearlyCommission;
    private BigDecimal unverifiedAccountWithdrawLimit;
    private BigDecimal unverifiedAccountTransferLimit;

    public CreditBankAccountBuilder withAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public CreditBankAccountBuilder withBankUser(BankUser bankUser) {
        user = bankUser;
        return this;
    }

    public CreditBankAccountBuilder withBank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public CreditBankAccountBuilder withUnverifiedAccountWithdrawLimit(BigDecimal limit) {
        unverifiedAccountWithdrawLimit = limit;
        return this;
    }

    public CreditBankAccountBuilder withUnverifiedAccountTransferLimit(BigDecimal limit) {
        unverifiedAccountTransferLimit = limit;
        return this;
    }

    public CreditBankAccountBuilder withYearlyCommission(BigDecimal yearlyCommission) {
        this.yearlyCommission = yearlyCommission;
        return this;
    }


    public BankAccount build() {
        return new CreditBankAccount(accountNumber, user, bank, yearlyCommission, unverifiedAccountWithdrawLimit, unverifiedAccountTransferLimit);
    }
}
