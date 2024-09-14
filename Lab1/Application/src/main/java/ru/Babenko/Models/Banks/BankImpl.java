package ru.babenko.models.banks;

import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.models.users.BankUser;
import ru.babenko.models.users.User;

import java.math.BigDecimal;
import java.util.Optional;

public class BankImpl implements Bank {
    private final String name;
    private Optional<BigDecimal> interestRate;
    private Optional<BigDecimal> commission;
    private Optional<BigDecimal> unverifiedAccountWithdrawLimit;

    private Optional<BigDecimal> unverifiedAccountTransferLimit;

    public BankImpl(String name) {
        this.name = name;
    }

    public User CreateUser(String name, String surname) {
        return new BankUser(name, surname, this);
    }

    public BankOperationsResultType setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
            return new BankOperationsResultType.Failure("Interest rate cannot be negative");
        }
        this.interestRate = Optional.of(interestRate);
        return new BankOperationsResultType.Success();
    }

    public BankOperationsResultType setCommission(BigDecimal commission) {
        if (commission.compareTo(BigDecimal.ZERO) < 0) {
            return new BankOperationsResultType.Failure("Commission cannot be negative");
        }
        this.commission = Optional.of(commission);
        return new BankOperationsResultType.Success();
    }

    public BankOperationsResultType SetTransferLimit(BigDecimal transferLimit) {
        if (transferLimit.compareTo(BigDecimal.ZERO) < 0) {
            return new BankOperationsResultType.Failure("Limit cannot be negative");
        }
        unverifiedAccountTransferLimit = Optional.of(transferLimit);
        return new BankOperationsResultType.Success();
    }

    public BankOperationsResultType SetUnverifiedAccountWithdrawLimit(BigDecimal limit) {
        if (limit.compareTo(BigDecimal.ZERO) < 0) {
            return new BankOperationsResultType.Failure("Limit cannot be negative");
        }
        unverifiedAccountWithdrawLimit = Optional.of(limit);
        return new BankOperationsResultType.Success();
    }

    public Optional<BigDecimal> GetUnverifiedAccountWithdrawLimit() {
        return unverifiedAccountWithdrawLimit;
    }

    public BankOperationsResultType SetUnverifiedAccountTransferLimit(BigDecimal limit) {
        if (limit.compareTo(BigDecimal.ZERO) < 0) {
            return new BankOperationsResultType.Failure("Limit cannot be negative");
        }
        unverifiedAccountTransferLimit = Optional.of(limit);
        return new BankOperationsResultType.Success();
    }

    public Optional<BigDecimal> GetUnverifiedAccountTransferLimit() {
        return unverifiedAccountTransferLimit;
    }

    public String getName() {
        return name;
    }

    public Optional<BigDecimal> GetInterestRate() {
        return interestRate;
    }

    public Optional<BigDecimal> GetCommission() {
        return commission;
    }
}
