package ru.babenko.dataAccess.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.babenko.models.banks.CentralBank;
import ru.babenko.models.users.CentralBankWorker;
import ru.babenko.models.users.User;
import ru.babenko.repositories.usersRepositories.UsersRepositoriesResultType;
import ru.babenko.repositories.usersRepositories.UsersRepository;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
    private final Map<String, User> _users;

    public UsersRepositoryImpl() {
        _users = new HashMap<String, User>() {
            {
                put("Sergey Babenko", new CentralBankWorker("Sergey", "Babenko", CentralBank.getInstance()));
            }
        };
    }

    @Override
    public Optional<User> findUserByFullName(String name, String surname) {
        String fullName = name + " " + surname;
        if (!_users.containsKey(fullName)) {
            return Optional.empty();
        }
        return Optional.ofNullable(_users.get(fullName));
    }

    @Override
    public UsersRepositoriesResultType addUser(User user) {
        if (_users.containsKey(user.getName() + " " + user.getSurname())) {
            return new UsersRepositoriesResultType.Failure("User with name " + user.getName() + " and surname " + user.getSurname() + " already exists");
        }
        _users.put(user.getName() + " " + user.getSurname(), user);
        return new UsersRepositoriesResultType.Success();
    }

    @Override
    public void removeUser(User user) {
        _users.remove(user.getName() + " " + user.getSurname());
    }
}
