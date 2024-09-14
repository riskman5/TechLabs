package ru.babenko.application.managers;

import org.springframework.stereotype.Service;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.users.User;

import java.util.Optional;

@Service
public class CurrentUserManager implements CurrentUserService {

    private Optional<User> currentUser;

    public CurrentUserManager() {
        currentUser = Optional.empty();
    }

    @Override
    public Optional<User> getUser() {
        return currentUser;
    }

    @Override
    public void setUser(User user) {
        if (user == null)
            return;
        currentUser = Optional.of(user);
    }
}
