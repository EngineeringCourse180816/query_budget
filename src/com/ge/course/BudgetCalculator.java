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
            Budget budget = getAmountOfBudgetMonth(startDate);
            return budget.getBudget() / budget.getDate().lengthOfMonth() * totalDays;
        }

        long calculatedBudget = 0;

        long firstMonthDays = DAYS.between(startDate, startDate.withDayOfMonth(startDate.lengthOfMonth())) + 1;
        Budget firstBudget = getAmountOfBudgetMonth(startDate);
        calculatedBudget += firstBudget.getBudget() / firstBudget.getDate().lengthOfMonth() * firstMonthDays;

        long lastMonthDays = DAYS.between(endDate.withDayOfMonth(1), endDate) + 1;
        Budget lastBudget = getAmountOfBudgetMonth(endDate);
        calculatedBudget += lastBudget.getBudget() / lastBudget.getDate().lengthOfMonth() * lastMonthDays;

        for (LocalDate currentStartDate = startDate.plusMonths(1).withDayOfMonth(1); !YearMonth.from(currentStartDate).equals(YearMonth.from(endDate)); currentStartDate = currentStartDate.plusMonths(1)) {
            calculatedBudget += getAmountOfBudgetMonth(currentStartDate).getBudget();
        }

        return calculatedBudget;
    }

    private Budget getAmountOfBudgetMonth(LocalDate date) {
        for (Budget budget : budgetDao.findAll()) {
            if (YearMonth.from(budget.getDate()).equals(YearMonth.from(date))) {
                return budget;
            }
        }
        return new Budget(date, 0);
    }
}
