package com.example.kucing.salim;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by node06 on 15/02/2016.
 */
public class FiveTime extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String idSolat = intent.getStringExtra("idSolat");
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Sudah "+idSolat)
//                .setContentText("Solat Sekarang?")
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        Intent resultIntent = new Intent(context,TowerBuilder.class);
        resultIntent.putExtra("Solat",idSolat);
        resultIntent.setClassName("com.example.kucing.salim","com.example.kucing.salim.TowerBuilder");
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(resultIntent);
//        PendingIntent pentent = PendingIntent.getActivity(context, 9, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pentent);

//        NotificationManager nomager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        mBuilder.setAutoCancel(true);
//        nomager.notify(9,mBuilder.build());
        Log.d("warong", "alarm terpicu");
    }
}
