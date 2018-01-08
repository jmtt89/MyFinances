package org.upv.myfinances.views.budge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.co.markormesher.android_fab.FloatingActionButton;

public class BudgeFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.addBudge)
    FloatingActionButton addBudge;

    Controller controller;
    BudgetListAdapter adapter;
    Unbinder unbinder;



    public BudgeFragment() {
        // Required empty public constructor
        controller = Controller.getInstance();
    }

    public static BudgeFragment newInstance() {
        BudgeFragment fragment = new BudgeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budge, container, false);
        unbinder = ButterKnife.bind(this, view);


        adapter = new BudgetListAdapter(controller);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.refreshData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addBudge)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), NewBudgetActivity.class);
        startActivity(intent);
    }
}
