package com.example.kucing.salim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by node06 on 09/02/2016.
 */
public class AlertRecivier extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sape = context.getSharedPreferences("Jaso", Context.MODE_PRIVATE);
        String expired_jason = new SimpleDateFormat("M/yyyy").format(new Date());
        String today_date = new SimpleDateFormat("d").format(new Date());
        try {
            String[] today_prayer_time = JadwalSolatParser.getArrayJaso(sape.getString(expired_jason,"Empty"),today_date);
            for (int i=0;i<5;i++){
                setAlarmEachPrayerTime(today_prayer_time[i],context,i);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }


    }

    public void setAlarmEachPrayerTime(String prayerTime,Context context, int n) throws ParseException {
        //parsing the input time
        Date a = new SimpleDateFormat("HH:mm").parse(prayerTime);
        Calendar cal = Calendar.getInstance();
        Calendar prayTime = Calendar.getInstance();
        prayTime.setTime(a);
        //setting calendar to input time
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, prayTime.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, prayTime.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        //setting alarm
        AlarmManager alma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent FiveTime = new Intent(context, FiveTime.class);
        PendingIntent pentent = PendingIntent.getBroadcast(context, n, FiveTime, PendingIntent.FLAG_UPDATE_CURRENT);
        alma.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pentent);
        Log.d("warong", "setAlarmEachPrayerTime: alarm at "+prayerTime+" setted");

    }
}


