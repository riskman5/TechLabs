package ru.babenko.contracts.bankAccounts;

import ru.babenko.models.bankAccountsOperations.BankAccountOperation;

import java.math.BigDecimal;

public interface BankAccountOperationsResultType {
    record Success() implements BankAccountOperationsResultType { }

    record SuccessWithAmount(BigDecimal balance) implements BankAccountOperationsResultType { }

    record SuccessWithOperations(Iterable<BankAccountOperation> operations) implements BankAccountOperationsResultType { }

    record Failure(String message) implements BankAccountOperationsResultType { }
}
