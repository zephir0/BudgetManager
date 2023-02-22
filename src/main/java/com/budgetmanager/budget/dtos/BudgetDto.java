package com.budgetmanager.budget.dtos;

import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;

public class BudgetDto {
    private final int value;
    private final BudgetType budgetType;
    private final ExpenseCategory expenseCategory;
    private final IncomeCategory incomeCategory;

    public BudgetDto(int value,
                     BudgetType budgetType,
                     ExpenseCategory expenseCategory,
                     IncomeCategory incomeCategory) {
        this.value = value;
        this.budgetType = budgetType;
        this.expenseCategory = expenseCategory;
        this.incomeCategory = incomeCategory;
    }


    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }


    public int getValue() {
        return value;
    }

    public BudgetType getBudgetType() {
        return budgetType;
    }
}