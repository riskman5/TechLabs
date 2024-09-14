package ru.babenko;

import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;

import java.util.List;

public interface CatsClient {
    FullCatDto findCatById(Long catId, Long ownerId);
    List<FullCatDto> findAllCats(Long ownerId);
    FullCatDto createCat(InitialCatDto createCatDto, Long ownerId);
    void deleteCatById(Long ownerId);
    void friendCats(Long catId1, Long catId2, Long ownerId);
    void unfriendCats(Long catId1, Long catId2, Long ownerId);
    List<FullCatDto> findCatsByParams(Color color, String breed, Long ownerId);
}
