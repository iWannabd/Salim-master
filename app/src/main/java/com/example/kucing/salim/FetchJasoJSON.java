package com.example.kucing.salim;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

import layout.Jadwal;

/**
 * Created by node06 on 26/01/2016.
 */
public class FetchJasoJSON extends AsyncTask<String, Void, String> {
    Jadwal container;
    HttpURLConnection urlcon;
    SimpleDateFormat ye = new SimpleDateFormat("yyyy");
    SimpleDateFormat me = new SimpleDateFormat("M");

    public FetchJasoJSON(Jadwal j){
        this.container = j;
    }

    @Override
    protected String doInBackground(String... params){
        //create the string buffer
        StringBuilder result = new StringBuilder();
        if ("Empty".equals(container.bacaJason())) {
            try {
                // getting Jaso JSON from url
                // membuat string url berdasarkan bulan dan tahun
                // lalu dibuatlah koneski untuk menerima String Json
                String urel = "http://api.xhanch.com/islamic-get-prayer-time.php?lng=107.6098111&lat=-6.9147444&yy=" +
                        ye.format(new Date()) + "&mm=" +
                        me.format(new Date()) + "&gmt=7&m=json";
                URL url = new URL(urel);
                urlcon = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlcon.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlcon.disconnect();
            }
        }
        return result.toString();
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onPostExecute(String result){
        if(container!=null && container.getActivity()!=null){
            //dicek jika sudah ada json string atau sudah aoutdate maka
            //akan disimpan
            if ("Empty".equals(container.bacaJason())){
                container.simpanJason(result);
            }
            try {
                container.setListData(container.bacaJason());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.container = null;
    }
}
