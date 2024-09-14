package ru.babenko.services;

import ru.babenko.dtos.AuthenticateUserDto;
import ru.babenko.dtos.AdminRegisterUserDto;
import ru.babenko.dtos.UserRegisterUserDto;
import ru.babenko.owners.AddOwnerToUserMessage;

public interface UsersService {
    String authenticate(AuthenticateUserDto initialUserDto);
    void register(UserRegisterUserDto initialUserDto);
    void register(AdminRegisterUserDto initialUserDto);
    void delete(String username);
    void delete(Long userId);
    void addOwnerToUser(AddOwnerToUserMessage message);
}