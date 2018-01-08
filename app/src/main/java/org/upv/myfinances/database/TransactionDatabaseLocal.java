package org.upv.myfinances.database;

import android.util.Log;

import org.upv.myfinances.model.MyCategory;
import org.upv.myfinances.model.MyTransaction;
import org.upv.myfinances.utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionDatabaseLocal implements TransactionDatabase {
    private static final String TAG = "TransactionDatabase";
    private static TransactionDatabaseLocal ourInstance;

    public static TransactionDatabaseLocal getInstance() {
        if(ourInstance == null){
            ourInstance = new TransactionDatabaseLocal();
        }
        return ourInstance;
    }

    private TransactionDatabaseLocal() {
        MyTransaction.findById(MyTransaction.class, (long) 1);
    }

    @Override
    public List<MyTransaction> getAll() {
        return MyTransaction.listAll(MyTransaction.class);
    }

    @Override
    public List<MyTransaction> getAll(Date endDate) {
        return MyTransaction.find(MyTransaction.class, "time_stamp <= ?", endDate.getTime() +"");
    }

    @Override
    public List<MyTransaction> getExpenses() {
        return MyTransaction.find(MyTransaction.class, "transaction_type = ?", String.valueOf(Constants.TRANSACTION_EXPENSE));
    }

    @Override
    public List<MyTransaction> getIncomes() {
        return MyTransaction.find(MyTransaction.class, "transaction_type = ?", String.valueOf(Constants.TRANSACTION_INCOME));
    }

    @Override
    public List<MyTransaction> getInMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long from = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long to = calendar.getTime().getTime();
        return MyTransaction.find(MyTransaction.class, "time_stamp between ? and ?", from +"", to+"");
    }

    @Override
    public List<MyTransaction> getExpensesInMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long from = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long to = calendar.getTime().getTime();
        return MyTransaction.find(MyTransaction.class, "transaction_type = ? AND time_stamp between ? and ?", String.valueOf(Constants.TRANSACTION_EXPENSE), from+"", to+"");
    }

    @Override
    public List<MyTransaction> getIncomesInMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long from = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long to = calendar.getTime().getTime();
        return MyTransaction.find(MyTransaction.class, "transaction_type = ? AND time_stamp between ? and ?", String.valueOf(Constants.TRANSACTION_INCOME), from + "", to + "");
    }

    @Override
    public List<MyTransaction> getExpensesByCategoryInMonth(MyCategory category, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long from = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long to = calendar.getTime().getTime();
        return MyTransaction.find(MyTransaction.class, "my_category = ? AND transaction_type = ? AND time_stamp between ? and ?", category.getId() + "" ,String.valueOf(Constants.TRANSACTION_EXPENSE), from + "", to + "");
    }

    @Override
    public List<MyTransaction> getIncomesByCategoryInMonth(MyCategory category, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long from = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long to = calendar.getTime().getTime();
        return MyTransaction.find(MyTransaction.class, "my_category = ? AND transaction_type = ? AND time_stamp between ? and ?", category.getId() + "" ,String.valueOf(Constants.TRANSACTION_INCOME), from + "", to + "");
    }

    @Override
    public MyTransaction get(long id) {
        return MyTransaction.findById(MyTransaction.class, id);
    }

    @Override
    public boolean add(MyTransaction myTransaction) {
        try {
            myTransaction.save();
        }catch(Exception _e){
            Log.e(TAG, "add: ", _e);
            return false;
        }
        return true;
    }

    @Override
    public boolean edit(MyTransaction myTransaction) {
        try {
            myTransaction.save();
        }catch(Exception _e){
            Log.e(TAG, "add: ", _e);
            return false;
        }
        return true;
    }

    @Override
    public boolean remove() {
        return false;
    }
}
