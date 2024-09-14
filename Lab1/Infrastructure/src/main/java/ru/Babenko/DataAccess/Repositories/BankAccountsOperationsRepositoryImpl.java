package ru.babenko.dataAccess.repositories;

import ru.babenko.models.bankAccountsOperations.BankAccountOperation;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepositoryResultType;

import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Repository
public class BankAccountsOperationsRepositoryImpl implements BankAccountsOperationsRepository {
        private final Map<UUID, BankAccountOperation> operationsById;
        private final Map<BigInteger, Set<BankAccountOperation>> operationsByAccountNumber;

    public BankAccountsOperationsRepositoryImpl() {
        operationsById = new HashMap<UUID, BankAccountOperation>();
        operationsByAccountNumber = new HashMap<BigInteger, Set<BankAccountOperation>>();
    }

    @Override
    public BankAccountsOperationsRepositoryResultType addOperation(BankAccountOperation operation) {
        operationsById.put(operation.getOperationId(), operation);

        if (operationsByAccountNumber.containsKey(operation.getBankAccountNumber())) {
            operationsByAccountNumber.get(operation.getBankAccountNumber()).add(operation);
        }
        else {
            operationsByAccountNumber.put(operation.getBankAccountNumber(), Set.of(operation));
        }

        return new BankAccountsOperationsRepositoryResultType.Success();
    }

    @Override
    public BankAccountsOperationsRepositoryResultType removeOperation(BankAccountOperation operation) {
        operationsById.remove(operation.getOperationId());

        if (operationsByAccountNumber.containsKey(operation.getBankAccountNumber())) {
            operationsByAccountNumber.get(operation.getBankAccountNumber()).remove(operation);
        }

        return new BankAccountsOperationsRepositoryResultType.Success();
    }

    @Override
    public BankAccountsOperationsRepositoryResultType findOperationsById(UUID accountId) {
        if (operationsById.containsKey(accountId)) {
            return new BankAccountsOperationsRepositoryResultType.SuccessWithOperation(operationsById.get(accountId));
        }

        return new BankAccountsOperationsRepositoryResultType.NotFound();
    }

    @Override
    public BankAccountsOperationsRepositoryResultType findOperationsByAccountNumber(BigInteger accountId) {
        if (operationsByAccountNumber.containsKey(accountId)) {
            return new BankAccountsOperationsRepositoryResultType.SuccessWithOperations(operationsByAccountNumber.get(accountId));
        }

        return new BankAccountsOperationsRepositoryResultType.NotFound();
    }
}
