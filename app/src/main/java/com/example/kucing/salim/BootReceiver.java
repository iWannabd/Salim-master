package com.example.kucing.salim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import layout.Jadwal;

/**
 * Created by node06 on 14/02/2016.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"));
        Utama.setFiveTimes(context);
        Log.d("warong", "onReceive: abis diboot terus diset alarmnya");
    }
}
