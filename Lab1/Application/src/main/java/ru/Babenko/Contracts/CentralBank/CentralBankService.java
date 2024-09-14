package ru.babenko.contracts.centralBank;

import ru.babenko.contracts.banks.BankOperationsResultType;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface CentralBankService {
    BankOperationsResultType createBank(String name);

    BankOperationsResultType transferBetweenBankAccounts(BigInteger fromBankAccountNumber, BigInteger toBankAccountNumber, BigDecimal amount);
}
