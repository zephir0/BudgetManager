package com.budgetmanager.Budget;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.exceptions.BudgetDoesntExistException;
import com.budgetmanager.budget.services.BudgetService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceChangeAndDeleteTests {
    @Mock
    private BudgetRepository budgetRepository;
    @Mock
    private UserService userService;

    private BudgetService budgetService;
    private BudgetDto testBudgetDto;
    private Budget testBudget;
    private User testUser;


    @BeforeEach
    void setUp() {
        budgetService = new BudgetService(budgetRepository, userService);
        testBudgetDto = new BudgetDto(2000, BudgetType.EXPENSE, ExpenseCategory.OTHERS, null);
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
    public void testChangeBudget() {
        when(userService.getLoggedUser()).thenReturn(testUser);
        when(budgetRepository.findById(testBudget.getId())).thenReturn(Optional.of(testBudget));

        budgetService.changeBudget(testBudget.getId(), testBudgetDto);

        verify(budgetRepository).findById(testBudget.getId());
        verify(budgetRepository).save(testBudget);

        assertEquals(2000, testBudget.getValue());
        assertEquals(ExpenseCategory.OTHERS, testBudget.getExpenseCategory());
    }

    @Test
    public void testDeleteBudget() {
        when(userService.getLoggedUser()).thenReturn(testUser);
        when(budgetRepository.findById(testBudget.getId())).thenReturn(Optional.of(testBudget));
        budgetService.deleteByBudgetId(testBudget.getId());
        verify(budgetRepository).findById(testBudget.getId());
        verify(budgetRepository).delete(testBudget);
        when(budgetRepository.findById(testBudget.getId())).thenReturn(Optional.empty());
        assertFalse((budgetRepository).findById(testBudget.getId()).isPresent());
    }


    @Test
    public void shouldThrowNotAuthorizedException() {
        User notAuthorizedUser = new User();

        when(userService.getLoggedUser()).thenReturn(notAuthorizedUser);
        when(budgetRepository.findById(testBudget.getId())).thenReturn(Optional.of(testBudget));

        assertThrows(NotAuthorizedException.class, () -> budgetService.performBudgetOperation(testBudget.getId(), budgetRepository::delete));
        assertThrows(NotAuthorizedException.class, () -> budgetService.performBudgetOperation(testBudget.getId(), budgetToUpdate -> {
            budgetToUpdate.setValue(500);
            budgetToUpdate.setBudgetType(BudgetType.INCOME);
        }));
    }

    @Test
    public void shouldThrowBudgetDoesntExistException() {
        when(budgetRepository.findById(testBudget.getId())).thenReturn(Optional.empty());
        assertThrows(BudgetDoesntExistException.class, () -> budgetService.performBudgetOperation(testBudget.getId(), budgetRepository::delete));
        assertThrows(BudgetDoesntExistException.class, () -> budgetService.performBudgetOperation(testBudget.getId(), budgetToUpdate -> {
            budgetToUpdate.setValue(500);
            budgetToUpdate.setBudgetType(BudgetType.INCOME);
        }));
    }


}
