package com.budgetmanager.repositories;

import com.budgetmanager.entities.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByDescription(String description);
}
