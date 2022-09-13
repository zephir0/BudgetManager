package com.budgetmanager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int income;
    private int expense;
    @Column(name = "history_day_number")
    private int historyDayNumber;

//    private int dayNumber;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public int getHistoryDayNumber() {
        return historyDayNumber;
    }

    public void setHistoryDayNumber(int historyDayNumber) {
        this.historyDayNumber = historyDayNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Budget build(int income,
                        int expense) {
        Budget budget = new Budget();
        budget.setIncome(income);
        budget.setExpense(expense);
        return budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }
}