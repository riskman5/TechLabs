package ru.babenko.repositories.banksRepositories;

public interface BanksRepositoriesResultType {
    record Success() implements BanksRepositoriesResultType {
    }

    record Failure(String message) implements BanksRepositoriesResultType {
    }
}
