package org.upv.myfinances.database;

import org.upv.myfinances.model.MyCategory;
import org.upv.myfinances.model.MyTransaction;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TransactionDatabase {
    public List<MyTransaction> getAll();
    public List<MyTransaction> getAll(Date endDate);
    public List<MyTransaction> getExpenses();
    public List<MyTransaction> getIncomes();

    public List<MyTransaction> getInMonth(int month);
    public List<MyTransaction> getExpensesInMonth(int month);
    public List<MyTransaction> getIncomesInMonth(int month);

    List<MyTransaction> getExpensesByCategoryInMonth(MyCategory category, int month);
    List<MyTransaction> getIncomesByCategoryInMonth(MyCategory category, int month);

    MyTransaction get(long id);
    public boolean add(MyTransaction myTransaction);
    public boolean edit(MyTransaction myTransaction);
    public boolean remove();



}
