package ru.babenko.services;

import org.springframework.data.util.Pair;
import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;
import ru.babenko.owners.CreateOwnerMessage;
import ru.babenko.owners.DeleteOwnerMessage;

import java.util.List;

public interface OwnersService {
    FullOwnerDto findOwnerById(Long id);
    List<FullOwnerDto> findAllOwners();
    FullOwnerDto createOwner(InitialOwnerDto owner);
    void deleteOwner(Long id);
    void addCatToOwner(Long ownerId, Long catId);
    void deleteCatFromOwner(Long ownerId, Long catId);
}
