package ru.babenko.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InitialOwnerDto(@NotNull
                              @Size(min = 1, max = 50, message = "name size should be between 1 and 50 symbols")
                              String name,
                              @NotNull
                              @Size(min = 1, max = 50, message = "name size should be between 1 and 50 symbols")
                              String surname,
                              @NotNull
                              @Past(message = "Date of birth must be in the past")
                              LocalDate dateOfBirth) implements OwnerDto { }

