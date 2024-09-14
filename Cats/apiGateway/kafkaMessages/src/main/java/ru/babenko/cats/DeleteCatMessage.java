package ru.babenko.cats;

public record DeleteCatMessage(Long catId, Long ownerId) { }
