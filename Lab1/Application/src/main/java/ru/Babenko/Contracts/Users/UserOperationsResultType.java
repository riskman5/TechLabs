package ru.babenko.contracts.users;

import ru.babenko.models.bankAccounts.BankAccount;

public interface UserOperationsResultType {
    record Success() implements UserOperationsResultType { }

    record SuccessWithBankAccounts(Iterable<BankAccount> Accounts) implements UserOperationsResultType { }

    record Failure(String message) implements UserOperationsResultType { }
}
