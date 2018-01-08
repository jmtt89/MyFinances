package org.upv.myfinances.database;

import org.upv.myfinances.model.MyBudget;
import org.upv.myfinances.model.MyCategory;

import java.util.List;

public interface BudgetDatabase {
    public List<MyBudget> get();
    public MyBudget get(long id);
    public boolean add(MyBudget budget);
    public boolean edit(MyBudget budget);

    List<MyBudget> getGlobalInMonth(int month);
}
