package ru.babenko.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.babenko.models.Authority;

import java.util.Set;

public record UserRegisterUserDto(
        @NotNull
        @Size(min = 3, max = 50, message = "login size should be between 3 and 50 symbols" )
        String username,
        @NotNull
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter and one number")
        String password) implements UserDto {}