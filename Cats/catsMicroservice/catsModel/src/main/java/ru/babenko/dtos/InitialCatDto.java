package ru.babenko.dtos;

import jakarta.validation.constraints.NotNull;

public record InitialCatDto(
        @NotNull
        String breed,
        @NotNull
        Color color) implements CatDto { }
