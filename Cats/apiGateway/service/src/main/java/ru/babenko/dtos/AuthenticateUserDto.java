package ru.babenko.dtos;

public record AuthenticateUserDto(String username,
                                  String password) implements UserDto {}
