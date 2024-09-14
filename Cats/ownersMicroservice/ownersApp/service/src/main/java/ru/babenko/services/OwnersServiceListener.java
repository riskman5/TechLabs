package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.babenko.KafkaProducer;
import ru.babenko.owners.AddOwnerToUserMessage;
import ru.babenko.owners.CreateOwnerMessage;
import ru.babenko.owners.DeleteOwnerMessage;

@Service
@RequiredArgsConstructor
public class OwnersServiceListener {
    private final OwnersService ownersService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "create-owner")
    public void createOwner(CreateOwnerMessage message) {
        var owner = ownersService.createOwner(message.owner());
        kafkaProducer.sendMessage("add-owner-to-user", new AddOwnerToUserMessage(owner.id(), message.userId()));
    }

    @KafkaListener(topics = "delete-owner")
    public void deleteOwner(DeleteOwnerMessage message) {
        ownersService.deleteOwner(message.ownerId());
    }
}
