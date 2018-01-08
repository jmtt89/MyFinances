package org.upv.myfinances.views.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_ID;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_IS_INCOME;

public class ListCategoryDialog extends AppCompatActivity implements CategoryListAdapter.Listeners{
    private static final java.lang.String ARG_SECTION_NUMBER = "section";
    private static final int REQUEST_ADD_CATEGORY = 35;
    private static final int REQUEST_MANAGE_CATEGORY = 45;
    @BindView(R.id.search_header)
    SearchView searchHeader;

    @BindView(R.id.category_list)
    RecyclerView categoryList;
    @BindView(R.id.launch_new_category)
    LinearLayout launchNewCategory;
    @BindView(R.id.launch_manage_categories)
    LinearLayout launchManageCategories;


    private CategoryListAdapter adapter;
    private Controller controller;
    private boolean isIncome;

    public ListCategoryDialog() {
        controller = Controller.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_category);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            isIncome = getIntent().getExtras().getBoolean(PARAM_CATEGORY_IS_INCOME);
        }

        RecyclerView list = findViewById(R.id.category_list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CategoryListAdapter(this);
        list.setAdapter(adapter);

        refreshData();
    }

    @OnClick(R.id.launch_new_category)
    public void onLaunchNewCategoryClicked() {
        Intent intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PARAM_CATEGORY_IS_INCOME, isIncome);
        startActivityForResult(intent, REQUEST_ADD_CATEGORY);
    }

    @OnClick(R.id.launch_manage_categories)
    public void onLaunchManageCategoriesClicked() {
        Intent intent = new Intent(getApplicationContext(), ListCategoryActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, REQUEST_MANAGE_CATEGORY);
    }

    @Override
    public void onClick(MyCategory category) {
        Intent data = new Intent();
        data.putExtra(PARAM_CATEGORY_ID, category.getId());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ADD_CATEGORY:
                if(resultCode == RESULT_OK){
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
            case REQUEST_MANAGE_CATEGORY:
                if(resultCode == RESULT_OK){
                    refreshData();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshData() {
        List<MyCategory> categoryList;
        if(isIncome){
            categoryList = controller.getIncomesCategories();
        }else{
            categoryList = controller.getExpensesCategories();
        }
        adapter.clear();
        adapter.add(categoryList);
    }
}
