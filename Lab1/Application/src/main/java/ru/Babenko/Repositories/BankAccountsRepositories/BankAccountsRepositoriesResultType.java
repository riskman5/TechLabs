package ru.babenko.repositories.bankAccountsRepositories;

public interface BankAccountsRepositoriesResultType {
    record Success() implements BankAccountsRepositoriesResultType { }

    record Failure(String message) implements BankAccountsRepositoriesResultType { }
}
