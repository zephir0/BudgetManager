package com.budgetmanager.budget.services;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.*;
import com.budgetmanager.budget.exceptions.*;
import com.budgetmanager.budget.mapper.BudgetMapper;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;


    public BudgetService(BudgetRepository budgetRepository,
                         UserService userService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
    }

    public void addBudget(BudgetDto budgetDto) {
        if (budgetDto.getExpenseCategory() == null && budgetDto.getIncomeCategory() == null)
            throw new InvalidBudgetCategoryException("Incorrect budget category value.");
        if (budgetDto.getBudgetType() == null) throw new InvalidBudgetTypeException("Incorrect budget type");
        if (budgetDto.getValue() == 0) throw new InvalidBudgetValueException("Budget value should be higher than 0");
        budgetRepository.save(BudgetMapper.map(budgetDto, userService.getLoggedUser()));
    }

    public void changeBudget(Long id,
                             BudgetDto budgetDto) {
        performBudgetOperation(id, budget -> {
            budget.setValue(budgetDto.getValue());
            budget.setBudgetType(budgetDto.getBudgetType());
            if (budgetDto.getBudgetType().equals(BudgetType.INCOME)) {
                budget.setIncomeCategory(budgetDto.getIncomeCategory());
            } else {
                budget.setExpenseCategory(budgetDto.getExpenseCategory());
            }
            budgetRepository.save(budget);
        });
    }

    @Transactional
    public void deleteAllBudgetsByUserId(Long id) {
        List<Budget> budgets = budgetRepository.findAllByUserId(id);
        if (budgets.isEmpty()) {
            throw new BudgetsNotFoundException("There is no budgets");
        } else
            budgets.stream().filter(budget -> budget.getUser().equals(userService.getLoggedUser()) || budget.getUser().getRole().equals(UserRoles.ADMIN)).forEach(budgetRepository::delete);
    }

    @Transactional
    public void deleteByBudgetId(Long id) {
        performBudgetOperation(id, budgetRepository::delete);
    }


    public void performBudgetOperation(Long id,
                                       Consumer<Budget> operation) {
        budgetRepository.findById(id).ifPresentOrElse(budget -> {
            if (budget.getUser().equals(userService.getLoggedUser()) || budget.getUser().getRole().equals(UserRoles.ADMIN)) {
                operation.accept(budget);
            } else throw new NotAuthorizedException("You are not authorized to perform this operation on that budget");
        }, () -> {
            throw new BudgetNotFoundException("There is no budget with id " + id);
        });
    }

    public List<Budget> findAllBudgetsForLoggedUser() {
        return new ArrayList<>(budgetRepository.findAllByUserId(userService.getLoggedUser().getId()));
    }


    public List<Budget> findAllBudgetsByCategoryForUser(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");
        if (category instanceof ExpenseCategory) {
            return budgetRepository.findAllByExpenseCategoryAndUserId((ExpenseCategory) category, userService.getLoggedUser().getId());
        } else if (category instanceof IncomeCategory) {
            return budgetRepository.findAllByIncomeCategoryAndUserId((IncomeCategory) category, userService.getLoggedUser().getId());
        } else {
            throw new IllegalArgumentException("Invalid category type");
        }
    }


    public List<Budget> findBudgetByHistoryDayNumberAndUserId(String day,
                                                              Long id) {
        return budgetRepository.findAllByHistoryDayNumberAndUserId(day, id);
    }


}
