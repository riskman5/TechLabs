package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.babenko.KafkaProducer;
import ru.babenko.OwnersClient;
import ru.babenko.dao.UsersDao;
import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;
import ru.babenko.owners.CreateOwnerMessage;
import ru.babenko.owners.DeleteOwnerMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnersServiceImpl implements OwnersService {
    @Autowired
    KafkaProducer kafkaProducer;
    private final OwnersClient ownersClient;
    private final UsersDao usersDao;

    @Override
    public FullOwnerDto findOwnerById(Long id) {
        return ownersClient.findOwnerById(id);
    }

    @Override
    public List<FullOwnerDto> findAllOwners() {
        return ownersClient.findAllOwners();
    }

    @Override
    public void createOwner(InitialOwnerDto owner, String username) {
        kafkaProducer.sendMessage("create-owner", new CreateOwnerMessage(owner, usersDao.findByUsername(username).getId()));
    }

    @Override
    public void deleteOwner(String username) {
        usersDao.findByUsername(username).setOwnerId(null);
        kafkaProducer.sendMessage("delete-owner", new DeleteOwnerMessage(usersDao.findByUsername(username).getOwnerId(), usersDao.findByUsername(username).getId()));
    }
}