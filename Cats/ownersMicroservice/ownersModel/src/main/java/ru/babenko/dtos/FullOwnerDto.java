package ru.babenko.dtos;

import java.time.LocalDate;
import java.util.List;

public record FullOwnerDto(Long id,
                           String name,
                           String surname,
                           LocalDate dateOfBirth,
                           List<Long> catsIds) implements OwnerDto { }

