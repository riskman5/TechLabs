package ru.babenko.application.authorization;

import org.springframework.stereotype.Service;
import ru.babenko.contracts.authorization.AuthorizationResultType;
import ru.babenko.contracts.authorization.AuthorizationService;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.repositories.usersRepositories.UsersRepository;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final CurrentUserService currentUserService;
    private final CurrentBankAccountService currentBankAccountService;
    private final UsersRepository usersRepository;

    public AuthorizationServiceImpl(CurrentUserService currentUserService, CurrentBankAccountService currentBankAccountService, UsersRepository usersRepository) {
        this.currentUserService = currentUserService;
        this.currentBankAccountService = currentBankAccountService;
        this.usersRepository = usersRepository;
    }

    @Override
    public AuthorizationResultType login(String name, String Surname) {
        var user = usersRepository.findUserByFullName(name, Surname);

        if (user.isEmpty()) {
            return new ru.babenko.contracts.authorization.AuthorizationResultType.NotFound();
        }

        currentUserService.setUser(user.get());

        return new ru.babenko.contracts.authorization.AuthorizationResultType.Success();
    }

    @Override
    public AuthorizationResultType logout() {
        currentUserService.setUser(null);
        currentBankAccountService.setBankAccount(null);
        return new ru.babenko.contracts.authorization.AuthorizationResultType.Success();
    }
}
