package ru.babenko.owners;

public record AddOwnerToUserMessage(Long ownerId, Long userId) {}
