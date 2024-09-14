import ru.babenko.application.bankAccounts.BankAccountServiceImpl;

import org.junit.Test;
import ru.babenko.application.managers.CurrentBankAccountManager;
import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.centralBank.CentralBankService;
import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.bankAccountsOperations.DepositOperation;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DepositTest {
    @Test
    public void test_deposit_succeeds_with_positive_amount() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType successResult = new BankAccountOperationsResultType.SuccessWithAmount(BigDecimal.TEN);
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.deposit(any(BigDecimal.class))).thenReturn(successResult);

        BankAccountsOperationsRepository bankAccountOperationsRepository = mock(BankAccountsOperationsRepository.class);
        CentralBankService centralBankService = mock(CentralBankService.class);
        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, centralBankService, bankAccountOperationsRepository, null);

        BankAccountOperationsResultType result = bankAccountService.deposit(BigDecimal.TEN);

        assertEquals(successResult, result);
        verify(bankAccountOperationsRepository, times(1)).addOperation(any(DepositOperation.class));
    }

    @Test
    public void test_deposit_fails_with_negative_amount() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType failureResult = new BankAccountOperationsResultType.Failure("Invalid amount");
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.deposit(any(BigDecimal.class))).thenReturn(failureResult);

        BankAccountsOperationsRepository bankAccountOperationsRepository = mock(BankAccountsOperationsRepository.class);
        CentralBankService centralBankService = mock(CentralBankService.class);
        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, centralBankService, bankAccountOperationsRepository, null);

        BankAccountOperationsResultType result = bankAccountService.deposit(BigDecimal.valueOf(-10));

        assertEquals(failureResult, result);
        verify(bankAccountOperationsRepository, never()).addOperation(any(DepositOperation.class));
    }

}