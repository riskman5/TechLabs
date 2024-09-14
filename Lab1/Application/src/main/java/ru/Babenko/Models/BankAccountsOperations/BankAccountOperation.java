package ru.babenko.models.bankAccountsOperations;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface BankAccountOperation {
    void revertOperation();

    UUID getOperationId();

    List<BankAccountOperation> getConjugateOperations();

    BigInteger getBankAccountNumber();
}
