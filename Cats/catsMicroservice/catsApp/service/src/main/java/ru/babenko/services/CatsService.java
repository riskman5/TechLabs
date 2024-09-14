package ru.babenko.services;

import org.springframework.data.util.Pair;
import ru.babenko.cats.CreateCatMessage;
import ru.babenko.cats.DeleteCatMessage;
import ru.babenko.cats.FriendCatsMessage;
import ru.babenko.cats.UnfriendCatsMessage;
import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;

import java.util.List;

public interface CatsService {
    FullCatDto findCatById(Long catId, Long ownerId);
    List<FullCatDto> findAllCats(Long ownerId);
    FullCatDto createCat(InitialCatDto catDto, Long ownerId);
    void deleteCat(Long catId, Long ownerId);
    void friendCats(Long firstCatId, Long secondCatId, Long ownerId);
    void unfriendCats(Long firstCatId, Long secondCatId, Long ownerId);
    List<FullCatDto> findCatsByParams(Color color, String breed, Long ownerId);
}
