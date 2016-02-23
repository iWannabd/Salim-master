package com.example.kucing.salim;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by node06 on 22/02/2016.
 */
public class StatsHandler {
    String json_string;
    JSONObject dates = new JSONObject();
    JSONArray solats = new JSONArray();
    SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
    String today = dateFormat.format(new Date());
    SharedPreferences shaper;
    Context con;
    String TAG = "apa?";
    public StatsHandler(Context context) throws JSONException {
        con = context;
        shaper = con.getSharedPreferences("UserStats",Context.MODE_PRIVATE);
        json_string = shaper.getString("UserStat", "");
        Log.d(TAG, "StatsHandler: "+json_string);
        Log.d(TAG, "StatsHandler: "+today);
        if (!json_string.equals("")){
            dates = new JSONObject(json_string);
            solats = dates.getJSONArray(today);
        }
    }

    public void putSolat(String sol){
        solats.put(sol);
        try {
            dates.put(today,solats);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json_string = dates.toString();
        SharedPreferences.Editor editor = shaper.edit();
        editor.putString("UserStat",json_string);
        editor.apply();
    }

    public String cek(){
        return shaper.getString("UserStat", "ternyata kosong");
    }
}
