package ru.babenko.services;

import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;

import java.util.List;

public interface OwnersService {
    FullOwnerDto findOwnerById(Long id);
    List<FullOwnerDto> findAllOwners();
    void createOwner(InitialOwnerDto owner, String username);
    void deleteOwner(String username);
}
