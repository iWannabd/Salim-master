package com.example.kucing.salim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashScreen extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy");
    SimpleDateFormat df = new SimpleDateFormat("d");
    String expire_jason = sdf.format(new Date());
    SharedPreferences SharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FetchJasoJSON mTask = new FetchJasoJSON(this);
        mTask.execute();
}

    public void simpanJason(String Jaso){
        //berguna untuk menyimpan json string
        SharePref = this.getSharedPreferences("Jaso", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharePref.edit();
        editor.putString(expire_jason, Jaso);
        editor.apply();
    }

    public String bacaJason(){
        SharePref = this.getSharedPreferences("Jaso", Context.MODE_PRIVATE);
        String default_jaso = getResources().getString(R.string.default_jaso);
        return SharePref.getString(expire_jason,default_jaso);
    }
}
