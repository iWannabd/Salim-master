package com.example.kucing.salim;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by node06 on 22/02/2016.
 */
public class StatsHandler {
    String json_string;
    JSONObject dates = new JSONObject();
    JSONArray solats = new JSONArray();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
    String today = dateFormat.format(new Date());
    SharedPreferences shaper;
    Context con;
    String TAG = "HAH";
    public StatsHandler(Context context) {
        con = context;
        shaper = con.getSharedPreferences("UserStats",Context.MODE_PRIVATE);
        json_string = shaper.getString("UserStat", "");
        Log.d(TAG, "StatsHandler: " + json_string);
        Log.d(TAG, "StatsHandler: " + today);
        if (!json_string.equals("")){
            try {
                dates = new JSONObject(json_string);
                solats = dates.getJSONArray(today);
            } catch (JSONException e) {
                solats = new JSONArray();
                e.printStackTrace();
            }
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
        editor.putString("UserStat", json_string);
        editor.apply();
    }
    //return solats that have completed by user of inputed date
    public ArrayList<String> getSolat(Date date) throws JSONException {
        ArrayList<String> result = new ArrayList<>();
        JSONArray solats_of_selected_date = new JSONArray();
        if (!json_string.equals("")){
            solats_of_selected_date = dates.getJSONArray(dateFormat.format(date));
            for (int i =0;i<solats_of_selected_date.length();i++){
                result.add((String) solats_of_selected_date.get(i));
            }
        }
        return result;
    }

    public void putAll(String json){
        SharedPreferences.Editor editor = shaper.edit();
        editor.putString("UserStat", json);
        editor.apply();
    }

    public String getAll(){
        return shaper.getString("UserStat", "ternyata kosong");
    }

    public ArrayList<Integer> getWeeklyData(Date d) {
        ArrayList<Integer> result = new ArrayList<>();
        LocalDate startdate = new LocalDate(d);
        LocalDate endate = startdate.plusDays(7);
        for (LocalDate date = startdate; date.isBefore(endate); date = date.plusDays(1)){
            int a = 0;
            try {
                a = dates.getJSONArray(fmt.print(date)).length();
            } catch (JSONException e) {
                a = 0;
                e.printStackTrace();
            }
            result.add(a);
            Log.d(TAG, "getWeeklyData: "+a);
        }
        return result;
    }

    public ArrayList<Date> getDateRange(Date d){
        ArrayList<Date> result = new ArrayList<>();
        LocalDate startdate = new LocalDate(d);
        LocalDate endate = startdate.plusDays(7);
        for (LocalDate date = startdate; date.isBefore(endate); date = date.plusDays(1)){
            result.add(date.toDate());
        }
        return result;
    }

    public ArrayList<Date> getWeekRange() throws ParseException {
        Iterator<?> keys = dates.keys();
        ArrayList<Date> dates = new ArrayList<>();
        while(keys.hasNext()) {
            String key = (String) keys.next();
            dates.add(dateFormat.parse(key));
        }
        ArrayList<Date> weeks = new ArrayList<>();
        ArrayList<Date> lotofweeks = new ArrayList<>();
        Collections.sort(dates);
        Log.d(TAG, "getWeekRange: dates "+dates);
        for(int l=0;l<dates.size();l++){
            lotofweeks.add(getEveryMonday(dates.get(l)));
        }

        if (!(lotofweeks.size()==0)) {
            int i = 0;
            weeks.add(lotofweeks.get(i));
            while (i + 1 < lotofweeks.size()) {
                if (!lotofweeks.get(i).equals(lotofweeks.get(i + 1)))
                    weeks.add(lotofweeks.get(i + 1));
                i++;
            }
        } else if (!lotofweeks.isEmpty()) {
            weeks.add(lotofweeks.get(0));
        }
        return weeks;
    }

    public Date getEveryMonday(Date now){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK,
                cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        cal.setTime(now);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week-2));
    }
}
