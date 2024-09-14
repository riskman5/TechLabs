package ru.babenko.dataAccess.repositories;

import org.springframework.stereotype.Repository;
import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepositoriesResultType;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepository;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Repository
public class BankAccountsRepositoryImpl implements BankAccountsRepository {
    private final Map<BigInteger, BankAccount> _bankAccounts;
    private BigInteger _currentFreeAccountNumber;

    public BankAccountsRepositoryImpl() {
        _bankAccounts = new java.util.HashMap<>();
        _currentFreeAccountNumber = BigInteger.ONE;
    }

    @Override
    public BankAccountsRepositoriesResultType addBankAccount(BankAccount bankAccount) {
        if (_bankAccounts.containsKey(bankAccount.getAccountNumber())) {
            return new BankAccountsRepositoriesResultType.Failure("Bank account with number " + bankAccount.getAccountNumber() + " already exists");
        }
        _bankAccounts.put(bankAccount.getAccountNumber(), bankAccount);
        return new BankAccountsRepositoriesResultType.Success();
    }

    @Override
    public Optional<BankAccount> findBankAccountByNumber(BigInteger accountNumber) {
        if (!_bankAccounts.containsKey(accountNumber)) {
            return Optional.empty();
        }
        return Optional.ofNullable(_bankAccounts.get(accountNumber));
    }

    @Override
    public void removeBankAccount(BigInteger accountNumber) {
        _bankAccounts.remove(accountNumber);
    }

    @Override
    public BigInteger getCurrentFreeAccountNumber() {
        var result = _currentFreeAccountNumber;
        _currentFreeAccountNumber = _currentFreeAccountNumber.add(BigInteger.ONE);

        return result;
    }
}
