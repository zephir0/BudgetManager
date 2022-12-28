package com.budgetmanager.repositories;

import com.budgetmanager.entities.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Cacheable
    Optional<User> findByLogin(String login);

    @Cacheable
    Optional<User> findUserById(Long id);

    @CacheEvict(allEntries = true)
    @Override
    void delete(User entity);

    @Cacheable
    List<User> findAll();

    boolean existsByLogin(String login);
}
