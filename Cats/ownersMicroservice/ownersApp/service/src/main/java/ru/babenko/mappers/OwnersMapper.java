package ru.babenko.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.models.Owner;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OwnersMapper {
    @Mapping(source = "birthDate", target = "dateOfBirth")
    FullOwnerDto ownerToFullOwnerDto(Owner owner);

    List<FullOwnerDto> ownersToFullOwnerDtos(List<Owner> owners);
}
