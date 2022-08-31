package com.budgetmanager.DTOs;

import java.util.List;

public class CalcDto {
    private List<Integer> incomes;
    private List<Integer> expenses;

    public List<Integer> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Integer> expenses) {
        this.expenses = expenses;
    }

    public List<Integer> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Integer> incomes) {
        this.incomes = incomes;
    }
}
