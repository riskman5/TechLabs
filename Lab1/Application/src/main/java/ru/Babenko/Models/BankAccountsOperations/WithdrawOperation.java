package ru.babenko.models.bankAccountsOperations;

import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class WithdrawOperation implements BankAccountOperation {
    private final UUID operationId;
    private final BankAccount bankAccount;
    private final BigDecimal amount;

    public WithdrawOperation(BankAccount bankAccount, BigDecimal amount) {
        operationId = UUID.randomUUID();
        this.bankAccount = bankAccount;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Deposit{"
                + "id="
                + operationId
                + "amount="
                + amount
                + '}';
    }

    @Override
    public void revertOperation() {
        bankAccount.setAmount(bankAccount.getAmount().add(amount));
    }

    @Override
    public UUID getOperationId() {
        return operationId;
    }

    @Override
    public List<BankAccountOperation> getConjugateOperations() {
        return List.of();
    }

    @Override
    public BigInteger getBankAccountNumber() {
        return bankAccount.getAccountNumber();
    }
}
