package com.budgetmanager.Budget;

import com.budgetmanager.budget.BudgetRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BudgetRepositoryTests {
    @Autowired
    private BudgetRepository budgetRepository;



//    @Test
//    @Rollback(value = false)
//    @Order(1)
//    public void createBudget() {
//        Budget budget = new Budget();
//        budget.setIncome(941030);
//        budget.setExpense(200);
//        budget.setHistoryDayNumber(LocalDate.now().toString());
//        budgetRepository.save(budget);
//        assertTrue(budgetRepository.findById(budget.getId()).isPresent());
//    }
//
//    @Test
//    @Order(2)
//    public void findBudgetById() {
//        Long budgetId = budgetRepository.findByIncome(941030).get().getId();
//        assertTrue(budgetRepository.findById(budgetId).isPresent());
//    }
//    @Test
//    @Order(3)
//    public void findBudgetByIncome() {
//        assertTrue(budgetRepository.findByIncome(941030).isPresent());
//    }
//
//
//
//    @Test
//    @Rollback(value = false)
//    @Order(4)
//    public void deleteBudgetById() {
//        budgetRepository.findByIncome(941030)
//                .ifPresent(
//                        budget -> {
//                            budgetRepository.deleteById(budget.getId());
//                            assertFalse(budgetRepository.existsByIncome(941030), "Budget not exist after deletion");
//                        }
//                );
//
//    }
}
