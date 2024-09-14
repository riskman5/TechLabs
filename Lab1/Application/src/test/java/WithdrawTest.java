import ru.babenko.application.bankAccounts.BankAccountServiceImpl;

import org.junit.Test;
import ru.babenko.application.managers.CurrentBankAccountManager;
import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.models.bankAccounts.BankAccount;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WithdrawTest {
    @Test
    public void test_withdraw_valid_amount() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType successResult = new BankAccountOperationsResultType.Success();
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.withdraw(any(BigDecimal.class))).thenReturn(successResult);

        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, null, null, null);

        BankAccountOperationsResultType result = bankAccountService.withdraw(BigDecimal.TEN);

        assertEquals(successResult, result);
        verify(bankAccount, times(1)).withdraw(BigDecimal.TEN);
    }

    @Test
    public void test_withdraw_entire_amount() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType successResult = new BankAccountOperationsResultType.Success();
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.withdraw(any(BigDecimal.class))).thenReturn(successResult);

        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, null, null, null);

        BankAccountOperationsResultType result = bankAccountService.withdraw(BigDecimal.TEN);

        assertEquals(successResult, result);
        verify(bankAccount, times(1)).withdraw(BigDecimal.TEN);
    }

    @Test
    public void test_withdraw_negative_amount() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType failureResult = new BankAccountOperationsResultType.Failure("Invalid amount");
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.withdraw(any(BigDecimal.class))).thenReturn(failureResult);

        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, null, null, null);

        BankAccountOperationsResultType result = bankAccountService.withdraw(BigDecimal.valueOf(-10));

        assertEquals(failureResult, result);
        verify(bankAccount, times(1)).withdraw(BigDecimal.valueOf(-10));
    }

    @Test
    public void test_withdraw_greater_than_balance() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType failureResult = new BankAccountOperationsResultType.Failure("Insufficient balance");
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.withdraw(any(BigDecimal.class))).thenReturn(failureResult);

        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, null, null, null);

        BankAccountOperationsResultType result = bankAccountService.withdraw(BigDecimal.valueOf(100));

        assertEquals(failureResult, result);
        verify(bankAccount, times(1)).withdraw(BigDecimal.valueOf(100));
    }

    @Test
    public void test_withdraw_greater_than_maximum_allowed() {
        CurrentBankAccountManager currentBankAccountManager = mock(CurrentBankAccountManager.class);
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountOperationsResultType failureResult = new BankAccountOperationsResultType.Failure("Exceeded maximum withdrawal limit");
        when(currentBankAccountManager.getBankAccount()).thenReturn(Optional.of(bankAccount));
        when(bankAccount.withdraw(any(BigDecimal.class))).thenReturn(failureResult);

        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(currentBankAccountManager, null, null, null);

        BankAccountOperationsResultType result = bankAccountService.withdraw(BigDecimal.valueOf(1000));

        assertEquals(failureResult, result);
        verify(bankAccount, times(1)).withdraw(BigDecimal.valueOf(1000));
    }

}