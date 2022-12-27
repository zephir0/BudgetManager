package com.budgetmanager.DTOs;

public class BudgetDto {
    private final Long id;
    private final int income;
    private final int expense;

    public BudgetDto(Long id,
                     int income,
                     int expense) {
        this.id = id;
        this.income = income;
        this.expense = expense;
    }

    public Long getId() {
        return id;
    }

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expense;
    }
}
