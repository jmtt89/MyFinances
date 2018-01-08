package org.upv.myfinances.views.categories;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyCategory;

import java.util.ArrayList;
import java.util.List;

import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_ID;
import static org.upv.myfinances.utils.Constants.PARAM_CATEGORY_IS_INCOME;

public class ListCategoryActivity extends AppCompatActivity{
    private static final int REQUEST_ADD_NEW_CATEGORY = 55;
    private static final int REQUEST_EDIT_CATEGORY = 65;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
                intent.putExtra(PARAM_CATEGORY_IS_INCOME, mViewPager.getCurrentItem()==1);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_ADD_NEW_CATEGORY);
            }
        });

    }

    public static class PlaceholderFragment extends Fragment implements CategoryListAdapter.Listeners{
        private static final String ARG_SECTION_NUMBER = "section";

        Controller controller;
        int sectionNumber;
        CategoryListAdapter adapter;

        public PlaceholderFragment() {
            controller = Controller.getInstance();
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_category, container, false);
            Context context = rootView.getContext();

            if (getArguments() != null) {
                sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            }

            RecyclerView list = rootView.findViewById(R.id.category_list);
            list.setLayoutManager(new LinearLayoutManager(context));

            adapter = new CategoryListAdapter(this);
            list.setAdapter(adapter);

            refresh();

            return rootView;
        }

        @Override
        public void onClick(MyCategory category) {
            Intent intent = new Intent(getContext(), EditCategoryActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(PARAM_CATEGORY_ID, category.getId());
            startActivityForResult(intent, REQUEST_EDIT_CATEGORY);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode){
                case REQUEST_ADD_NEW_CATEGORY:
                    break;

                case REQUEST_EDIT_CATEGORY:
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }

        List<MyCategory> categoryList = new ArrayList<>();
        public void refresh() {
            switch (sectionNumber){
                case 1:
                    categoryList = controller.getIncomesCategories();
                    break;
                case 0:
                default:
                    categoryList = controller.getExpensesCategories();
                    break;
            }
            adapter.clear();
            adapter.add(categoryList);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private PlaceholderFragment[] fragments = new PlaceholderFragment[]{
            PlaceholderFragment.newInstance(0),
            PlaceholderFragment.newInstance(1)
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        public void refresh(int position){
            fragments[position].refresh();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ADD_NEW_CATEGORY:
            case REQUEST_EDIT_CATEGORY:
                mSectionsPagerAdapter.refresh(mViewPager.getCurrentItem());
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }
}
