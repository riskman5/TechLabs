package ru.babenko.services;

import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;

import java.util.List;

public interface CatsService {
    FullCatDto findCatById(Long id, String username);

    List<FullCatDto> findAllCats(String username);

    void createCat(InitialCatDto cat, String username);

    void deleteCatById(Long id, String username);

    void friendCats(Long catId1, Long catId2, String username);

    void unfriendCats(Long catId1, Long catId2, String username);

    List<FullCatDto> findCatsByParams(Color color, String breed, String username);
}
