package org.upv.myfinances.views.categories;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyCategory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_COLOR;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_ICON;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_ID;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_IS_INCOME;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_TITLE;

public class NewCategoryActivity extends AppCompatActivity {
    Controller controller;
    boolean isIncome = false;

    @BindView(R.id.title_category)
    EditText title;
    @BindView(R.id.color_preview)
    CardView colorPreview;
    @BindView(R.id.caret_more)
    ImageView caretMore;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.accept)
    Button accept;

    public NewCategoryActivity() {
        this.controller = Controller.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            isIncome = getIntent().getExtras().getBoolean(PARAM_CATEGORY_IS_INCOME, false);
        }
    }

    @OnClick({R.id.color_preview, R.id.caret_more})
    public void onSelectColor(){
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                colorPreview.setCardBackgroundColor(color);
            }

            @Override
            public void onCancel(){
                // put code
            }
        });
    }

    @OnClick(R.id.cancel)
    public void onCancelClicked() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.accept)
    public void onAcceptClicked() {
        Intent data = new Intent();
        String cColor = String.format("#%06X", (0xFFFFFF & colorPreview.getCardBackgroundColor().getDefaultColor()));
        String cTitle = title.getText().toString();
        MyCategory category = new MyCategory(isIncome, cTitle, cColor);
        controller.addNewCategory(category);
        data.putExtra(PARAM_CATEGORY_ID, category.getId());
        data.putExtra(PARAM_CATEGORY_TITLE, category.getTitle());
        data.putExtra(PARAM_CATEGORY_COLOR, category.getColor());
        data.putExtra(PARAM_CATEGORY_ICON, category.getIcon());
        data.putExtra(PARAM_CATEGORY_IS_INCOME, category.isIncome());
        setResult(RESULT_OK, data);
        finish();
    }
}
