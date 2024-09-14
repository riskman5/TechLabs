package ru.babenko.repositories.usersRepositories;

public interface UsersRepositoriesResultType {
    record Success() implements UsersRepositoriesResultType {
    }

    record Failure(String message) implements UsersRepositoriesResultType {
    }
}
