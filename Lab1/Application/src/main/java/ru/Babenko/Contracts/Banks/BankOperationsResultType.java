package ru.babenko.contracts.banks;

import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.bankAccountsOperations.BankAccountOperation;

public interface BankOperationsResultType {
    record Success() implements BankOperationsResultType { }

    record SuccessWithOperations(Iterable<BankAccountOperation> operations) implements BankOperationsResultType { }

    record SuccessWithBankAccount(BankAccount bankAccount) implements BankOperationsResultType { }

    record Failure(String message) implements BankOperationsResultType { }
}
