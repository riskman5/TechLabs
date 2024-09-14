package ru.babenko.models.bankAccounts.builders;

import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.bankAccounts.DebitBankAccount;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.users.BankUser;
import ru.babenko.models.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DebitBankAccountBuilder {
    private BigInteger accountNumber;
    private User user;
    private Bank bank;
    private BigDecimal unverifiedAccountWithdrawLimit;
    private BigDecimal unverifiedAccountTransferLimit;

    public DebitBankAccountBuilder withAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public DebitBankAccountBuilder withBankUser(BankUser bankUser) {
        user = bankUser;
        return this;
    }

    public DebitBankAccountBuilder withBank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public DebitBankAccountBuilder withUnverifiedAccountWithdrawLimit(BigDecimal limit) {
        unverifiedAccountWithdrawLimit = limit;
        return this;
    }

    public DebitBankAccountBuilder withUnverifiedAccountTransferLimit(BigDecimal limit) {
        unverifiedAccountTransferLimit = limit;
        return this;
    }

    public BankAccount build() {
        return new DebitBankAccount(accountNumber, user, bank, unverifiedAccountWithdrawLimit, unverifiedAccountTransferLimit);
    }
}
