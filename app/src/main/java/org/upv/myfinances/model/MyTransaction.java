package org.upv.myfinances.model;

import android.content.Intent;
import android.location.Location;

import com.orm.SugarRecord;

import org.upv.myfinances.utils.Constants;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

public class MyTransaction extends SugarRecord<MyTransaction> {
    private int transactionType;
    private float value;
    private String currencyCode;
    private Date timeStamp;
    private String description;
    private MyCategory myCategory;

    private MyAccount myAccount;
    private boolean isPeriodic;
    private int quantity;
    private DateRange range;
    private boolean isForever;
    private Date endDate;

    private boolean remindMe;

    public MyTransaction() {
    }

    public MyTransaction(MyTransaction transaction){
        transactionType = transaction.transactionType;
        value = transaction.value;
        currencyCode = transaction.currencyCode;
        timeStamp = Calendar.getInstance().getTime();
        description = transaction.description;
        myCategory = transaction.myCategory;
        myAccount = transaction.myAccount;
        isPeriodic = transaction.isPeriodic;
        quantity = transaction.quantity;
        range = transaction.range;
        isForever = transaction.isForever;
        endDate = transaction.endDate;
        remindMe = transaction.remindMe;
    }

    protected MyTransaction(int type){
        transactionType = type;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.getMaximum(Calendar.YEAR));
        endDate = calendar.getTime();
        quantity = Integer.MAX_VALUE;
        range = DateRange.Yearly;
    }

    public static MyTransaction newIncome(){
        return new MyTransaction(Constants.TRANSACTION_INCOME);
    }

    public static MyTransaction newExpense(){
        return new MyTransaction(Constants.TRANSACTION_EXPENSE);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }

    public void setCurrency(Currency currency) {
        this.currencyCode = currency.getCurrencyCode();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyCategory getMyCategory() {
        return myCategory;
    }

    public void setMyCategory(MyCategory myCategory) {
        this.myCategory = myCategory;
    }

    public MyAccount getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(MyAccount myAccount) {
        this.myAccount = myAccount;
    }

    public boolean isPeriodic() {
        return isPeriodic;
    }

    public void setPeriodic(boolean periodic) {
        isPeriodic = periodic;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }


    public boolean isForever() {
        return isForever;
    }

    public void setForever(boolean forever) {
        isForever = forever;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRemindMe() {
        return remindMe;
    }

    public void setRemindMe(boolean remindMe) {
        this.remindMe = remindMe;
    }

    public boolean isIncome(){
        return transactionType == Constants.TRANSACTION_INCOME;
    }

    public boolean isExpense(){
        return transactionType == Constants.TRANSACTION_EXPENSE;
    }
}
