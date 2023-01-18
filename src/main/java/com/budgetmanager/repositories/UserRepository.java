package com.budgetmanager.repositories;

import com.budgetmanager.entities.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@EnableCaching
public interface UserRepository extends CrudRepository<User, Long> {
    @Cacheable(cacheNames = "users")
    Optional<User> findByLogin(String login);

    @Cacheable(cacheNames = "users")
    Optional<User> findUserById(Long id);

    @CacheEvict(cacheNames = "users", allEntries = true)
    @Override
    void delete(User entity);

    @Cacheable(cacheNames = "users")
    List<User> findAll();

    boolean existsByLogin(String login);

}
