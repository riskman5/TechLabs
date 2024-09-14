package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babenko.CatsClient;
import ru.babenko.KafkaProducer;
import ru.babenko.dao.OwnersDao;
import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;
import ru.babenko.mappers.OwnersMapper;
import ru.babenko.models.Owner;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnersServiceImpl implements OwnersService {
    private final KafkaProducer kafkaProducer;
    private final CatsClient catsClient;
    private final OwnersMapper ownersMapper;
    private final OwnersDao ownersDao;

    @Override
    public FullOwnerDto findOwnerById(Long id) {
        Optional<Owner> optionalOwner = ownersDao.findById(id);
        return optionalOwner.map(ownersMapper::ownerToFullOwnerDto).orElse(null);
    }

    @Override
    public List<FullOwnerDto> findAllOwners() {
        return ownersMapper.ownersToFullOwnerDtos(ownersDao.findAll());
    }

    @Override
    public FullOwnerDto createOwner(InitialOwnerDto owner) {
        Owner newOwner = Owner.builder()
                .name(owner.name())
                .surname(owner.surname())
                .birthDate(owner.dateOfBirth())
                .catsIds(List.of())
                .build();

        ownersDao.save(newOwner);

        return ownersMapper.ownerToFullOwnerDto(newOwner);
    }

    @Override
    public void deleteOwner(Long id) {
        var owner = ownersDao.findById(id).orElseThrow();

        for (Long catId : owner.getCatsIds()) {
            catsClient.deleteCatById(catId);
        }

        ownersDao.delete(owner);
    }

    @Override
    public void addCatToOwner(Long ownerId, Long catId) {
        var owner = ownersDao.findById(ownerId).orElseThrow();
        owner.getCatsIds().add(catId);
    }

    @Override
    public void deleteCatFromOwner(Long ownerId, Long catId) {
        var owner = ownersDao.findById(ownerId).orElseThrow();
        owner.getCatsIds().remove(catId);
    }
}
