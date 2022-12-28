package com.budgetmanager.repositories;

import com.budgetmanager.entities.Budget;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BudgetRepository extends CrudRepository<Budget, Long> {
    @Cacheable
    List<Budget> findAllByUserId(Long id);

    @Cacheable
    List<Budget> findAllByHistoryDayNumberAndUserId(String day,
                                                    Long id);

    @CacheEvict(allEntries = true)
    void deleteById(Long id);

    @Override
    @CacheEvict(allEntries = true)
    <S extends Budget> S save(S entity);
}


