package ru.babenko.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.models.Cat;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CatsMapper {
    @Mapping(source = "friends", target = "friendsIds")
    FullCatDto catToFullCatDto(Cat cat);

    List<FullCatDto> catsToFullCatDtos(List<Cat> cats);

    default Long catToId(Cat cat) {
        return cat == null ? null : cat.getId();
    }

    default List<Long> catsToIds(List<Cat> cats) {
        if (cats == null) {
            return null;
        }
        return cats.stream()
                .map(this::catToId)
                .collect(Collectors.toList());
    }
}
