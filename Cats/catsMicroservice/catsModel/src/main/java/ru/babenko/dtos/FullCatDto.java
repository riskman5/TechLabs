package ru.babenko.dtos;

import java.util.List;

public record FullCatDto(Long id,
                         String breed,
                         Color color,
                         Long ownerId,
                         List<Long> friendsIds) implements CatDto { }
