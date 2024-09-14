package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.babenko.cats.CreateCatMessage;
import ru.babenko.cats.DeleteCatMessage;
import ru.babenko.cats.FriendCatsMessage;
import ru.babenko.cats.UnfriendCatsMessage;

@Service
@RequiredArgsConstructor
public class CatsServiceListener {
    private final CatsService catsService;

    @KafkaListener(topics = "create-cat")
    public void createCat(CreateCatMessage message) {
        catsService.createCat(message.cat(), message.ownerId());
    }

    @KafkaListener(topics = "delete-cat")
    public void deleteCat(DeleteCatMessage message) {
       catsService. deleteCat(message.catId(), message.ownerId());
    }

    @KafkaListener(topics = "friend-cats")
    public void friendCats(FriendCatsMessage message) {
        catsService.friendCats(message.catId1(), message.catId2(), message.ownerId());
    }

    @KafkaListener(topics = "unfriend-cats")
    public void unfriendCats(UnfriendCatsMessage message) {
        catsService.unfriendCats(message.catId1(), message.catId2(), message.ownerId());
    }
}
