package ru.babenko.contracts.authorization;

public interface AuthorizationResultType {
    record Success() implements AuthorizationResultType {}

    record NotFound() implements AuthorizationResultType {}
}
