package org.upv.myfinances.model;

import com.orm.SugarRecord;

import org.upv.myfinances.R;

public class MyCategory extends SugarRecord<MyCategory> {
    private boolean isIncome;
    private String title;
    private int icon;
    private String color;

    public MyCategory() {
    }

    public MyCategory(boolean isIncome, String title, String color) {
        this.isIncome = isIncome;
        this.title = title;
        this.icon = R.drawable.ic_category_24dp;
        this.color = color;
    }

    public MyCategory(boolean isIncome, String title, int icon, String color) {
        this.isIncome = isIncome;
        this.title = title;
        this.icon = icon;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyCategory category = (MyCategory) o;

        if (isIncome != category.isIncome) return false;
        if (!title.equals(category.title)) return false;
        return color.equals(category.color);
    }

    @Override
    public int hashCode() {
        int result = (isIncome ? 1 : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
