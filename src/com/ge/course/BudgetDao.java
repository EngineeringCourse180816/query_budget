package com.ge.course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetDao {

    List<Budget> budgetList = new ArrayList<>();

    public BudgetDao(){

        budgetList.add(new Budget(LocalDate.of(2018,5,1),310));
        budgetList.add(new Budget(LocalDate.of(2018,4,1),270));
    }

    public List<Budget> findAll(){
        return budgetList;
    }
}
