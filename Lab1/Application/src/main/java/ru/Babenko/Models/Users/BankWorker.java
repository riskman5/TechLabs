package ru.babenko.models.users;

import ru.babenko.models.banks.Bank;

import java.util.Optional;

public class BankWorker implements User {
    private final String name;
    private final String surname;
    private final Bank usersBank;
    private Optional<Integer> passportSeries;
    private Optional<Integer> passportNumber;
    private Optional<String> address;
    private Optional<String> email;
    private Optional<String> phoneNumber;

    public BankWorker(String name, String surname, Bank usersbank) {
        this.name = name;
        this.surname = surname;
        usersBank = usersbank;
        passportSeries = Optional.empty();
        passportNumber = Optional.empty();
        address = Optional.empty();
        email = Optional.empty();
        phoneNumber = Optional.empty();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public Optional<Integer> getPassportSeries() {
        return passportSeries;
    }

    @Override
    public void setPassportSeries(Integer passportSeries) {
        this.passportSeries = Optional.ofNullable(passportSeries);
    }

    @Override
    public Optional<Integer> getPassportNumber() {
        return passportNumber;
    }

    @Override
    public void setPassportNumber(Integer passportNumber) {
        this.passportNumber = Optional.ofNullable(passportNumber);
    }

    @Override
    public Optional<String> getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = Optional.ofNullable(address);
    }

    @Override
    public Optional<String> getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = Optional.ofNullable(email);
    }

    @Override
    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Optional.ofNullable(phoneNumber);
    }

    @Override
    public Bank getUsersBank() {
        return usersBank;
    }

    @Override
    public boolean isVerified() {
        return getPassportNumber().isPresent() && getPassportSeries().isPresent();
    }
}
