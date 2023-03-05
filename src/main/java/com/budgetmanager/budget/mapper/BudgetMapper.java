package com.budgetmanager.budget.mapper;

import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.user.entities.User;

import java.time.LocalDate;

public class BudgetMapper {
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