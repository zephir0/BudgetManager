package com.budgetmanager.services;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.*;
import com.budgetmanager.repositories.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Budget budget = BudgetMapper.map(budgetdto, userService.getLoggedUser());
        budgetRepository.save(budget);
    }


    public void changeBudget(Long id,
                             BudgetDto budgetDto) {
        budgetRepository.findById(id).ifPresent(budget -> {
            budget.setValue(budgetDto.getValue());
            budget.setBudgetType(budgetDto.getBudgetType());
            budgetRepository.save(budget);
        });
    }

    public List<Budget> showAllBudget() {
        return new ArrayList<>(budgetRepository.findAllByUserId(getLoggedUserId()));
    }

    public void deleteByBudgetId(Long id) {
        budgetRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllBudgetsByUserId(Long id) {
        budgetRepository.deleteAllByUserId(id);
    }


    public List<Budget> findAllBudgetsByExpenseCategory(ExpenseCategory expenseCategory) {
        return budgetRepository.findAllByExpenseCategoryAndUserId(expenseCategory, getLoggedUserId());
    }

    public List<Budget> findAllBudgetsByIncomeCategory(IncomeCategory incomeCategory) {
        return budgetRepository.findAllByIncomeCategoryAndUserId(incomeCategory, getLoggedUserId());
    }

    public Long getLoggedUserId() {
        return userService.getLoggedUser().getId();
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
        return budgetRepository.findAllByUserIdAndBudgetType(getLoggedUserId(), budgetType);
    }

    public List<Budget> showAllBudgetByUserId(Long id) {
        return new ArrayList<>(budgetRepository.findAllByUserId(id));
    }

    public List<Budget> showBudgetByHistoryDayNumberAndUserId(String day,
                                                              Long id) {
        return budgetRepository.findAllByHistoryDayNumberAndUserId(day, id);
    }

    static class BudgetMapper {
        static public Budget map(BudgetDto budgetDto,
                                 User user) {
            Budget budget = new Budget();
            budget.setUser(user);
            budget.setValue(budgetDto.getValue());
            setBudgetCategory(budget, budgetDto);
            budget.setBudgetType(budgetDto.getBudgetType());
            budget.setHistoryDayNumber(LocalDate.now().toString());
            return budget;
        }

        static public void setBudgetCategory(Budget budget,
                                             BudgetDto budgetDto) {
            if (budgetDto.getBudgetType().equals(BudgetType.EXPENSE))
                budget.setExpenseCategory(budgetDto.getExpenseCategory());
            else budget.setIncomeCategory(budgetDto.getIncomeCategory());
        }
    }
}
