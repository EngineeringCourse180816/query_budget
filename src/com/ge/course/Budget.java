package com.ge.course;

import java.time.LocalDate;

public class Budget {
    private int amount;
    private LocalDate date;

    public Budget(LocalDate date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public void setBudget(int budget) {
        amount = budget;
    }

    public int getDailyAmount() {
        return amount / date.lengthOfMonth();
    }
}
