package ru.babenko.owners;

import ru.babenko.dtos.InitialOwnerDto;

public record CreateOwnerMessage(InitialOwnerDto owner, Long userId) { }
