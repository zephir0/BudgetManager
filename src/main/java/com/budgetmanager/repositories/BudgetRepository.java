package com.budgetmanager.repositories;

import com.budgetmanager.entities.Budget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends CrudRepository<Budget, Long> {
    List<Budget> findAllByUserId(Long id);

    List<Budget> findAllByHistoryDayNumberAndUserId(String day,
                                                    Long id);




    void deleteById(Long id);
}


