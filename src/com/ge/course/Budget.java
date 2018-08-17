package com.ge.course;

import java.time.LocalDate;

public class Budget {
    private int amount;
    private LocalDate date;

    public Budget(LocalDate date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public long getOverlappingAmount(Period period) {
        return getDailyAmount() * period.getOverlappingDayCount(getPeriod());
    }

    private int getDailyAmount() {
        return amount / date.lengthOfMonth();
    }

    private LocalDate getStartDate() {
        return date.withDayOfMonth(1);
    }

    private LocalDate getEndDate() {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    private Period getPeriod() {
        return new Period(getStartDate(), getEndDate());
    }
}
