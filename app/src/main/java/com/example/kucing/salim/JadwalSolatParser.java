package com.example.kucing.salim;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by node06 on 12/02/2016.
 */
public class JadwalSolatParser {


//    mendapatkan jadwalshoaltadapeter dari inputan tanggal
    public static jadwalSholatAdapter getAdapter(ArrayList<String> jaso,Resources res,Activity act){
        ArrayList<ItemJadwalSholat> harian = new ArrayList<>();
        ItemJadwalSholat temp = new ItemJadwalSholat();
        //mendapatkan setiap item solat
        //subuh
        temp.setSolatna("Subuh");
        temp.setWaktuna(jaso.get(0));
        temp.setImage(R.drawable.night);
        harian.add(temp);temp = new ItemJadwalSholat();
        //dzuhur
        temp.setSolatna("Dzuhr");
        temp.setWaktuna(jaso.get(1));
        temp.setImage(R.drawable.day);
        harian.add(temp);temp = new ItemJadwalSholat();
        //asr
        temp.setSolatna("Ashar");
        temp.setWaktuna(jaso.get(2));
        temp.setImage(R.drawable.day);
        harian.add(temp);temp = new ItemJadwalSholat();
        //maghrib
        temp.setSolatna("Maghrib");
        temp.setWaktuna(jaso.get(3));
        temp.setImage(R.drawable.night);
        harian.add(temp);temp = new ItemJadwalSholat();
        //isha
        temp.setSolatna("Isya");
        temp.setWaktuna(jaso.get(4));
        temp.setImage(R.drawable.night);
        harian.add(temp);
        return new jadwalSholatAdapter(act,harian,res);
    }

}
