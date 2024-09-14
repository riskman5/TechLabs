package ru.babenko.dataAccess;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.babenko.dataAccess.repositories.BankAccountsOperationsRepositoryImpl;
import ru.babenko.dataAccess.repositories.BankAccountsRepositoryImpl;
import ru.babenko.dataAccess.repositories.BanksRepositoryImpl;
import ru.babenko.dataAccess.repositories.UsersRepositoryImpl;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepository;
import ru.babenko.repositories.banksRepositories.BanksRepository;
import ru.babenko.repositories.usersRepositories.UsersRepository;

@Configuration
@ComponentScan(basePackages = {
        "ru.babenko.dataAccess"})
public class InfrastructureConfig {
}
