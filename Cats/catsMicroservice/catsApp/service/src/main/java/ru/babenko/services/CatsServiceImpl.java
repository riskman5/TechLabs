package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babenko.OwnersClient;
import ru.babenko.dao.CatsDao;
import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;
import ru.babenko.mappers.CatsMapper;
import ru.babenko.models.Cat;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatsServiceImpl implements CatsService {
    private final OwnersClient ownersClient;
    private final CatsMapper catsMapper;
    private final CatsDao catsDao;

    @Override
    public FullCatDto findCatById(Long catId, Long ownerId) {
        return catsMapper.catToFullCatDto(catsDao.findByOwnerIdAndId(ownerId, catId));
    }

    @Override
    public List<FullCatDto> findAllCats(Long ownerId) {
        return catsMapper.catsToFullCatDtos(catsDao.findAllByOwnerId(ownerId));
    }

    @Override
    public FullCatDto createCat(InitialCatDto catDto, Long ownerId) {
        Cat cat = Cat.builder()
                .breed(catDto.breed())
                .color(catDto.color())
                .ownerId(ownerId)
                .build();
        ownersClient.addCatToOwner(ownerId, cat.getId());
        return catsMapper.catToFullCatDto(catsDao.save(cat));
    }

    @Override
    public void deleteCat(Long catId, Long ownerId) {
        catsDao.delete(catsDao.findByOwnerIdAndId(ownerId, catId));
        ownersClient.deleteCatFromOwner(ownerId, catId);
    }

    @Override
    public void friendCats(Long firstCatId, Long secondCatId, Long ownerId) {
        Cat cat1 = catsDao.findByOwnerIdAndId(ownerId, firstCatId);
        Cat cat2 = catsDao.findByOwnerIdAndId(ownerId, secondCatId);
        if (cat1 == null || cat2 == null) {
            throw new IllegalArgumentException("Cats not found");
        }
        cat1.getFriends().add(cat2);
    }

    @Override
    public void unfriendCats(Long firstCatId, Long secondCatId, Long ownerId) {
        Cat cat1 = catsDao.findByOwnerIdAndId(ownerId, firstCatId);
        Cat cat2 = catsDao.findByOwnerIdAndId(ownerId, secondCatId);
        if (cat1 == null || cat2 == null) {
            throw new IllegalArgumentException("Cats not found");
        }
        cat1.getFriends().remove(cat2);
    }

    @Override
    public List<FullCatDto> findCatsByParams(Color color, String breed, Long ownerId) {
        return catsMapper.catsToFullCatDtos(catsDao.findAllByOwnerIdAndBreedAndColor(ownerId, breed, color));
    }
}
