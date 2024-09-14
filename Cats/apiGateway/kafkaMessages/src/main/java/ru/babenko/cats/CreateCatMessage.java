package ru.babenko.cats;

import ru.babenko.dtos.InitialCatDto;

public record CreateCatMessage(InitialCatDto cat, Long ownerId) { }
