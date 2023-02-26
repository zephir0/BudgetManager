package com.budgetmanager.budget.services;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
import com.budgetmanager.budget.exceptions.BudgetDoesntExistException;
import com.budgetmanager.budget.exceptions.BudgetValueNullException;
import com.budgetmanager.budget.exceptions.EmptyBudgetCategoryException;
import com.budgetmanager.budget.exceptions.EmptyBudgetTypeException;
import com.budgetmanager.budget.mapper.BudgetMapper;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    public void addBudget(BudgetDto budgetdto) {
        if (budgetdto.getExpenseCategory() == null && budgetdto.getIncomeCategory() == null) {
            throw new EmptyBudgetCategoryException("You need to insert an income or expense category");
        } else if (budgetdto.getValue() == 0) {
            throw new BudgetValueNullException("You need to insert an income or expense category");
        } else if (budgetdto.getBudgetType() == null) {
            throw new EmptyBudgetTypeException("You need to insert a budget type");
        } else {
            Budget budget = BudgetMapper.map(budgetdto, userService.getLoggedUser());
            budgetRepository.save(budget);
        }

    }


    public void changeBudget(Long id,
                             BudgetDto budgetDto) {
        performBudgetOperation(id, budget -> {
            budget.setValue(budgetDto.getValue());
            if (budget.getBudgetType() == BudgetType.INCOME) {
                budget.setIncomeCategory(budgetDto.getIncomeCategory());
            } else if (budget.getBudgetType() == BudgetType.EXPENSE) {
                budget.setExpenseCategory(budgetDto.getExpenseCategory());
            }
            budgetRepository.save(budget);
        });
    }

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
            throw new BudgetDoesntExistException("There is no budget with id " + id);
        });
    }

    public List<Budget> showAllBudget() {
        return new ArrayList<>(budgetRepository.findAllByUserId(userService.getLoggedUser().getId()));
    }

    @Transactional
    public void deleteAllBudgetsByUserId(Long id) {
        budgetRepository.deleteAllByUserId(id);
    }


    public List<Budget> findAllBudgetsByExpenseCategory(ExpenseCategory expenseCategory) {
        return budgetRepository.findAllByExpenseCategoryAndUserId(expenseCategory, userService.getLoggedUser().getId());
    }

    public List<Budget> findAllBudgetsByIncomeCategory(IncomeCategory incomeCategory) {
        return budgetRepository.findAllByIncomeCategoryAndUserId(incomeCategory, userService.getLoggedUser().getId());
    }


    public int countAllBudgetValue() {
        int expenses = 0;
        int incomes = 0;
        for (Budget budget : allBudgetListForUserIdAndBudgetType(BudgetType.INCOME)) {
            incomes += budget.getValue();
        }
        for (Budget budget : allBudgetListForUserIdAndBudgetType(BudgetType.EXPENSE)) {
            expenses -= budget.getValue();
        }
        return incomes - expenses;
    }


    public Iterable<Budget> allBudgetListForUserIdAndBudgetType(BudgetType budgetType) {
        return budgetRepository.findAllByUserIdAndBudgetType(userService.getLoggedUser().getId(), budgetType);
    }

    public List<Budget> showAllBudgetByUserId(Long id) {
        return new ArrayList<>(budgetRepository.findAllByUserId(id));
    }

    public List<Budget> showBudgetByHistoryDayNumberAndUserId(String day,
                                                              Long id) {
        return budgetRepository.findAllByHistoryDayNumberAndUserId(day, id);
    }


}
