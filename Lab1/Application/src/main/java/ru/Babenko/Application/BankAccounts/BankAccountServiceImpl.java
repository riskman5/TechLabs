package ru.babenko.application.bankAccounts;

import org.springframework.stereotype.Service;
import ru.babenko.application.managers.CurrentBankAccountManager;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.centralBank.CentralBankService;
import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.bankAccounts.BankAccountService;

import ru.babenko.models.bankAccounts.DepositBankAccount;
import ru.babenko.models.bankAccountsOperations.DepositOperation;
import ru.babenko.models.bankAccountsOperations.TransferOperation;
import ru.babenko.models.bankAccountsOperations.TransferOperationType;
import ru.babenko.models.bankAccountsOperations.WithdrawOperation;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepositoryResultType;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepository;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final CentralBankService centralBankService;
    private final CurrentBankAccountManager currentBankAccountManager;
    private final BankAccountsOperationsRepository bankAccountOperationsRepository;
    private final BankAccountsRepository bankAccountsRepository;

    public BankAccountServiceImpl(CurrentBankAccountManager currentBankAccountManager, CentralBankService centralBankService, BankAccountsOperationsRepository bankAccountOperationsRepository, BankAccountsRepository bankAccountsRepository) {
        this.centralBankService = centralBankService;
        this.currentBankAccountManager = currentBankAccountManager;
        this.bankAccountOperationsRepository = bankAccountOperationsRepository;
        this.bankAccountsRepository = bankAccountsRepository;
    }

    @Override
    public BankAccountOperationsResultType deposit(BigDecimal amount) {
        var result = currentBankAccountManager.getBankAccount().get().deposit(amount);
        if (result instanceof BankAccountOperationsResultType.SuccessWithAmount) {
            bankAccountOperationsRepository.addOperation(new DepositOperation(currentBankAccountManager.getBankAccount().get(), amount));
        }
        return result;
    }

    @Override
    public BankAccountOperationsResultType withdraw(BigDecimal amount) {
        var result = currentBankAccountManager.getBankAccount().get().withdraw(amount);
        if (result instanceof BankAccountOperationsResultType.SuccessWithAmount) {
            bankAccountOperationsRepository.addOperation(new WithdrawOperation(currentBankAccountManager.getBankAccount().get(), amount.negate()));
        }
        return result;
    }

    @Override
    public BankAccountOperationsResultType checkBalance() {
        var amount = currentBankAccountManager.getBankAccount().get().getAmount();
        return new BankAccountOperationsResultType.SuccessWithAmount(amount);
    }

    @Override
    public BankAccountOperationsResultType transfer(BigInteger anotherBankAccountNumber, BigDecimal amount) {
        var bankAccount = currentBankAccountManager.getBankAccount().get();

        var anotherBankAccount = bankAccountsRepository.findBankAccountByNumber(anotherBankAccountNumber);

        if (anotherBankAccount.isEmpty()) {
            return new BankAccountOperationsResultType.Failure("Account not found");
        }

        if (anotherBankAccount.get().getBank() != currentBankAccountManager.getBankAccount().get().getBank()) {

            var result = centralBankService.transferBetweenBankAccounts(bankAccount.getAccountNumber(), anotherBankAccountNumber, amount);

            if (result instanceof BankOperationsResultType.Success) {
                return new BankAccountOperationsResultType.Success();
            } else if (result instanceof BankOperationsResultType.Failure failureResult) {
                return new BankAccountOperationsResultType.Failure(failureResult.message());
            }
            return new BankAccountOperationsResultType.Failure("Something went wrong. Please try again.");
        }

        var transferResult = bankAccount.sendMoney(amount);
        if (transferResult instanceof BankAccountOperationsResultType.Success) {
            var firstOperation = new TransferOperation(bankAccount, anotherBankAccount.get(), amount, TransferOperationType.SENT);

            transferResult = anotherBankAccount.get().receiveMoney(amount);
            if (transferResult instanceof BankAccountOperationsResultType.Success) {
                var secondOperation = new TransferOperation(anotherBankAccount.get(), bankAccount, amount, TransferOperationType.RECEIVED);

                bankAccountOperationsRepository.addOperation(firstOperation);
                bankAccountOperationsRepository.addOperation(secondOperation);

                return new BankAccountOperationsResultType.Success();
            }

            firstOperation.revertOperation();
        }

        return new BankAccountOperationsResultType.Failure("Something went wrong. Please try again.");
    }

    @Override
    public BankAccountOperationsResultType skipDays(int days) {
        if (currentBankAccountManager.getBankAccount().get() instanceof DepositBankAccount depositBankAccount) {
            var result = depositBankAccount.skipDays(days);
            if (result instanceof BankAccountOperationsResultType.Failure failureResult) {
                return new BankAccountOperationsResultType.Failure(failureResult.message());
            }
        }
        return new BankAccountOperationsResultType.Failure("You can't skip days");
    }

    @Override
    public BankAccountOperationsResultType checkOperationsHistory() {
        var result = bankAccountOperationsRepository.findOperationsByAccountNumber(currentBankAccountManager.getBankAccount().get().getAccountNumber());

        if (result instanceof BankAccountsOperationsRepositoryResultType.SuccessWithOperations successResult) {
            return new BankAccountOperationsResultType.SuccessWithOperations(successResult.operations());
        }
        else if (result instanceof BankAccountsOperationsRepositoryResultType.Failure failureResult) {
            return new BankAccountOperationsResultType.Failure(failureResult.message());
        }
        return new BankAccountOperationsResultType.Failure("Something went wrong. Please try again.");
    }
}
