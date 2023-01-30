package com.budgetmanager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;

@Entity
@Cacheable(cacheNames = "budgets")
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_type")
    private BudgetType budgetType;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_category")
    private IncomeCategory incomeCategory;
    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private ExpenseCategory expenseCategory;

    @Column(name = "value")
    private int value;

    @Column(name = "history_day_number")
    private String historyDayNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public String getHistoryDayNumber() {
        return historyDayNumber;
    }

    public void setHistoryDayNumber(String historyDayNumber) {
        this.historyDayNumber = historyDayNumber;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return value;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }
}
