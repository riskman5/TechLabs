package ru.babenko.models.bankAccountsOperations;

import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class TransferOperation implements BankAccountOperation {
    private final UUID operationId;
    private final BankAccount bankAccount;
    private final BankAccount anotherBankAccount;
    private final BigDecimal amount;
    private final TransferOperationType operationType;
    private TransferOperation conjugateOperation;

    public TransferOperation(BankAccount bankAccount, BankAccount anotherBankAccount, BigDecimal amount, TransferOperationType operationType) {
        operationId = UUID.randomUUID();
        this.bankAccount = bankAccount;
        this.anotherBankAccount = anotherBankAccount;
        this.amount = amount;
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "Transfer{"
                + "id="
                + operationId
                + "anotherBankAccountNumber="
                + anotherBankAccount
                + ", amount="
                + amount
                + '}';
    }

    public void revertOperation() {
        bankAccount.setAmount(bankAccount.getAmount().add(amount));
        anotherBankAccount.setAmount(anotherBankAccount.getAmount().subtract(amount));
    }

    @Override
    public UUID getOperationId() {
        return operationId;
    }

    @Override
    public List<BankAccountOperation> getConjugateOperations() {
        return List.of(conjugateOperation);
    }

    @Override
    public BigInteger getBankAccountNumber() {
        return bankAccount.getAccountNumber();
    }

    public void setConjugateOperation(TransferOperation conjugateOperation) {
        this.conjugateOperation = conjugateOperation;
    }
}
