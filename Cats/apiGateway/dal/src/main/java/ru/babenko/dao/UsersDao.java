package ru.babenko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.babenko.models.User;

@Repository
public interface UsersDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
