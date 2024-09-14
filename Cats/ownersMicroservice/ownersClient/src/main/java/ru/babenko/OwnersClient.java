package ru.babenko;

import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;

import java.util.List;

public interface OwnersClient {
    FullOwnerDto findOwnerById(Long id);

    List<FullOwnerDto> findAllOwners();

    FullOwnerDto createOwner(InitialOwnerDto owner);

    void addCatToOwner(Long ownerId, Long catId);

    void deleteCatFromOwner(Long ownerId, Long catId);

    void deleteOwner(Long id);
}
