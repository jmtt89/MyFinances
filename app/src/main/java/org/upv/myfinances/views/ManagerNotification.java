package org.upv.myfinances.views;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import org.upv.myfinances.R;
import org.upv.myfinances.model.MyBudget;
import org.upv.myfinances.model.MyTransaction;

import java.util.Calendar;


public class ManagerNotification {
    private static final String NOTIFICATION_TAG = "ManagerNotification";

    public static void notify(final Context context, final MyBudget budget) {
        final Resources res = context.getResources();

        final String ticker = "Alerta de Presupuesto: "+ budget.getCategory().getTitle();
        final String title = "Alerta de Presupuesto";
        final String text = "El presupuesto asignado a "+ budget.getCategory().getTitle() +"ya casi se consume este mes";

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_budget)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    public static void notify(final Context context, final MyTransaction transaction) {
        final Resources res = context.getResources();

        final String ticker = "Alerta de Transaccion: "+ transaction.getDescription();
        final String title = "Alerta de Transaccion";

        String vr = transaction.isIncome() ? "agrega": "descontar";

        final String text = "Se acaba de  "+ vr + "a su balance la cantidad de"+ transaction.getValue() +", por motivo de la transaccion periodica: "+ transaction.getDescription();
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(transaction.isIncome() ? R.drawable.ic_income_24dp : R.drawable.ic_expense_24dp)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                .setWhen(Calendar.getInstance().getTime().getTime())

                .setAutoCancel(true);

        notify(context, builder.build());
    }

    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_TAG, 0);
    }
}
