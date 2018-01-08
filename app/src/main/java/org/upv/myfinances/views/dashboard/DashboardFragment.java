package org.upv.myfinances.views.dashboard;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyCategory;
import org.upv.myfinances.model.MyTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardFragment extends Fragment {

    @BindView(R.id.income_total)
    TextView incomeTotal;
    @BindView(R.id.expenses_total)
    TextView expensesTotal;
    @BindView(R.id.overview_balance_card)
    CardView overviewBalanceCard;
    @BindView(R.id.overview_categories_card_pie_chart)
    PieChart overviewCategoriesCardPieChart;
    @BindView(R.id.expense_category_a)
    TextView expenseCategoryA;
    @BindView(R.id.expense_category_a_value)
    TextView expenseCategoryAValue;
    @BindView(R.id.expense_category_b)
    TextView expenseCategoryB;
    @BindView(R.id.expense_category_b_value)
    TextView expenseCategoryBValue;
    @BindView(R.id.expense_category_c)
    TextView expenseCategoryC;
    @BindView(R.id.expense_category_c_value)
    TextView expenseCategoryCValue;
    @BindView(R.id.expense_others)
    TextView expenseOthers;
    @BindView(R.id.expense_other_value)
    TextView expenseOtherValue;
    @BindView(R.id.overview_categories_card)
    CardView overviewCategoriesCard;
    @BindView(R.id.budget_value)
    TextView budgetValue;
    @BindView(R.id.budget_spent)
    TextView budgetSpent;
    @BindView(R.id.budget_predicted)
    TextView budgetPredicted;
    @BindView(R.id.budget_monthly_progress)
    ProgressBar budgetMonthlyProgress;
    @BindView(R.id.budget_remaining)
    TextView budgetRemaining;
    @BindView(R.id.overview_monthly_budget)
    CardView overviewMonthlyBudget;
    @BindView(R.id.overview_line_chart)
    LineChart overviewMonthlyBudgeLineChart;
    @BindView(R.id.overview_monthly_budge)
    CardView overvoewLastWeerk;
    @BindView(R.id.overview_monthly_balance_card_bar_chart)
    BarChart overviewMonthlyBalanceCardBarChart;
    @BindView(R.id.income_value)
    TextView incomeValue;
    @BindView(R.id.expenses_value)
    TextView expensesValue;
    @BindView(R.id.balance_total)
    TextView balanceTotal;
    @BindView(R.id.overview_monthly_balance)
    CardView overviewMonthlyBalance;

    @BindView(R.id.expense_category_a_wrapper)
    LinearLayout expenseCategoryAWrapper;
    @BindView(R.id.expense_category_b_wrapper)
    LinearLayout expenseCategoryBWrapper;
    @BindView(R.id.expense_category_c_wrapper)
    LinearLayout expenseCategoryCWrapper;
    @BindView(R.id.expense_others_wrapper)
    LinearLayout expenseOthersWrapper;
    @BindView(R.id.overview_monthly_balance_title)
    TextView overviewMonthlyBalanceTitle;


    Controller controller;
    Unbinder unbinder;

    public DashboardFragment() {
        controller = Controller.getInstance();
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showOverviewCard();
        showMonthlyBudgetCard();
        showMonthlyBalanceCard();
        showExpensesByCategoryCard();
        showBalanceTimelineCard();
    }

    private PieEntry setCategoryA(Map.Entry<MyCategory, Float> entry) {
        expenseCategoryA.setText(entry.getKey().getTitle());
        expenseCategoryA.setTextColor(Color.parseColor(entry.getKey().getColor()));
        expenseCategoryAValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + entry.getValue());
        expenseCategoryAWrapper.setVisibility(View.VISIBLE);
        return new PieEntry(entry.getValue());
    }

    private PieEntry setCategoryB(Map.Entry<MyCategory, Float> entry) {
        expenseCategoryB.setText(entry.getKey().getTitle());
        expenseCategoryB.setTextColor(Color.parseColor(entry.getKey().getColor()));
        expenseCategoryBValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + entry.getValue());
        expenseCategoryBWrapper.setVisibility(View.VISIBLE);
        return new PieEntry(entry.getValue());
    }

    private PieEntry setCategoryC(Map.Entry<MyCategory, Float> entry) {
        expenseCategoryC.setText(entry.getKey().getTitle());
        expenseCategoryC.setTextColor(Color.parseColor(entry.getKey().getColor()));
        expenseCategoryCValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + entry.getValue());
        expenseCategoryCWrapper.setVisibility(View.VISIBLE);
        return new PieEntry(entry.getValue());
    }

    private PieEntry setOthersCategories(float accum) {
        expenseOtherValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + accum);
        expenseOthersWrapper.setVisibility(View.VISIBLE);
        return new PieEntry(accum);
    }

    private void showOverviewCard(){
        //Card de Global Overview
        float totalIncomes = controller.getIncomeTotal();
        float totalExpenses = controller.getExpensesTotal();

        incomeTotal.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + totalIncomes);
        expensesTotal.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + totalExpenses);
    }

    private void showExpensesByCategoryCard(){
        String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
        int month = Calendar.getInstance().get(Calendar.MONTH);

        //Card de Expenses By Category
        List<MyTransaction> expensesTransactions = controller.getExpenses();

        Map<MyCategory, Float> categoryMap = new HashMap<>();
        MyCategory tmp;
        for (MyTransaction transaction : expensesTransactions) {
            tmp = transaction.getMyCategory();
            if(categoryMap.containsKey(tmp)){
                categoryMap.put(tmp, categoryMap.get(tmp) + transaction.getValue());
            }else{
                categoryMap.put(tmp, transaction.getValue());
            }
        }

        List<Map.Entry<MyCategory, Float>> list = new ArrayList<>(categoryMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<MyCategory, Float>>() {
            @Override
            public int compare(Map.Entry<MyCategory, Float> e1, Map.Entry<MyCategory, Float> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });



        List<PieEntry> pieEntries = new ArrayList<>();
        List<Integer> pieColors = new ArrayList<>();
        try {
            pieEntries.add(setCategoryA(list.get(0)));
            pieColors.add(Color.parseColor(list.get(0).getKey().getColor()));
        }catch (Exception _e){
            overviewCategoriesCard.setVisibility(View.GONE);
        }

        try {
            pieEntries.add(setCategoryB(list.get(1)));
            pieColors.add(Color.parseColor(list.get(1).getKey().getColor()));
        }catch (Exception _e){
            expenseCategoryBWrapper.setVisibility(View.GONE);
        }

        try{
            pieEntries.add(setCategoryC(list.get(2)));
            pieColors.add(Color.parseColor(list.get(2).getKey().getColor()));
        }catch (Exception _e){
            expenseCategoryCWrapper.setVisibility(View.GONE);
        }

        try{
            float accum = 0f;
            for (Map.Entry<MyCategory, Float> entry: list.subList(3, list.size())) {
                accum += entry.getValue();
            }
            pieEntries.add(setOthersCategories(accum));
            pieColors.add(Color.DKGRAY);
        }catch (Exception _e){
            expenseOthersWrapper.setVisibility(View.GONE);
        }

        //Agrega los datos al grafico
        PieDataSet pieSet = new PieDataSet(pieEntries, "");
        pieSet.setColors(pieColors);
        pieSet.setSliceSpace(4);
        pieSet.setDrawValues(false);
        PieData pieData = new PieData(pieSet);
        overviewCategoriesCardPieChart.setData(pieData);
        overviewCategoriesCardPieChart.setDescription(null);    // Hide the description
        overviewCategoriesCardPieChart.getLegend().setEnabled(false);   // Hide the legend
        overviewCategoriesCardPieChart.invalidate(); // refresh

    }

    private void showBalanceTimelineCard(){
        String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int actualDay = c.get(Calendar.DATE);
        //Month Budge
        List<ILineDataSet> dataSets = new ArrayList<>();


        int numberOfDays = actualDay;
        float[] incomes = new float[numberOfDays];
        float[] expenses = new float[numberOfDays];
        float[] budge = new float[numberOfDays];

        // Monthly Budget
        float budget = controller.getMaxBudgetInMonth(month);
        if(budget > 0){
            Arrays.fill(budge, budget);
            List<Entry> budgetEntries = new ArrayList<>();
            for (int i = 0; i < budge.length; i++) {
                budgetEntries.add(new Entry(i+1, budge[i]));
            }
            LineDataSet budgetDataSet = new LineDataSet(budgetEntries, "Max Month Budget");
            budgetDataSet.setDrawCircles(false);
            budgetDataSet.setColor(Color.MAGENTA);
            budgetDataSet.setDrawValues(false);
            dataSets.add(budgetDataSet);
        }

        //Total Expense per day
        List<MyTransaction> monthExpenses = controller.getExpensesInMonth(month);
        for (MyTransaction transaction:monthExpenses) {
            c.setTime(transaction.getTimeStamp());
            int day = c.get(Calendar.DAY_OF_MONTH);
            if(day <= actualDay){
                expenses[day-1] += transaction.getValue();
            }
        }

        //Total Income per day
        List<MyTransaction> monthIncomes = controller.getIncomesInMonth(month);
        for (MyTransaction transaction:monthIncomes) {
            c.setTime(transaction.getTimeStamp());
            int day = c.get(Calendar.DAY_OF_MONTH);
            if(day <= actualDay){
                incomes[day-1] += transaction.getValue();
            }
        }

        List<Entry> balanceEntries = new ArrayList<>();
        float previus;
        for (int i = 0; i < incomes.length; i++) {
            if(i == 0){
                previus = controller.getBalance(dateAtLastMonth());
            }else{
                previus = balanceEntries.get(i-1).getY();
            }
            balanceEntries.add(new Entry(i+1, previus + incomes[i] - expenses[i]));
        }

        LineDataSet incomeDataSet = new LineDataSet(balanceEntries, "Daily Balance");
        incomeDataSet.setDrawValues(false);
        incomeDataSet.setDrawCircles(false);
        incomeDataSet.setColor(Color.GREEN);

        dataSets.add(incomeDataSet);

        LineData lineData = new LineData(dataSets);
        overviewMonthlyBudgeLineChart.setData(lineData);

        XAxis xAxisLine = overviewMonthlyBudgeLineChart.getXAxis();
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);

        overviewMonthlyBudgeLineChart.setAutoScaleMinMaxEnabled(true);

        overviewMonthlyBudgeLineChart.getAxisRight().setEnabled(false);
        overviewMonthlyBudgeLineChart.setDescription(null);
        overviewMonthlyBudgeLineChart.invalidate(); // refresh
    }

    private void showMonthlyBudgetCard(){
        String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
        int month = Calendar.getInstance().get(Calendar.MONTH);
        float value = controller.getMaxBudgetInMonth(month);
        float spent = controller.getExpensesTotalInMonth(month);
        float remaning = value - spent;
        budgetValue.setText( symbol + value );
        budgetSpent.setText( symbol + spent );
        budgetRemaining.setText("Remaning " + symbol + remaning);
        budgetMonthlyProgress.setMax(100);
        budgetMonthlyProgress.setProgress((int) (spent * 100 / value));
    }

    private void showMonthlyBalanceCard(){
        String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
        int month = Calendar.getInstance().get(Calendar.MONTH);

        //Card de Monthly Balance
        float monthTotalIncomes = controller.getIncomeTotalInMonth(month);
        float monthTotalExpenses = controller.getExpensesTotalInMonth(month);

        incomeValue.setText(symbol + monthTotalIncomes);
        expensesValue.setText(symbol + monthTotalExpenses);
        balanceTotal.setText(symbol + (monthTotalIncomes - monthTotalExpenses));

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, monthTotalIncomes));
        entries.add(new BarEntry(1f, monthTotalExpenses));
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        set.setColors(Color.GREEN, Color.RED);
        set.setDrawValues(false);
        BarData data = new BarData(set);

        // data has AxisDependency.LEFT
        overviewMonthlyBalanceCardBarChart.setData(data);

        XAxis xAxis = overviewMonthlyBalanceCardBarChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        overviewMonthlyBalanceCardBarChart.getAxisLeft().setEnabled(false);
        overviewMonthlyBalanceCardBarChart.getAxisRight().setEnabled(false);

        overviewMonthlyBalanceCardBarChart.setDescription(null);    // Hide the description
        overviewMonthlyBalanceCardBarChart.getLegend().setEnabled(false);   // Hide the legend

        overviewMonthlyBalanceCardBarChart.getAxisLeft().setAxisMaximum(Math.max(monthTotalIncomes, monthTotalExpenses));
        overviewMonthlyBalanceCardBarChart.getAxisLeft().setAxisMinimum(0f);

        overviewMonthlyBalanceCardBarChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.overview_balance_card)
    public void onOverviewBalanceCardClicked() {
    }

    @OnClick(R.id.overview_categories_card)
    public void onOverviewCategoriesCardClicked() {
    }

    @OnClick(R.id.overview_monthly_budget)
    public void onOverviewMonthlyBudgetClicked() {
    }

    @OnClick(R.id.overview_monthly_budge)
    public void onOvervoewLastWeerkClicked() {
    }

    @OnClick(R.id.overview_monthly_balance)
    public void onOverviewMonthlyBalanceClicked() {
    }

    private Date dateAtLastMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTime().getTime() - ((calendar.get(Calendar.DAY_OF_MONTH)-1)*24*60*60*1000 + (calendar.get(Calendar.HOUR_OF_DAY)+1)*60*60*1000 - 12*60*60*1000 ));
        return calendar.getTime();
    }
}
