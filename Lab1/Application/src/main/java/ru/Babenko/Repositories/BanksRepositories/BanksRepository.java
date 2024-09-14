package ru.babenko.repositories.banksRepositories;

import ru.babenko.models.banks.Bank;

import java.util.Optional;

public interface BanksRepository {
    BanksRepositoriesResultType addBank(Bank bank);

    Optional<Bank> findBankByName(String bankName);

    void removeBank(Bank bank);
}
