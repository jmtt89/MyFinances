package org.upv.myfinances.views.budge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.jobs.BudgetJob;
import org.upv.myfinances.jobs.TransactionJob;
import org.upv.myfinances.model.MyBudget;
import org.upv.myfinances.model.MyCategory;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class NewBudgetActivity extends AppCompatActivity {

    @BindView(R.id.value)
    EditText value;
    @BindView(R.id.is_global)
    AppCompatCheckBox isGlobal;
    @BindView(R.id.select_category)
    Spinner selectCategory;
    @BindView(R.id.receive_alerts)
    AppCompatCheckBox receiveAlerts;
    @BindView(R.id.set_periodic)
    AppCompatCheckBox setPeriodic;
    @BindView(R.id.cancel_action)
    Button cancelAction;
    @BindView(R.id.save_action)
    Button saveAction;

    Controller controller;
    MyBudget budget;

    public NewBudgetActivity() {
        controller = Controller.getInstance();
        budget = new MyBudget();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_budget);
        ButterKnife.bind(this);

        List<MyCategory> categories = controller.getExpensesCategories();
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, R.layout.spinner_category_item, categories);
        selectCategory.setAdapter(categoryAdapter);
    }

    @OnCheckedChanged(R.id.is_global)
    public void onIsGlobalClicked(CompoundButton button, boolean checked) {
        selectCategory.setVisibility(checked ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.cancel_action)
    public void onCancelActionClicked() {
        finish();
    }

    @OnClick(R.id.save_action)
    public void onSaveActionClicked() {
        budget.setAmount(Float.parseFloat(value.getText().toString()));
        budget.setNotifyMe(receiveAlerts.isChecked());
        budget.setPeriodic(setPeriodic.isChecked());
        budget.setTimeStamp(Calendar.getInstance().getTime());
        if(isGlobal.isChecked()){
            budget.setGlobal(true);
            budget.setCategory((MyCategory) selectCategory.getSelectedItem());
        } else {
            budget.setGlobal(false);
        }
        if(controller.addNewBudget(budget)){
            //Agregamos el job persistente
            //TODO: Mejorar el componente mensual (Que es el mas utilizado seguro) para que sea correctamente el mismo dia de cada mes (o el mas cercano)
            if(budget.isPeriodic()){
                long millis = 32L * 24 * 60 * 60 * 1000;
                new BudgetJob().schedulePeriodicJob(millis, budget);
            }
            finish();
        }
    }

}
