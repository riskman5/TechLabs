package ru.babenko.models.users;

import ru.babenko.models.bankAccounts.BankAccount;
import ru.babenko.models.banks.Bank;

import java.util.List;
import java.util.Optional;

public class BankUser implements User {
    private final String name;
    private final String surname;
    private final Bank usersBank;
    private Optional<Integer> passportSeries;
    private Optional<Integer> passportNumber;
    private Optional<String> address;
    private Optional<String> email;
    private Optional<String> phoneNumber;
    private List<BankAccount> bankAccounts;

    public BankUser(String name, String surname, Bank usersbank) {
        this.name = name;
        this.surname = surname;
        usersBank = usersbank;
        passportSeries = Optional.empty();
        passportNumber = Optional.empty();
        address = Optional.empty();
        email = Optional.empty();
        phoneNumber = Optional.empty();
        bankAccounts = List.of();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Optional<Integer> getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(Integer passportSeries) {
        this.passportSeries = Optional.ofNullable(passportSeries);
    }

    public Optional<Integer> getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Integer passportNumber) {
        this.passportNumber = Optional.ofNullable(passportNumber);
    }

    public Optional<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = Optional.ofNullable(address);
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Optional.ofNullable(email);
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Optional.ofNullable(phoneNumber);
    }

    public Bank getUsersBank() {
        return usersBank;
    }

    @Override
    public boolean isVerified() {
        return passportSeries.isPresent() && passportNumber.isPresent();
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }
}
