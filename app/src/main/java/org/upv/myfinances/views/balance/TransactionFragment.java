package org.upv.myfinances.views.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.upv.myfinances.R;
import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyTransaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import uk.co.markormesher.android_fab.FloatingActionButton;

public class TransactionFragment extends Fragment {
    SectionedRecyclerViewAdapter sectionAdapter;

    public TransactionFragment() {
    }

    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        Context context = view.getContext();

        // Create an instance of SectionedRecyclerViewAdapter
        sectionAdapter = new SectionedRecyclerViewAdapter();

        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(sectionAdapter);

        FloatingActionButton fab = view.findViewById(R.id.addTransaction);
        fab.setSpeedDialMenuAdapter(new AddTransactionSpeedDialAdapter(context.getApplicationContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Add your Sections
        List<MyTransaction> myTransactionList = Controller.getInstance().getTransactionList();
        Collections.sort(myTransactionList, new Comparator<MyTransaction>() {
            @Override
            public int compare(MyTransaction t1, MyTransaction t2) {
                return t2.getTimeStamp().compareTo(t1.getTimeStamp());
            }
        });

        sectionAdapter.removeAllSections();
        int dayOfMonth = 0;
        List<MyTransaction> transactionsInDay = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        for (MyTransaction myTransaction : myTransactionList) {
            Calendar tmp = toCalendar(myTransaction.getTimeStamp());
            if(dayOfMonth > 0 && dayOfMonth != tmp.get(Calendar.DAY_OF_MONTH)){
                String title = format.format(transactionsInDay.get(0).getTimeStamp());
                sectionAdapter.addSection(new TimeSection(getContext().getApplicationContext(), transactionsInDay, title));
                dayOfMonth = tmp.get(Calendar.DAY_OF_MONTH);
                transactionsInDay = new ArrayList<>();
            }else if(dayOfMonth == 0){
                dayOfMonth = tmp.get(Calendar.DAY_OF_MONTH);
            }
            transactionsInDay.add(myTransaction);
        }

        if(transactionsInDay.size() > 0){
            String title = format.format(transactionsInDay.get(0).getTimeStamp());
            sectionAdapter.addSection(new TimeSection(getContext().getApplicationContext(), transactionsInDay, title));
        }
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
