package ru.babenko.contracts.authorization;

public interface AuthorizationService {
    AuthorizationResultType login(String name, String Surname);
    AuthorizationResultType logout();
}
