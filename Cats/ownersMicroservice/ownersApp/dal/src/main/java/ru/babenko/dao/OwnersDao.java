package ru.babenko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.babenko.models.Owner;

@Repository
public interface OwnersDao extends JpaRepository<Owner, Long> { }