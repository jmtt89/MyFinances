package org.upv.myfinances.jobs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyBudget;
import org.upv.myfinances.model.MyTransaction;
import org.upv.myfinances.utils.Constants;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class BudgetJob extends Job {
    public static final String TAG = "BudgetJob";
    private Controller controller;

    public BudgetJob() {
        controller = Controller.getInstance();
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        long id = params.getExtras().getLong(Constants.PARAM_BUDGET_ID, -1);
        if(id > 0) {
            MyBudget budget = controller.getBudgetById(id);
            if(controller.addNewBudget(new MyBudget(budget))) {
                return Result.SUCCESS;
            }
        }
        return Result.FAILURE;
    }

    public void schedulePeriodicJob(long millis, MyBudget budget) {
        PersistableBundleCompat bundle = new PersistableBundleCompat();
        bundle.putLong(Constants.PARAM_BUDGET_ID, budget.getId());
        int jobId = schedulePeriodicJob(millis, bundle);
        getContext()
                .getSharedPreferences("JOBS_IDS", Context.MODE_PRIVATE)
                .edit()
                .putInt(budget.getId()+"", jobId)
                .apply();
    }

    private int schedulePeriodicJob(long millis, PersistableBundleCompat bundle) {
        return new JobRequest.Builder(TAG)
                .setPeriodic(millis, TimeUnit.MINUTES.toMillis(5))
                .addExtras(bundle)
                .build()
                .schedule();
    }

    @Override
    protected void onReschedule(int newJobId) {

    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }
}