package com.budgetmanager.repositories;

import com.budgetmanager.entities.Budget;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableCaching
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Cacheable(cacheNames = "budgets")
    List<Budget> findAllByUserId(Long id);

    @Cacheable(cacheNames = "budgets")

    @Override
    Optional<Budget> findById(Long id);

    @Cacheable(cacheNames = "budgets")
    List<Budget> findAllByHistoryDayNumberAndUserId(String day,
                                                    Long id);

    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    void deleteById(Long id);

    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    void deleteAllByUserId(Long id);

    @Override
    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    <S extends Budget> S save(S entity);

    boolean existsByIncome(int income);

    Optional<Budget> findByIncome(int income);
}


