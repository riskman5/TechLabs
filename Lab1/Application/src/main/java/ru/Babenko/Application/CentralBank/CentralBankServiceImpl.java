package ru.babenko.application.centralBank;

import org.springframework.stereotype.Service;
import ru.babenko.contracts.bankAccounts.BankAccountOperationsResultType;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.centralBank.CentralBankService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.bankAccountsOperations.TransferOperation;
import ru.babenko.models.bankAccountsOperations.TransferOperationType;
import ru.babenko.models.banks.CentralBank;
import ru.babenko.models.users.CentralBankWorker;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepository;
import ru.babenko.repositories.banksRepositories.BanksRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class CentralBankServiceImpl implements CentralBankService {
    private final BanksRepository banksRepository;
    private final BankAccountsRepository bankAccountsRepository;
    private final CurrentUserService currentUserService;
    private final BankAccountsOperationsRepository operationsRepository;

    public CentralBankServiceImpl(BanksRepository banksRepository, BankAccountsRepository bankAccountsRepository, CurrentUserService currentUserService, BankAccountsOperationsRepository operationsRepository) {
        this.banksRepository = banksRepository;
        this.bankAccountsRepository = bankAccountsRepository;
        this.currentUserService = currentUserService;
        this.operationsRepository = operationsRepository;
    }

    @Override
    public BankOperationsResultType createBank(String name) {
        if (currentUserService.getUser().get() instanceof CentralBankWorker centralBankWorker) {
            if (centralBankWorker.getUsersBank() instanceof CentralBank centralBank) {
                var bank = centralBank.createBank(name);
                banksRepository.addBank(bank);
                return new BankOperationsResultType.Success();
            }
        }
        return new BankOperationsResultType.Failure("You are not authorized to create a bank");
    }

    @Override
    public BankOperationsResultType transferBetweenBankAccounts(BigInteger fromBankAccountNumber, BigInteger toBankAccountNumber, BigDecimal amount) {
        if (!(currentUserService.getUser().get() instanceof CentralBankWorker)) {
            return new BankOperationsResultType.Failure("You are not authorized to transfer between bank accounts");
        }

        Optional<BankAccount> fromBankAccount = bankAccountsRepository.findBankAccountByNumber(fromBankAccountNumber);
        Optional<BankAccount> toBankAccount = bankAccountsRepository.findBankAccountByNumber(toBankAccountNumber);

        if (fromBankAccount.isEmpty() || toBankAccount.isEmpty()) {
            return new BankOperationsResultType.Failure("One of the bank accounts does not exist");
        }

        var result = fromBankAccount.get().sendMoney(amount);

        if (result instanceof BankAccountOperationsResultType.Failure failure) {
            return new BankOperationsResultType.Failure(failure.message());
        }

        var senderOperation = new TransferOperation(fromBankAccount.get(), toBankAccount.get(), amount, TransferOperationType.SENT);

        result = toBankAccount.get().receiveMoney(amount);

        if (result instanceof BankAccountOperationsResultType.Failure failure) {
            senderOperation.revertOperation();
            return new BankOperationsResultType.Failure(failure.message());
        }

        var receiverOperation = new TransferOperation(toBankAccount.get(), fromBankAccount.get(), amount, TransferOperationType.RECEIVED);

        operationsRepository.addOperation(senderOperation);
        operationsRepository.addOperation(receiverOperation);

        return new BankOperationsResultType.Success();
    }
}
