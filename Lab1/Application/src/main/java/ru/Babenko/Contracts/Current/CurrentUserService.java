package ru.babenko.contracts.current;

import ru.babenko.models.users.User;

import java.util.Optional;

public interface CurrentUserService {
    Optional<User> getUser();
    void setUser(User user);
}
