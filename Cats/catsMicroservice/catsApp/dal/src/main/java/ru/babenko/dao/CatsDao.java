package ru.babenko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babenko.dtos.Color;
import ru.babenko.models.Cat;

import java.util.List;

public interface CatsDao extends JpaRepository<Cat, Long> {
    Cat findByOwnerIdAndId(Long ownerId, Long id);
    List<Cat> findAllByOwnerId(Long ownerId);
    List<Cat> findAllByOwnerIdAndBreedAndColor(Long ownerId, String breed, Color color);
}
