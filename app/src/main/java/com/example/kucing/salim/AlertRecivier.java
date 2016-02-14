package com.example.kucing.salim;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by node06 on 09/02/2016.
 */
public class AlertRecivier extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        createNotif(context,"Udah Dzuhr","solat sekarang?");

    }

    public void createNotif(Context ctx,String msg, String submsg){
        PendingIntent notifiIntent = PendingIntent.getActivity(ctx, 0, new Intent(ctx, TowerBuilder.class), 0);
        NotificationCompat.Builder pembangun = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msg)
                .setContentText(submsg);

        pembangun.setContentIntent(notifiIntent);
        pembangun.setDefaults(NotificationCompat.DEFAULT_SOUND);
        pembangun.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,pembangun.build());
    }
}
