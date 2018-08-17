package com.ge.course;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getOverlappingDayCount(Period another) {
        LocalDate overlappingEnd = endDate.isBefore(another.endDate) ? endDate : another.endDate;
        LocalDate overlappingStart = startDate.isAfter(another.startDate) ? startDate : another.startDate;
        if (overlappingStart.isAfter(overlappingEnd))
            return 0;

        return new Period(overlappingStart, overlappingEnd).getDayCount();
    }

    private long getDayCount() {
        return DAYS.between(startDate, endDate) + 1;
    }
}
