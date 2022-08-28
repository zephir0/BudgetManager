package com.budgetmanager.repositories;

import com.budgetmanager.entities.Budget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends CrudRepository<Budget, Long> {
    Iterable<Budget> findAllByUserId(Long id);

}
