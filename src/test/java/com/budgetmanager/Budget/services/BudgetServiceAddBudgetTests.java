package com.budgetmanager.Budget.services;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.exceptions.InvalidBudgetValueException;
import com.budgetmanager.budget.exceptions.InvalidBudgetCategoryException;
import com.budgetmanager.budget.exceptions.InvalidBudgetTypeException;
import com.budgetmanager.budget.services.BudgetService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceAddBudgetTests {
    @Mock
    private BudgetRepository budgetRepository;

    private BudgetService budgetService;

    @Mock
    private UserService userService;

    private BudgetDto testBudgetDto;
    private Budget testBudget;
    private User testUser;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetService(budgetRepository, userService);

        testBudgetDto = new BudgetDto(1000, BudgetType.EXPENSE, ExpenseCategory.FOOD, null);
        testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("test");
        testUser.setRole(UserRoles.USER);

        testBudget = new Budget();
        testBudget.setId(1L);
        testBudget.setValue(1000);
        testBudget.setExpenseCategory(ExpenseCategory.FOOD);
        testBudget.setBudgetType(BudgetType.EXPENSE);
        testBudget.setUser(testUser);
        testBudget.setHistoryDayNumber(LocalDate.now().toString());
    }


    @Test
    void testAddBudget() {
        when(userService.getLoggedUser()).thenReturn(testUser);
        doAnswer(invocation -> {
            Budget budget = invocation.getArgument(0, Budget.class);
            assertEquals(testBudget.getValue(), budget.getValue());
            assertEquals(testBudget.getExpenseCategory(), budget.getExpenseCategory());
            assertEquals(testBudget.getBudgetType(), budget.getBudgetType());
            assertEquals(testBudget.getUser(), budget.getUser());
            assertEquals(testBudget.getHistoryDayNumber(), budget.getHistoryDayNumber());
            return null;
        }).when(budgetRepository).save(any(Budget.class));
        budgetService.addBudget(testBudgetDto);
        verify(budgetRepository).save(any(Budget.class));
    }

    @Test
    void shouldThrowEmptyBudgetCategoryException() {
        testBudgetDto = new BudgetDto(1000, BudgetType.EXPENSE, null, null);
        assertThrows(InvalidBudgetCategoryException.class, () -> budgetService.addBudget(testBudgetDto));
    }

    @Test
    void shouldThrowBudgetValueNullException() {
        testBudgetDto = new BudgetDto(0, BudgetType.EXPENSE, ExpenseCategory.FOOD, null);
        assertThrows(InvalidBudgetValueException.class, () -> budgetService.addBudget(testBudgetDto));
    }

    @Test
    void shouldThrowEmptyBudgetTypeException() {
        testBudgetDto = new BudgetDto(100, null, ExpenseCategory.FOOD, null);
        assertThrows(InvalidBudgetTypeException.class, () -> budgetService.addBudget(testBudgetDto));
    }

}
