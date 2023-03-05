package com.budgetmanager.budget.dtos;

import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BudgetDto {
    private final int value;
    private final BudgetType budgetType;
    private final ExpenseCategory expenseCategory;
    private final IncomeCategory incomeCategory;


    @JsonCreator
    public BudgetDto(@JsonProperty("value") int value,
                     @JsonProperty("budgetType") BudgetType budgetType,
                     @JsonProperty("expenseCategory") ExpenseCategory expenseCategory,
                     @JsonProperty("incomeCategory") IncomeCategory incomeCategory) {
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
