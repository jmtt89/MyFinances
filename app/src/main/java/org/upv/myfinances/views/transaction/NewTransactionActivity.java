package org.upv.myfinances.views.transaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.jobs.TransactionJob;
import org.upv.myfinances.model.MyBudget;
import org.upv.myfinances.model.MyCategory;
import org.upv.myfinances.model.DateRange;
import org.upv.myfinances.model.MyTransaction;
import org.upv.myfinances.views.ManagerNotification;
import org.upv.myfinances.views.categories.ListCategoryDialog;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_ID;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_IS_INCOME;
import static org.upv.myfinances.utils.Constants.PARAM_TRANSACTION_IS_INCOME;

public class NewTransactionActivity extends AppCompatActivity {
    private static final int REQUEST_CHANGE_CATEGORY = 99;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.value)
    TextInputEditText value;
    @BindView(R.id.currency)
    TextView currency;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.description)
    TextInputEditText description;
    @BindView(R.id.periodicity)
    AppCompatCheckBox periodicity;
    @BindView(R.id.quantityDateRange)
    EditText quantityDateRange;
    @BindView(R.id.dateRange)
    Spinner dateRange;
    @BindView(R.id.duration)
    AppCompatCheckBox duration;
    @BindView(R.id.endDate)
    TextView endDate;

    @BindView(R.id.reminderable)
    AppCompatCheckBox reminderable;

    @BindView(R.id.save)
    Button save;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.endDate_wrapper)
    TextInputLayout endDateWrapper;
    @BindView(R.id.periodicity_form)
    LinearLayout periodicityForm;

    Controller controller;
    MyTransaction transaction;

    public NewTransactionActivity() {
        controller = Controller.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trasaction);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean isIncome = false;
        if(getIntent() != null && getIntent().getExtras() != null) {
            isIncome = getIntent().getExtras().getBoolean(PARAM_TRANSACTION_IS_INCOME, false);
        }

        if (isIncome) {
            transaction = MyTransaction.newIncome();
            value.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_black_24dp, 0, 0, 0);
            for (Drawable drawable : category.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));
                }
            }
            value.setTextColor(Color.GREEN);
        } else {
            transaction = MyTransaction.newExpense();
            value.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove_24dp, 0, 0, 0);
            for (Drawable drawable : category.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
                }
            }
            value.setTextColor(Color.RED);
        }

        //Set Default timestamp
        Calendar calendar = Calendar.getInstance();
        date.setText(format(calendar));
        transaction.setTimeStamp(calendar.getTime());

        //Set default MyCategory
        MyCategory defaultMyCategory;
        if(isIncome){
            defaultMyCategory = controller.getDefaultIncomeCategory();
        }else{
            defaultMyCategory = controller.getDefaultExpenseCategory();
        }
        category.setText(defaultMyCategory.getTitle());
        category.setCompoundDrawablesWithIntrinsicBounds(defaultMyCategory.getIcon(), 0, R.drawable.ic_arrow_drop_down_24dp, 0);
        for (Drawable drawable : category.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(defaultMyCategory.getColor()), PorterDuff.Mode.SRC_IN));
            }
        }
        transaction.setMyCategory(defaultMyCategory);

        // Set default Currency
        Currency defaultCurrency = Currency.getInstance(Locale.getDefault());
        currency.setText(defaultCurrency.getCurrencyCode());
        transaction.setCurrency(defaultCurrency);
    }

    @OnClick(R.id.currency)
    public void onCurrencyClicked() {
        final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                currency.setText(code);
                transaction.setCurrency(Currency.getInstance(code));
                picker.dismiss();
            }
        });
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
    }

    @OnClick(R.id.category)
    public void onCategoryClicked() {
        Intent intent = new Intent(getApplicationContext(), ListCategoryDialog.class);
        intent.putExtra(PARAM_CATEGORY_IS_INCOME, transaction.isIncome());
        startActivityForResult(intent, REQUEST_CHANGE_CATEGORY);
    }

    @OnClick(R.id.date)
    public void onDateClicked(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, monthOfYear, dayOfMonth);
                date.setText(format(calendar));
                transaction.setTimeStamp(calendar.getTime());
            }
        };

        DatePickerDialog mdiDialog = new DatePickerDialog(this, listener, year, month, day);
        mdiDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
        mdiDialog.show();

    }

    @OnClick(R.id.endDate)
    public void onEndDateClicked(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, monthOfYear, dayOfMonth);
                endDate.setText(format(calendar));
                transaction.setEndDate(calendar.getTime());
            }
        };

        DatePickerDialog mdiDialog = new DatePickerDialog(this, listener, year, month, day);
        mdiDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mdiDialog.show();

    }

    @OnClick({R.id.save, R.id.fab})
    public void onSaveClicked() {
        if(transaction.isPeriodic()){
            switch (dateRange.getSelectedItemPosition()){
                case 2:
                    transaction.setRange(DateRange.Monthly);
                    break;
                case 1:
                    transaction.setRange(DateRange.Weekly);
                    break;
                case 0:
                default:
                    transaction.setRange(DateRange.Daily);
                    break;
            }
            transaction.setQuantity(Integer.parseInt(quantityDateRange.getText().toString()));
            transaction.setRemindMe(reminderable.isChecked());
        }
        transaction.setValue(Float.parseFloat(value.getText().toString()));
        transaction.setDescription(description.getText().toString());
        if(controller.addNewTransaction(transaction)){
            int month = Calendar.getInstance().get(Calendar.MONTH);
            //Checkeamos los budget
            for (MyBudget budget: controller.getAllBudget()) {
                if(budget.isGlobal() && budget.getAmount() <= controller.getBalance(month)){
                    ManagerNotification.notify(getApplicationContext(), budget);
                }else if(!budget.isGlobal() &&  budget.getAmount() <= controller.getBalance(budget.getCategory(), month)){
                    ManagerNotification.notify(getApplicationContext(), budget);
                }
            }

            //Agregamos el job persistente
            //TODO: Mejorar el componente mensual (Que es el mas utilizado seguro) para que sea correctamente el mismo dia de cada mes (o el mas cercano)
            if(transaction.isPeriodic()){
                long millis = Long.MAX_VALUE;
                switch (transaction.getRange()){
                    case Daily:
                        millis = transaction.getQuantity() * 24 * 60 * 60 * 1000L;
                        break;
                    case Weekly:
                        millis = transaction.getQuantity()* 7 * 24 * 60 * 60 * 1000L;
                        break;
                    case Monthly:
                        millis = transaction.getQuantity() * 31 * 24 * 60 * 60 * 1000L;
                        break;
                }
                new TransactionJob().schedulePeriodicJob(getApplicationContext(), millis, transaction);
            }
            finish();
        }
    }

    @OnCheckedChanged(R.id.periodicity)
    public void isPeriodic(CompoundButton button, boolean checked) {
        transaction.setPeriodic(checked);
        if (checked) {
            periodicityForm.setVisibility(View.VISIBLE);
        } else {
            periodicityForm.setVisibility(View.GONE);
        }
    }

    @OnCheckedChanged(R.id.duration)
    public void isForever(CompoundButton button, boolean checked) {
        transaction.setForever(checked);
        if (checked) {
            endDateWrapper.setVisibility(View.GONE);
        } else {
            endDateWrapper.setVisibility(View.VISIBLE);
        }
    }

    private String format(Calendar calendar){
        Date date = calendar.getTime();
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        return dateFormat.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CHANGE_CATEGORY && resultCode == RESULT_OK){
            long id = data.getExtras().getLong(PARAM_CATEGORY_ID);
            MyCategory nCategory = controller.getCategoryById(id);
            category.setText(nCategory.getTitle());
            category.setCompoundDrawablesWithIntrinsicBounds(nCategory.getIcon(), 0, R.drawable.ic_arrow_drop_down_24dp, 0);
            for (Drawable drawable : category.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(nCategory.getColor()), PorterDuff.Mode.SRC_IN));
                }
            }
            transaction.setMyCategory(nCategory);
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
