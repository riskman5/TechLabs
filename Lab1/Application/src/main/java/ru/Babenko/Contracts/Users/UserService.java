package ru.babenko.contracts.users;

public interface UserService {
    UserOperationsResultType addAddress(String address);

    UserOperationsResultType addPassport(Integer passportSeries, Integer passportNumber);

    UserOperationsResultType addPhoneNumber(String phoneNumber);

    UserOperationsResultType chooseBankAccount(Integer bankAccountId);

    UserOperationsResultType getBankAccounts();

    UserOperationsResultType createDebitBankAccount();

    UserOperationsResultType createCreditBankAccount();

    UserOperationsResultType createDepositBankAccount();
}
