package org.upv.myfinances.controller;

import org.upv.myfinances.database.BudgetDatabase;
import org.upv.myfinances.database.BudgetDatabaseLocal;
import org.upv.myfinances.database.CategoriesDatabase;
import org.upv.myfinances.database.CategoriesDatabaseLocal;
import org.upv.myfinances.database.TransactionDatabase;
import org.upv.myfinances.database.TransactionDatabaseLocal;
import org.upv.myfinances.model.MyBudget;
import org.upv.myfinances.model.MyCategory;
import org.upv.myfinances.model.MyTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
    private final TransactionDatabase databaseT;
    private final CategoriesDatabase databaseC;
    private final BudgetDatabase databaseB;
    private static Controller ourInstance;

    public static Controller getInstance() {
        if(ourInstance == null){
            ourInstance = new Controller();
        }
        return ourInstance;
    }

    private Controller() {
        databaseT = TransactionDatabaseLocal.getInstance();
        databaseC = CategoriesDatabaseLocal.getInstance();
        databaseB = BudgetDatabaseLocal.getInstance();
    }

    public List<MyTransaction> getTransactionList(){
        return databaseT.getAll();
    }

    private List<MyTransaction> getTransactionList(Date endDate) {
        return databaseT.getAll(endDate);
    }


    public boolean addNewTransaction(MyTransaction myTransaction){
        return databaseT.add(myTransaction);
    }

    public boolean editTransaction(MyTransaction myTransaction){
        return databaseT.edit(myTransaction);
    }

    public boolean removeTransaction(MyTransaction myTransaction){
        return databaseT.remove();
    }


    public MyCategory getDefaultIncomeCategory() {
        return databaseC.getIncomes().get(0);
    }

    public MyCategory getDefaultExpenseCategory() {
        return databaseC.getExpenses().get(0);
    }

    public MyTransaction getTransactionById(long id) {
        return databaseT.get(id);
    }


    public List<MyCategory> getExpensesCategories(){
        return databaseC.getExpenses();
    }

    public List<MyCategory> getIncomesCategories(){
        return databaseC.getIncomes();
    }

    public boolean addNewCategory(MyCategory category) {
        return databaseC.add(category);
    }

    public boolean editCategory(MyCategory category) {
        return databaseC.edit(category);
    }

    public MyCategory getCategoryById(long id) {
        return databaseC.get(id);
    }


    public float getBalance() {
        float balance = 0f;
        for (MyTransaction transaction: getTransactionList()) {
            if(transaction.isIncome()){
                balance += transaction.getValue();
            }else{
                balance -= transaction.getValue();
            }
        }
        return balance;
    }

    public float getBalance(Date endDate) {
        float balance = 0f;
        for (MyTransaction transaction: getTransactionList(endDate)) {
            if(transaction.isIncome()){
                balance += transaction.getValue();
            }else{
                balance -= transaction.getValue();
            }
        }
        return balance;
    }

    public float getBalance(int month) {
        float balance = 0f;
        List<MyTransaction> transactions = new ArrayList<>(databaseT.getExpensesInMonth(month));
        transactions.addAll(databaseT.getIncomesInMonth(month));
        for (MyTransaction transaction: transactions) {
            if(transaction.isIncome()){
                balance += transaction.getValue();
            }else{
                balance -= transaction.getValue();
            }
        }
        return balance;
    }

    public float getBalance(MyCategory category, int month) {
        List<MyTransaction> transactions = new ArrayList<>(databaseT.getExpensesByCategoryInMonth(category, month));
        transactions.addAll(databaseT.getIncomesByCategoryInMonth(category, month));
        float balance = 0f;
        for (MyTransaction transaction: transactions) {
            if(transaction.isIncome()){
                balance += transaction.getValue();
            }else{
                balance -= transaction.getValue();
            }
        }
        return balance;
    }

    public List<MyTransaction> getIncomes() {
        return databaseT.getIncomes();
    }

    public List<MyTransaction> getExpenses() {
        return databaseT.getExpenses();
    }

    public List<MyTransaction> getIncomesInMonth(int month) {
        return databaseT.getIncomesInMonth(month);
    }

    public List<MyTransaction> getExpensesInMonth(int month) {
        return databaseT.getExpensesInMonth(month);
    }

    public float getIncomeTotal() {
        List<MyTransaction> transactions = databaseT.getIncomes();
        float accum = 0f;
        for (MyTransaction transaction: transactions) {
            accum += transaction.getValue();
        }
        return accum;
    }

    public float getExpensesTotal() {
        List<MyTransaction> transactions = databaseT.getExpenses();
        float accum = 0f;
        for (MyTransaction transaction: transactions) {
            accum += transaction.getValue();
        }
        return accum;
    }

    public float getIncomeTotalInMonth(int month) {
        List<MyTransaction> transactions = databaseT.getIncomesInMonth(month);
        float accum = 0f;
        for (MyTransaction transaction: transactions) {
            accum += transaction.getValue();
        }
        return accum;
    }

    public float getExpensesTotalInMonth(int month) {
        List<MyTransaction> transactions = databaseT.getExpensesInMonth(month);
        float accum = 0f;
        for (MyTransaction transaction: transactions) {
            accum += transaction.getValue();
        }
        return accum;
    }

    public float getExpensesByCategoryInMonth(MyCategory category, int month) {
        List<MyTransaction> transactions = databaseT.getExpensesByCategoryInMonth(category, month);
        float accum = 0f;
        for (MyTransaction transaction: transactions) {
            accum += transaction.getValue();
        }
        return accum;
    }


    public List<MyBudget> getAllBudget(){
        return databaseB.get();
    }

    public float getMaxBudgetInMonth(int month) {
        List<MyBudget> budgets = databaseB.getGlobalInMonth(month);
        float max = -1;
        for (MyBudget budget:budgets) {
            max = Math.max(max, budget.getAmount());
        }
        return max;
    }

    public boolean addNewBudget(MyBudget budget) {
        return databaseB.add(budget);
    }

    public boolean editBudget(MyBudget budget){
        return databaseB.edit(budget);
    }

    public MyBudget getBudgetById(long id) {
        return databaseB.get(id);
    }
}
