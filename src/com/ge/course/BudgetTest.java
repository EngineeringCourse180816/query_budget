package com.ge.course;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetTest {

    BudgetDao stubBudgetDao = mock(BudgetDao.class);
    BudgetCalculator budgetCalculator = new BudgetCalculator(stubBudgetDao);

    @Test
    public void no_budget() {
        givenBudgets();

        assertTotalEquals(0,
                of(2018, 8, 10),
                of(2018, 8, 15));
    }

    @Test
    public void start_end_is_same_day() {
        givenBudgets(budget(2018, 8, 310));

        assertTotalEquals(10,
                of(2018, 8, 10),
                of(2018, 8, 10));
    }

    @Test
    public void start_end_is_different_but_in_same_month() {
        givenBudgets(budget(2018, 8, 310));

        assertTotalEquals(60,
                of(2018, 8, 10),
                of(2018, 8, 15));
    }

    @Test
    public void start_is_before_budget_start() {
        givenBudgets(budget(2018, 8, 310));

        assertTotalEquals(150,
                of(2018, 7, 10),
                of(2018, 8, 15));
    }

    @Test
    public void end_is_after_budget_end() {
        givenBudgets(budget(2018, 8, 310));

        assertTotalEquals(220,
                of(2018, 8, 10),
                of(2018, 9, 15));
    }

    @Test
    public void end_is_before_budget_start() {
        givenBudgets(budget(2018, 8, 310));

        assertTotalEquals(0,
                of(2018, 7, 10),
                of(2018, 7, 15));
    }

    @Test
    public void start_is_after_budget_end() {
        givenBudgets(budget(2018, 8, 310));

        assertTotalEquals(0,
                of(2018, 9, 10),
                of(2018, 9, 15));
    }

    @Test
    public void two_months() {
        givenBudgets(
                budget(2018, 8, 310),
                budget(2018, 7, 310)
        );

        assertTotalEquals(220 + 150,
                of(2018, 7, 10),
                of(2018, 8, 15));
    }

    @Test
    public void acceptance_test() {
        givenBudgets(
                budget(2015, 12, 310),
                budget(2016, 2, 290)
        );

        assertTotalEquals(220 + 290,
                of(2015, 12, 10),
                of(2016, 3, 15));
    }

    private void assertTotalEquals(int expected, LocalDate start, LocalDate end) {
        int actual = budgetCalculator.queryBudget(start, end);

        assertEquals(expected, actual);
    }

    private Budget budget(int year, int month, int amount) {
        return new Budget(of(year, month, 1), amount);
    }

    private void givenBudgets(Budget... budget) {
        when(stubBudgetDao.findAll()).thenReturn(Arrays.asList(budget));
    }

//    @Test
//    public void budgetTest1(){
//        BudgetDao dao = new BudgetDao();
//        BudgetCalculator calc = new BudgetCalculator(dao);
//        assertEquals(calc.queryBudget(LocalDate.of(1991,02,02),LocalDate.of(2020,02,02)),580);
//    }
//
//    @Test
//    public void budgetTest2(){
//        BudgetDao dao = new BudgetDao();
//        BudgetCalculator calc = new BudgetCalculator(dao);
//        assertEquals(calc.queryBudget(LocalDate.of(2018,1,1),LocalDate.of(2018,4,30)),270);
//    }
//
//    @Test
//    public void budgetTest3(){
//        BudgetDao dao = new BudgetDao();
//        BudgetCalculator calc = new BudgetCalculator(dao);
//        assertEquals(calc.queryBudget(LocalDate.of(2018,4,1),LocalDate.of(2018,4,30)),270);
//    }
//
//    @Test
//    public void budgetTest4(){
//        BudgetDao dao = new BudgetDao();
//        BudgetCalculator calc = new BudgetCalculator(dao);
//        assertEquals(calc.queryBudget(LocalDate.of(2018,5,1),LocalDate.of(2018,5,2)),20);
//    }
//
//    @Test
//    public void budgetTest5(){
//        BudgetDao dao = new BudgetDao();
//        BudgetCalculator calc = new BudgetCalculator(dao);
//        assertEquals(calc.queryBudget(LocalDate.of(2018,4,29),LocalDate.of(2018,5,2)),38);
//    }
}
