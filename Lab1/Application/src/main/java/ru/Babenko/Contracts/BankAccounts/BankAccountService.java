package ru.babenko.contracts.bankAccounts;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface BankAccountService {
    BankAccountOperationsResultType deposit(BigDecimal amount);

    BankAccountOperationsResultType withdraw(BigDecimal amount);

    BankAccountOperationsResultType checkBalance();

    BankAccountOperationsResultType transfer(BigInteger anotherBankAccountNumber, BigDecimal amount);

    BankAccountOperationsResultType skipDays(int days);

    BankAccountOperationsResultType checkOperationsHistory();
}
