import org.junit.Test;
import ru.babenko.application.banks.BankServiceImpl;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.models.bankAccountsOperations.BankAccountOperation;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepositoryResultType;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeclineOperationTest {
    @Test
    public void test_valid_operationId() {
        UUID operationId = UUID.randomUUID();
        BankAccountOperation operation = mock(BankAccountOperation.class);
        when(operation.getOperationId()).thenReturn(operationId);
        var successResult = new BankAccountsOperationsRepositoryResultType.SuccessWithOperation(operation);
        var bankAccountsOperationsRepository = mock(BankAccountsOperationsRepository.class);
        when(bankAccountsOperationsRepository.findOperationsById(operationId)).thenReturn(successResult);

        var bankService = new BankServiceImpl(null, bankAccountsOperationsRepository, null, null);
        BankOperationsResultType result = bankService.declineOperationById(operationId);

        verify(operation, times(1)).revertOperation();
        verify(operation, times(1)).getConjugateOperations();
        verify(bankAccountsOperationsRepository, times(1)).findOperationsById(operationId);
        assertEquals(new BankOperationsResultType.Success(), result);
    }

    @Test
    public void test_transfer_operation_target_account_not_exist() {
        UUID operationId = UUID.randomUUID();
        BankAccountOperation operation = mock(BankAccountOperation.class);
        when(operation.getOperationId()).thenReturn(operationId);
        var notFoundResult = new BankAccountsOperationsRepositoryResultType.NotFound();
        var bankAccountsOperationsRepository = mock(BankAccountsOperationsRepository.class);
        when(bankAccountsOperationsRepository.findOperationsById(operationId)).thenReturn(notFoundResult);

        var bankService = new BankServiceImpl(null, bankAccountsOperationsRepository, null, null);
        BankOperationsResultType result = bankService.declineOperationById(operationId);

        verify(bankAccountsOperationsRepository, times(1)).findOperationsById(operationId);
        assertEquals(new BankOperationsResultType.Failure("Operation not found"), result);
    }

    @Test
    public void test_valid_transfer_operation() {
        UUID operationId = UUID.randomUUID();
        BankAccountOperation operation = mock(BankAccountOperation.class);
        BankAccountOperation conjugateOperation = mock(BankAccountOperation.class);
        when(operation.getOperationId()).thenReturn(operationId);
        var successResult = new BankAccountsOperationsRepositoryResultType.SuccessWithOperation(operation);
        var bankAccountsOperationsRepository = mock(BankAccountsOperationsRepository.class);
        when(bankAccountsOperationsRepository.findOperationsById(operationId)).thenReturn(successResult);
        when(operation.getConjugateOperations()).thenReturn(Collections.singletonList(conjugateOperation));

        var bankService = new BankServiceImpl(null, bankAccountsOperationsRepository, null, null);
        BankOperationsResultType result = bankService.declineOperationById(operationId);

        verify(operation, times(1)).revertOperation();
        verify(operation, times(1)).getConjugateOperations();
        verify(conjugateOperation, times(1)).revertOperation();
        verify(bankAccountsOperationsRepository, times(1)).findOperationsById(operationId);
        assertEquals(new BankOperationsResultType.Success(), result);
    }

}