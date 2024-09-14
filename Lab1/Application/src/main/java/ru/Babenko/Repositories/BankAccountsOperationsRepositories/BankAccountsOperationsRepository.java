package ru.babenko.repositories.bankAccountsOperationsRepositories;

import ru.babenko.models.bankAccountsOperations.BankAccountOperation;

import java.math.BigInteger;
import java.util.UUID;

public interface BankAccountsOperationsRepository {
    BankAccountsOperationsRepositoryResultType addOperation(BankAccountOperation operation);

    BankAccountsOperationsRepositoryResultType removeOperation(BankAccountOperation operation);

    BankAccountsOperationsRepositoryResultType findOperationsById(UUID accountId);

    BankAccountsOperationsRepositoryResultType findOperationsByAccountNumber(BigInteger accountId);
}
