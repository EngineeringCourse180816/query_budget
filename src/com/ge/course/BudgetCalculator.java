package com.ge.course;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetCalculator {

    private BudgetDao budgetDao;

    public BudgetCalculator(BudgetDao dao) {
        budgetDao = dao;
    }

    public long queryBudget(LocalDate startDate, LocalDate endDate) {
        long totalDays = DAYS.between(startDate, endDate) + 1;

        if (YearMonth.from(startDate).equals(YearMonth.from(endDate))) {
            return getAmountOfBudgetMonth(startDate) / startDate.lengthOfMonth() * totalDays;
        }

        long calculatedBudget = 0;

        long firstMonthDays = DAYS.between(startDate, startDate.withDayOfMonth(startDate.lengthOfMonth())) + 1;
        calculatedBudget += getAmountOfBudgetMonth(startDate) / startDate.lengthOfMonth() * firstMonthDays;

        long lastMonthDays = DAYS.between(endDate.withDayOfMonth(1), endDate) + 1;
        calculatedBudget += getAmountOfBudgetMonth(endDate) / endDate.lengthOfMonth() * lastMonthDays;

        for (LocalDate currentStartDate = startDate.plusMonths(1).withDayOfMonth(1); !YearMonth.from(currentStartDate).equals(YearMonth.from(endDate)); currentStartDate.plusMonths(1)) {
            calculatedBudget += getAmountOfBudgetMonth(currentStartDate);
        }

        return calculatedBudget;
    }

    private int getAmountOfBudgetMonth(LocalDate date) {
        for (Budget budget : budgetDao.findAll()) {
            if (YearMonth.from(budget.getDate()).equals(YearMonth.from(date))) {
                return budget.getBudget();
            }
        }
        return 0;
    }
}
