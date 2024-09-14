import ru.babenko.application.centralBank.CentralBankServiceImpl;

import org.junit.Test;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.banks.Bank;
import ru.babenko.models.banks.CentralBank;
import ru.babenko.models.users.CentralBankWorker;
import ru.babenko.models.users.User;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepository;
import ru.babenko.repositories.banksRepositories.BanksRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BankCreationTest {
    @Test
    public void test_create_bank_success() {
        BanksRepository banksRepository = mock(BanksRepository.class);
        BankAccountsRepository bankAccountsRepository = mock(BankAccountsRepository.class);
        CurrentUserService currentUserService = mock(CurrentUserService.class);
        BankAccountsOperationsRepository operationsRepository = mock(BankAccountsOperationsRepository.class);
        CentralBankServiceImpl centralBankService = new CentralBankServiceImpl(banksRepository, bankAccountsRepository, currentUserService, operationsRepository);
    
        CentralBankWorker centralBankWorker = new CentralBankWorker("John", "Doe", CentralBank.getInstance());
        when(currentUserService.getUser()).thenReturn(Optional.of(centralBankWorker));
    
        BankOperationsResultType result = centralBankService.createBank("New Bank");
    
        assertTrue(result instanceof BankOperationsResultType.Success);
        verify(banksRepository, times(1)).addBank(any(Bank.class));
    }

    @Test
    public void test_create_bank_failure() {
        BanksRepository banksRepository = mock(BanksRepository.class);
        BankAccountsRepository bankAccountsRepository = mock(BankAccountsRepository.class);
        CurrentUserService currentUserService = mock(CurrentUserService.class);
        BankAccountsOperationsRepository operationsRepository = mock(BankAccountsOperationsRepository.class);
        CentralBankServiceImpl centralBankService = new CentralBankServiceImpl(banksRepository, bankAccountsRepository, currentUserService, operationsRepository);
    
        User user = mock(User.class);
        when(currentUserService.getUser()).thenReturn(Optional.of(user));
    
        BankOperationsResultType result = centralBankService.createBank("New Bank");
    
        assertTrue(result instanceof BankOperationsResultType.Failure);
        assertEquals("You are not authorized to create a bank", ((BankOperationsResultType.Failure) result).message());
        verify(banksRepository, never()).addBank(any(Bank.class));
    }

}