package org.upv.myfinances.jobs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import org.upv.myfinances.controller.Controller;
import org.upv.myfinances.model.MyTransaction;
import org.upv.myfinances.utils.Constants;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class TransactionJob extends Job {
    public static final String TAG = "TransactionJob";
    public Controller controller;

    public TransactionJob() {
        controller = Controller.getInstance();
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        long id = params.getExtras().getLong(Constants.PARAM_TRANSACTION_ID, -1);
        if(id > 0) {
            MyTransaction transaction = controller.getTransactionById(id);
            Calendar calendar = Calendar.getInstance();
            if(!transaction.isForever() && calendar.getTime().getTime() >= transaction.getEndDate().getTime()) {
                int jobId = getContext().getSharedPreferences("JOBS_IDS", Context.MODE_PRIVATE).getInt(transaction.getId()+"",-1);
                cancelJob(jobId);
                return Result.SUCCESS;
            }

            if(controller.addNewTransaction(new MyTransaction(transaction))) {
                return Result.SUCCESS;
            }
        }
        return Result.FAILURE;
    }

    @Override
    protected void onReschedule(int newJobId) {

    }

    public void schedulePeriodicJob(Context context, long millis, MyTransaction transaction) {
        PersistableBundleCompat bundle = new PersistableBundleCompat();
        bundle.putLong(Constants.PARAM_TRANSACTION_ID, transaction.getId());
        int jobId = schedulePeriodicJob(millis, bundle);
        context
                .getSharedPreferences("JOBS_IDS", Context.MODE_PRIVATE)
                .edit()
                .putInt(transaction.getId()+"", jobId)
                .apply();

    }

    private int schedulePeriodicJob(long millis, PersistableBundleCompat bundle) {
        return new JobRequest.Builder(TAG)
                .setPeriodic(millis, TimeUnit.MINUTES.toMillis(5))
                .addExtras(bundle)
                .build()
                .schedule();
    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }

}