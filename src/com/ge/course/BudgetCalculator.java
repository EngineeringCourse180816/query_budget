package com.ge.course;

import java.time.LocalDate;
import java.time.YearMonth;

public class BudgetCalculator {

    private BudgetDao budgetDao;

    public BudgetCalculator(BudgetDao dao) {
        budgetDao = dao;
    }

    public long queryBudget(LocalDate startDate, LocalDate endDate) {
        return queryTotal(new Period(startDate, endDate));
    }

    private long queryTotal(Period period) {
        if (YearMonth.from(period.getStartDate()).equals(YearMonth.from(period.getEndDate()))) {
            Budget budget = getAmountOfBudgetMonth(period.getStartDate());
            return budget.getDailyAmount() * period.getDayCount();
        }

        long calculatedBudget = 0;

        Budget firstBudget = getAmountOfBudgetMonth(period.getStartDate());
        calculatedBudget += firstBudget.getDailyAmount() * new Period(period.getStartDate(), firstBudget.getEndDate()).getDayCount();

        Budget lastBudget = getAmountOfBudgetMonth(period.getEndDate());
        calculatedBudget += lastBudget.getDailyAmount() * new Period(lastBudget.getStartDate(), period.getEndDate()).getDayCount();

        for (LocalDate currentStartDate = period.getStartDate().plusMonths(1).withDayOfMonth(1); !YearMonth.from(currentStartDate).equals(YearMonth.from(period.getEndDate())); currentStartDate = currentStartDate.plusMonths(1)) {
            Budget budget = getAmountOfBudgetMonth(currentStartDate);
            calculatedBudget += budget.getDailyAmount() * new Period(budget.getStartDate(), budget.getEndDate()).getDayCount();
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
