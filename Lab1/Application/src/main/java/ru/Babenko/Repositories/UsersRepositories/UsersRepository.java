package ru.babenko.repositories.usersRepositories;

import ru.babenko.models.users.User;

import java.util.Optional;

public interface UsersRepository {
    Optional<User> findUserByFullName(String Name, String Surname);

    UsersRepositoriesResultType addUser(User user);

    void removeUser(User user);
}
