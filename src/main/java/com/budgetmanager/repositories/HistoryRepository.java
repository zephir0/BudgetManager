package com.budgetmanager.repositories;

import com.budgetmanager.entities.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends CrudRepository<History, Integer> {

//    @Query("SELECT coalesce(max(ch.budgetDayNumber), 0) FROM History ch")
    @Query("select max(budgetDayNumber) FROM History where user.id = ?1")
    int highestBudgetDayNumber(Long userId);

    Optional<History> findByBudgetDayNumberAndUserId(int dayNumber,
                                                     Long id);
}
