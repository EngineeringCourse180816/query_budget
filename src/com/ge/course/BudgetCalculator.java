package com.ge.course;

import java.time.LocalDate;

public class BudgetCalculator {

    private BudgetDao budgetDao;

    public BudgetCalculator(BudgetDao dao) {
        budgetDao = dao;
    }

    public long queryBudget(LocalDate startDate, LocalDate endDate) {
        return queryTotal(new Period(startDate, endDate));
    }

    private long queryTotal(Period period) {
        long calculatedBudget = 0;

        for (Budget budget : budgetDao.findAll()) {
            calculatedBudget += budget.getDailyAmount() * period.getOverlappingDayCount(budget.getPeriod());
        }

        return calculatedBudget;
    }

}
