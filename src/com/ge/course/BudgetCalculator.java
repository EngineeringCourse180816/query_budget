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

        if (YearMonth.from(startDate).equals(YearMonth.from(endDate))) {
            Budget budget = getAmountOfBudgetMonth(startDate);
            return budget.getDailyAmount() * (DAYS.between(startDate, endDate) + 1);
        }

        long calculatedBudget = 0;

        Budget firstBudget = getAmountOfBudgetMonth(startDate);
        calculatedBudget += firstBudget.getDailyAmount() * (DAYS.between(startDate, startDate.withDayOfMonth(startDate.lengthOfMonth())) + 1);

        Budget lastBudget = getAmountOfBudgetMonth(endDate);
        calculatedBudget += lastBudget.getDailyAmount() * (DAYS.between(endDate.withDayOfMonth(1), endDate) + 1);

        for (LocalDate currentStartDate = startDate.plusMonths(1).withDayOfMonth(1); !YearMonth.from(currentStartDate).equals(YearMonth.from(endDate)); currentStartDate = currentStartDate.plusMonths(1)) {
            calculatedBudget += getAmountOfBudgetMonth(currentStartDate).getAmount();
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
