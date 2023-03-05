package com.budgetmanager.budget;

import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
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

    @Override
    @Cacheable(cacheNames = "budgets")
    Optional<Budget> findById(Long id);

    @Cacheable(cacheNames = "budgets")
    List<Budget> findAllByExpenseCategoryAndUserId(ExpenseCategory expenseCategory,
                                                   Long id);

    @Cacheable(cacheNames = "budgets")
    List<Budget> findAllByIncomeCategoryAndUserId(IncomeCategory incomeCategory,
                                                  Long id);


    @Cacheable(cacheNames = "budgets")
    List<Budget> findAllByHistoryDayNumberAndUserId(String day,
                                                    Long id);

    @Override
    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    void delete(Budget budget);


    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    void deleteAllByUserId(Long id);

    @Override
    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    <S extends Budget> S save(S entity);


}


