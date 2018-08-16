package com.ge.course;

import java.time.LocalDate;

public class Budget {
    private int budget;
    private LocalDate date;

    public Budget(LocalDate date, int budget) {
        this.date = date;
        this.budget = budget;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
