import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BudgetTest {

    @Test
    public void budgetTest1(){
        BudgetDao dao = new BudgetDao();
        BudgetCalculator calc = new BudgetCalculator(dao);
        assertEquals(calc.queryBudget(LocalDate.of(1991,02,02),LocalDate.of(2020,02,02)),580);
    }

    @Test
    public void budgetTest2(){
        BudgetDao dao = new BudgetDao();
        BudgetCalculator calc = new BudgetCalculator(dao);
        assertEquals(calc.queryBudget(LocalDate.of(2018,1,1),LocalDate.of(2018,4,30)),270);
    }

    @Test
    public void budgetTest3(){
        BudgetDao dao = new BudgetDao();
        BudgetCalculator calc = new BudgetCalculator(dao);
        assertEquals(calc.queryBudget(LocalDate.of(2018,4,1),LocalDate.of(2018,4,30)),270);
    }

    @Test
    public void budgetTest4(){
        BudgetDao dao = new BudgetDao();
        BudgetCalculator calc = new BudgetCalculator(dao);
        assertEquals(calc.queryBudget(LocalDate.of(2018,5,1),LocalDate.of(2018,5,2)),20);
    }

    @Test
    public void budgetTest5(){
        BudgetDao dao = new BudgetDao();
        BudgetCalculator calc = new BudgetCalculator(dao);
        assertEquals(calc.queryBudget(LocalDate.of(2018,4,29),LocalDate.of(2018,5,2)),38);
    }
}
