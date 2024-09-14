package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babenko.CatsClient;
import ru.babenko.KafkaProducer;
import ru.babenko.cats.CreateCatMessage;
import ru.babenko.cats.DeleteCatMessage;
import ru.babenko.cats.FriendCatsMessage;
import ru.babenko.dao.UsersDao;
import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatsServiceImpl implements CatsService {
    private final KafkaProducer kafkaProducer;
    private final CatsClient catsClient;
    private final UsersDao userDao;

    @Override
    public FullCatDto findCatById(Long id, String username) {
        var ownerId = userDao.findByUsername(username).getOwnerId();

        return catsClient.findCatById(id, ownerId);
    }

    @Override
    public List<FullCatDto> findAllCats(String username) {
        var ownerId = userDao.findByUsername(username).getOwnerId();

        return catsClient.findAllCats(ownerId);
    }

    @Override
    public void createCat(InitialCatDto cat, String username) {
        kafkaProducer.sendMessage("create-cat", new CreateCatMessage(cat, userDao.findByUsername(username).getOwnerId()));
    }

    @Override
    public void deleteCatById(Long id, String username) {
        kafkaProducer.sendMessage("delete-cat", new DeleteCatMessage(id, userDao.findByUsername(username).getOwnerId()));
    }

    @Override
    public void friendCats(Long catId1, Long catId2, String username) {
        var ownerId = userDao.findByUsername(username).getOwnerId();

        kafkaProducer.sendMessage("friend-cats", new FriendCatsMessage(catId1, catId2, ownerId));
    }

    @Override
    public void unfriendCats(Long catId1, Long catId2, String username) {
        var ownerId = userDao.findByUsername(username).getOwnerId();

        kafkaProducer.sendMessage("unfriend-cats", new DeleteCatMessage(catId1, ownerId));
    }

    @Override
    public List<FullCatDto> findCatsByParams(Color color, String breed, String username) {
        var ownerId = userDao.findByUsername(username).getOwnerId();

        return catsClient.findCatsByParams(color, breed, ownerId);
    }
}
