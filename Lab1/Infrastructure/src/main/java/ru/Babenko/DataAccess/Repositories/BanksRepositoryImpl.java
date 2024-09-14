package ru.babenko.dataAccess.repositories;

import org.springframework.stereotype.Repository;
import ru.babenko.models.banks.Bank;
import ru.babenko.repositories.banksRepositories.BanksRepositoriesResultType;
import ru.babenko.repositories.banksRepositories.BanksRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class BanksRepositoryImpl implements BanksRepository {
    private final Map<String, Bank> _banks;

    public BanksRepositoryImpl() {
        _banks = new HashMap<String, Bank>();
    }

    @Override
    public BanksRepositoriesResultType addBank(Bank bank) {
        if (_banks.containsKey(bank.getName())) {
            return new BanksRepositoriesResultType.Failure("Bank with name " + bank.getName() + " already exists");
        }
        _banks.put(bank.getName(), bank);
        return new BanksRepositoriesResultType.Success();
    }

    @Override
    public Optional<Bank> findBankByName(String bankName) {
        if (!_banks.containsKey(bankName)) {
            return Optional.empty();
        }
        return Optional.ofNullable(_banks.get(bankName));
    }

    @Override
    public void removeBank(Bank bank) {
        _banks.remove(bank.getName());
    }
}
