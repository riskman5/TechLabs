package ru.babenko.models.bankAccountsOperations;

import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepositOperation implements BankAccountOperation {
    private final UUID id;
    private final BankAccount bankAccount;
    private final BigDecimal amount;

    public DepositOperation(BankAccount bankAccount, BigDecimal amount) {
        id = UUID.randomUUID();
        this.bankAccount = bankAccount;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Deposit{"
                + "id="
                + id
                + "amount="
                + amount
                + '}';
    }

    @Override
    public void revertOperation() {
        bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
    }

    @Override
    public UUID getOperationId() {
        return id;
    }

    @Override
    public List<BankAccountOperation> getConjugateOperations() {
        return new ArrayList<>();
    }

    @Override
    public BigInteger getBankAccountNumber() {
        return bankAccount.getAccountNumber();
    }
}
