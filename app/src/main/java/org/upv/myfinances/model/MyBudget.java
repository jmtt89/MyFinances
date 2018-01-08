package org.upv.myfinances.model;

import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.Date;

public class MyBudget extends SugarRecord<MyBudget> {
    private Date timeStamp;
    private float amount;
    private MyCategory category;
    private boolean isGlobal;
    private boolean notifyMe;
    private boolean isPeriodic;

    public MyBudget() {
    }

    public MyBudget(Date timeStamp, float amount, MyCategory category, boolean isGlobal, boolean notifyMe, boolean isPeriodic) {
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.category = category;
        this.isGlobal = isGlobal;
        this.notifyMe = notifyMe;
        this.isPeriodic = isPeriodic;
    }

    public MyBudget(MyBudget budget) {
        this.timeStamp = Calendar.getInstance().getTime();
        this.amount = budget.amount;
        this.category = budget.category;
        this.isGlobal = budget.isGlobal;
        this.notifyMe = budget.notifyMe;
        this.isPeriodic = budget.isPeriodic;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public MyCategory getCategory() {
        return category;
    }

    public void setCategory(MyCategory category) {
        this.category = category;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public boolean isNotifyMe() {
        return notifyMe;
    }

    public void setNotifyMe(boolean notifyMe) {
        this.notifyMe = notifyMe;
    }

    public boolean isPeriodic() {
        return isPeriodic;
    }

    public void setPeriodic(boolean periodic) {
        isPeriodic = periodic;
    }
}
