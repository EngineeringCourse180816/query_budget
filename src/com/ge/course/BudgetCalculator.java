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

        int lastMonthDays = (int) DAYS.between(LocalDate.of(endDate.getYear(), endDate.getMonth(), 1), endDate) + 1;
        calculatedBudget += (int) (getAmountOfBudgetMonth(endDate)) / endDate.lengthOfMonth() * lastMonthDays;

        long daysLeft = totalDays;
        daysLeft = daysLeft - firstMonthDays - lastMonthDays;

        LocalDate currentStartDate = startDate;
        while (daysLeft != 0) {
            LocalDate date;
            if (currentStartDate.getMonthValue() == 12) {
                date = LocalDate.of(currentStartDate.getYear() + 1, 1, 1);
                calculatedBudget += getAmountOfBudgetMonth(date);
            } else {
                date = LocalDate.of(currentStartDate.getYear(), currentStartDate.getMonthValue() + 1, 1);
                calculatedBudget += getAmountOfBudgetMonth(date);
            }
            daysLeft -= date.lengthOfMonth();
            currentStartDate = date;

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
