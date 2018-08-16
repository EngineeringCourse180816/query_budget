import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetCalculator {

    private BudgetDao budgetDao;

    public BudgetCalculator(BudgetDao dao){
        budgetDao = dao;
//        LocalDate d1 = LocalDate.of(2018,1,30);
//        LocalDate d2 = LocalDate.of(d1.getYear(),d1.getMonthValue()+1,1);
//        System.out.println(DAYS.between(d1, d2));

    }

    public int queryBudget(LocalDate startDate,LocalDate endDate){
        int calculatedBudget = 0;

        long totalDays = DAYS.between(startDate,endDate)+1;
        long daysLeft = totalDays;


        if(startDate.getYear() == endDate.getYear() && startDate.getMonth() == endDate.getMonth()){
            calculatedBudget = (int)(getBudgetOfMonth(startDate)/startDate.lengthOfMonth() * totalDays);
        }
        else{
            int firstMounthDays = (int)DAYS.between(startDate,LocalDate.of(startDate.getYear(),startDate.getMonth(),startDate.lengthOfMonth()))+1;
            calculatedBudget += (int)(getBudgetOfMonth(startDate))/startDate.lengthOfMonth() * firstMounthDays;

            int lastMonthDays = (int)DAYS.between(LocalDate.of(endDate.getYear(),endDate.getMonth(),1),endDate)+1;
            calculatedBudget += (int)(getBudgetOfMonth(endDate))/endDate.lengthOfMonth() * lastMonthDays;
            daysLeft = daysLeft - firstMounthDays - lastMonthDays;

            LocalDate currentStartDate = startDate;
            while(daysLeft != 0){
                LocalDate date;
                if(currentStartDate.getMonthValue() == 12){
                    date = LocalDate.of(currentStartDate.getYear()+1,1,1);
                    calculatedBudget += getBudgetOfMonth(date);
                }
                else{
                    date = LocalDate.of(currentStartDate.getYear(),currentStartDate.getMonthValue()+1,1);
                    calculatedBudget += getBudgetOfMonth(date);
                }
                daysLeft -= date.lengthOfMonth();
                currentStartDate = date;

            }


        }
        return calculatedBudget;

    }

    private int getBudgetOfMonth(LocalDate date){
        List<Budget> allBudgetList = budgetDao.findAll();
        for(Budget budget : allBudgetList){
            if(budget.getDate().getYear()==date.getYear() && budget.getDate().getMonth()==date.getMonth()){
                return budget.getBudget();
            }
        }
        return 0;
    }
}
