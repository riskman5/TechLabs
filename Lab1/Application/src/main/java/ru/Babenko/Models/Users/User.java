package ru.babenko.models.users;

import ru.babenko.models.banks.Bank;

import java.util.Optional;


public interface User{
    String getName();
    String getSurname();
    Optional<Integer> getPassportSeries();
    void setPassportSeries(Integer passportSeries);
    Optional<Integer> getPassportNumber();
    void setPassportNumber(Integer passportNumber);
    Optional<String> getAddress();
    void setAddress(String address);
    Optional<String> getEmail();
    void setEmail(String email);
    Optional<String> getPhoneNumber();
    void setPhoneNumber(String phoneNumber);
    Bank getUsersBank();
    boolean isVerified();
}
