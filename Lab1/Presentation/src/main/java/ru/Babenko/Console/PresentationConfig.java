package ru.babenko.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.babenko.ApplicationConfig;
import ru.babenko.console.scenarios.authorization.LogoutScenarioProvider;
import ru.babenko.console.scenarios.bankAccounts.*;
import ru.babenko.console.scenarios.banks.GetBankAccountOperationsScenarioProvider;
import ru.babenko.console.scenarios.banks.RegisterNewUserScenarioProvider;
import ru.babenko.console.scenarios.banks.RevertBankOperationScenarioProvider;
import ru.babenko.console.scenarios.centralBank.CreateNewBankScenarioProvider;
import ru.babenko.console.scenarios.authorization.LoginScenarioProvider;
import ru.babenko.console.scenarios.users.*;
import ru.babenko.contracts.bankAccounts.BankAccountService;
import ru.babenko.contracts.banks.BankService;
import ru.babenko.contracts.centralBank.CentralBankService;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.contracts.authorization.AuthorizationService;
import ru.babenko.contracts.users.UserService;
import ru.babenko.dataAccess.InfrastructureConfig;

import java.util.List;

@Configuration
@Import({ApplicationConfig.class, InfrastructureConfig.class})
@ComponentScan(basePackages = {
        "ru.babenko.console.scenarios",
        "ru.babenko.console"})
public class PresentationConfig {
}
