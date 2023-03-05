package com.budgetmanager.Budget.services;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
import com.budgetmanager.budget.services.BudgetService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceShowBudgetsTests {
    @Mock
    private BudgetRepository budgetRepository;

    private BudgetService budgetService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetService(budgetRepository, userService);
    }

    @Test
    void testFindAllBudgetsForLoggedUser() {
        when(userService.getLoggedUser()).thenReturn(new User());
        List<Budget> expectedList = Arrays.asList(new Budget(), new Budget());
        when(budgetRepository.findAllByUserId(userService.getLoggedUser().getId())).thenReturn(expectedList);
        List<Budget> actualList = budgetService.findAllBudgetsForLoggedUser();
        assertEquals(2, actualList.size());
        assertEquals(expectedList, actualList);
    }

    @Test
    void testFindAllBudgetsForUserByExpenseCategory() {
        when(userService.getLoggedUser()).thenReturn(new User());
        List<Budget> expectedList = Arrays.asList(new Budget(), new Budget());
        when(budgetRepository.findAllByExpenseCategoryAndUserId(ExpenseCategory.OTHERS, userService.getLoggedUser().getId())).thenReturn(expectedList);
        List<Budget> actualList = budgetService.findAllBudgetsByCategoryForUser(ExpenseCategory.OTHERS);
        assertEquals(2, actualList.size());
        assertEquals(expectedList, actualList);
    }

    @Test
    void testFindAllBudgetsForUserByIncomeCategory() {
        when(userService.getLoggedUser()).thenReturn(new User());
        List<Budget> expectedList = Arrays.asList(new Budget(), new Budget());
        when(budgetRepository.findAllByIncomeCategoryAndUserId(IncomeCategory.SALARY, userService.getLoggedUser().getId())).thenReturn(expectedList);
        List<Budget> actualList = budgetService.findAllBudgetsByCategoryForUser(IncomeCategory.SALARY);
        assertEquals(2, actualList.size());
        assertEquals(expectedList, actualList);
    }

}
