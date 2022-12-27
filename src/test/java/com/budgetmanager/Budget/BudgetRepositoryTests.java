package com.budgetmanager.Budget;

import com.budgetmanager.entities.Budget;
import com.budgetmanager.repositories.BudgetRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BudgetRepositoryTests {
    @Autowired
    private BudgetRepository budgetRepository;

    @Test
    @Order(1)
    public void createBudget() {
        Budget budget = new Budget();
        budget.setIncome(200);
        budget.setExpense(200);
        budget.setHistoryDayNumber(LocalDate.now().toString());
        budgetRepository.save(budget);
        Long budgetId = budgetRepository.findById(budget.getId()).get().getId();
        assertThat(budgetId).isNotNull();
    }

//    @Test
//    @Order(2)
//    public void deleteBudgetById() {
//        budgetRepository.deleteById(14L);
//        assertFalse(budgetRepository.existsById(14L));
//    }

//    @Test
//    @Order(3)
//    public void findAllByHistoryDayNumberAndUserId() {
//        List<Budget> allByHistoryDayNumberAndUserId = budgetRepository.findAllByHistoryDayNumberAndUserId(, 1L);
//        assertThat(allByHistoryDayNumberAndUserId.size()).isGreaterThan(0);
//    }


}
