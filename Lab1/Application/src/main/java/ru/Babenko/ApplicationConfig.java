package ru.babenko;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ru.babenko.application.authorization",
        "ru.babenko.application.bankAccounts",
        "ru.babenko.application.banks",
        "ru.babenko.application.centralBank",
        "ru.babenko.application.managers",
        "ru.babenko.application.users"
})
public class ApplicationConfig {
}
