package org.upv.myfinances.receivers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import org.upv.myfinances.jobs.BudgetJob;
import org.upv.myfinances.jobs.TransactionJob;


public class MyJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case TransactionJob.TAG:
                return new TransactionJob();
            case BudgetJob.TAG:
                return new BudgetJob();
            default:
                return null;
        }
    }
}