package com.budgetmanager.Budget.repository;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
import com.budgetmanager.user.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetRepositoryTests {


    @Mock
    private BudgetRepository budgetRepository;

    private User user;
    private Budget expectedBudget;
    private List<Budget> expectedBudgetList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setLogin("admin");
        expectedBudget = new Budget();
        expectedBudget.setId(1L);
        expectedBudget.setValue(1000);
        expectedBudget.setIncomeCategory(IncomeCategory.SALARY);
        expectedBudget.setUser(user);
        expectedBudget.setHistoryDayNumber(LocalDate.now().toString());

    }



    @Test
    public void testFindAllByUserId() {
        when(budgetRepository.findAllByUserId(expectedBudget.getUser().getId())).thenReturn(expectedBudgetList);
        List<Budget> actualBudgetList = budgetRepository.findAllByUserId(expectedBudget.getUser().getId());
        assertEquals(expectedBudgetList, actualBudgetList);
    }

    @Test
    public void testFindBudgetById() {
        when(budgetRepository.findById(expectedBudget.getId())).thenReturn(Optional.of(expectedBudget));
        Optional<Budget> actualBudget = budgetRepository.findById(expectedBudget.getId());
        assertEquals(Optional.of(expectedBudget), actualBudget);
    }

    @Test
    public void testFindAllByExpenseCategoryAndUserId() {
        when(budgetRepository.findAllByExpenseCategoryAndUserId(ExpenseCategory.FOOD, expectedBudget.getUser().getId())).thenReturn(expectedBudgetList);
        List<Budget> actualBudgetList = budgetRepository.findAllByExpenseCategoryAndUserId(ExpenseCategory.FOOD, expectedBudget.getUser().getId());
        assertEquals(expectedBudgetList, actualBudgetList);
    }

    @Test
    public void testFindAllByIncomeCategoryAndUserId() {
        when(budgetRepository.findAllByIncomeCategoryAndUserId(IncomeCategory.SALARY, expectedBudget.getUser().getId())).thenReturn(expectedBudgetList);
        List<Budget> actualBudgetList = budgetRepository.findAllByIncomeCategoryAndUserId(IncomeCategory.SALARY, expectedBudget.getUser().getId());
        assertEquals(expectedBudgetList, actualBudgetList);
    }

    @Test
    public void testFindAllByHistoryDayNumberAndUserId() {
        when(budgetRepository.findAllByHistoryDayNumberAndUserId(expectedBudget.getHistoryDayNumber(), expectedBudget.getUser().getId())).thenReturn(expectedBudgetList);
        List<Budget> actualBudgetList = budgetRepository.findAllByHistoryDayNumberAndUserId(expectedBudget.getHistoryDayNumber(), expectedBudget.getUser().getId());
        assertEquals(expectedBudgetList, actualBudgetList);
    }

    @Test
    public void testDelete() {
        budgetRepository.delete(expectedBudget);
        verify(budgetRepository).delete(expectedBudget);
    }

    @Test
    public void testDeleteAllByUserId() {
        budgetRepository.deleteAllByUserId(expectedBudget.getUser().getId());
        verify(budgetRepository).deleteAllByUserId(expectedBudget.getUser().getId());
    }


}

