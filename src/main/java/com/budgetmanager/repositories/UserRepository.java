package com.budgetmanager.repositories;

import com.budgetmanager.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findUserById(Long id);
    List<User> findAll();
    boolean existsByLogin(String login);
}
