package ru.babenko.repositories.bankAccountsOperationsRepositories;

import ru.babenko.models.bankAccountsOperations.BankAccountOperation;

import java.util.Set;

public interface BankAccountsOperationsRepositoryResultType {
    record Success() implements BankAccountsOperationsRepositoryResultType { }

    record SuccessWithOperation(BankAccountOperation operation) implements BankAccountsOperationsRepositoryResultType { }

    record SuccessWithOperations(Set<BankAccountOperation> operations) implements BankAccountsOperationsRepositoryResultType { }

    record NotFound() implements BankAccountsOperationsRepositoryResultType { }

    record Failure(String message) implements BankAccountsOperationsRepositoryResultType { }
}
