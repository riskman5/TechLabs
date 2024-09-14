package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.babenko.dao.UsersDao;
import ru.babenko.models.Authority;
import ru.babenko.owners.AddOwnerToUserMessage;

@Service
@RequiredArgsConstructor
public class UserServiceListener {
    private final UsersDao usersDao;

    @KafkaListener(topics = "add-owner-to-user")
    public void addOwnerToUser(AddOwnerToUserMessage message) {
        var user = usersDao.findById(message.userId()).get();
        user.setOwnerId(message.ownerId());
        user.addAuthority(Authority.OWNER);
        usersDao.save(user);
    }
}
