package com.budgetmanager.budget.services;

import com.budgetmanager.budget.dtos.CalcDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalcService {
    public String yearlyBudget(CalcDto calcDto) {
        return "Total =  " +
                calcDto.getIncomes()
                + " " + "+" + " " +
                calcDto.getExpenses()
                + " " + "=" +
                calculateYearlyBudget(calcDto.getIncomes(), calcDto.getExpenses());
    }

    public int calculateYearlyBudget(List<Integer> incomes,
                                     List<Integer> expenses) {
        int result = 0;
        for (Integer income : incomes) {
            result += income;
        }
        for (Integer expense : expenses) {
            result -= expense;
        }
        return result;
    }
}
