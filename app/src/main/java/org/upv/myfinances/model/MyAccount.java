package org.upv.myfinances.model;

import android.graphics.Color;

import com.orm.SugarRecord;

public class MyAccount extends SugarRecord<MyAccount> {
    private String title;
    private int balance;
    private AccountType type;
    private Color color;

    public MyAccount() {
    }

    public MyAccount(String title, int balance, AccountType type, Color color) {
        this.title = title;
        this.balance = balance;
        this.type = type;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
