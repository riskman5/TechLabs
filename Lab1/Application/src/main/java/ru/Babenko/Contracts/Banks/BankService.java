package ru.babenko.contracts.banks;

import ru.babenko.models.bankAccounts.builders.CreditBankAccountBuilder;
import ru.babenko.models.bankAccounts.builders.DebitBankAccountBuilder;
import ru.babenko.models.bankAccounts.builders.DepositBankAccountBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public interface BankService {
    BankOperationsResultType setInterestRate(BigDecimal interestRate);

    BankOperationsResultType setCommission(BigDecimal commission);

    BankOperationsResultType setTransferLimit(BigDecimal transferLimit);

    BankOperationsResultType setUnverifiedAccountWithdrawLimit(BigDecimal limit);

    BankOperationsResultType setUnverifiedAccountTransferLimit(BigDecimal limit);

    BankOperationsResultType getBankAccountOperationsByBankAccountNumber(BigInteger bankAccountNumber);

    BankOperationsResultType declineOperationById(UUID operationId);

    BankOperationsResultType registerBankUser(String name, String surname);

    BankOperationsResultType createDebitBankAccount(DebitBankAccountBuilder builder);

    BankOperationsResultType createCreditBankAccount(CreditBankAccountBuilder builder);

    BankOperationsResultType createDepositBankAccount(DepositBankAccountBuilder builder);
}
