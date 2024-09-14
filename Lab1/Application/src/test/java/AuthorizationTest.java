import ru.babenko.application.authorization.AuthorizationServiceImpl;

import org.junit.Test;
import ru.babenko.contracts.authorization.AuthorizationResultType;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.banks.BankImpl;
import ru.babenko.models.users.BankUser;
import ru.babenko.models.users.User;
import ru.babenko.repositories.usersRepositories.UsersRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationTest {
    @Test
    public void test_valid_name_and_surname_returns_success() {
        Bank bank = new BankImpl("Cool Bank");
        User user = new BankUser("John", "Doe", bank);
        UsersRepository usersRepository = mock(UsersRepository.class);
        when(usersRepository.findUserByFullName("John", "Doe")).thenReturn(Optional.of(user));
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl(mock(CurrentUserService.class), mock(CurrentBankAccountService.class), usersRepository);

        AuthorizationResultType result = authorizationService.login("John", "Doe");

        assertTrue(result instanceof AuthorizationResultType.Success);
    }

    @Test
    public void test_empty_name_and_surname_returns_not_found() {
        UsersRepository usersRepository = mock(UsersRepository.class);
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl(mock(CurrentUserService.class), mock(CurrentBankAccountService.class), usersRepository);

        AuthorizationResultType result = authorizationService.login("", "");

        assertTrue(result instanceof AuthorizationResultType.NotFound);
    }

}