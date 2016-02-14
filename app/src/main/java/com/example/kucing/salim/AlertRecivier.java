package com.example.kucing.salim;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import layout.Jadwal;

/**
 * Created by node06 on 09/02/2016.
 */
public class AlertRecivier extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        createNotif(context,"Udah Dzuhr");

    }

    public void createNotif(Context ctx,String msg){
        SharedPreferences sapee = ctx.getSharedPreferences("Jaso", Context.MODE_PRIVATE);
        PendingIntent notifiIntent = PendingIntent.getActivity(ctx, 0, new Intent(ctx, TowerBuilder.class), 0);
        NotificationCompat.Builder pembangun = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msg)
                .setContentText(sapee.getString("2/2016","Empty"));

        pembangun.setContentIntent(notifiIntent);
        pembangun.setDefaults(NotificationCompat.DEFAULT_SOUND);
        pembangun.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,pembangun.build());
    }
}


