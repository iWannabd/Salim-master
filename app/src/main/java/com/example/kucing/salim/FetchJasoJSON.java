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

    public FetchJasoJSON(Jadwal j){
        this.container = j;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params){
        StringBuilder result = new StringBuilder();
        Log.d("SALAH", "doInBackground: " + container.bacaJason());
        if (Objects.equals(container.bacaJason(), "Empty")) {
            try {
                Log.d("SALAH","di parsing looh");
                SimpleDateFormat ye = new SimpleDateFormat("yyyy");
                SimpleDateFormat me = new SimpleDateFormat("M");
                String urel = "http://api.xhanch.com/islamic-get-prayer-time.php?lng=107.6098111&lat=-6.9147444&yy=" + ye.format(new Date()) + "&mm=" + me.format(new Date()) + "&gmt=7&m=json";
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



    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String result){
        if(container!=null && container.getActivity()!=null){
            if (Objects.equals(container.bacaJason(), "Empty")){
                container.simpanJason(result);
                Log.d("SALAH", "onPostExecute: data terupdate "+result);
            }

            try {
                container.setListData(container.bacaJason());
                Log.d("SALAH","data data yang ada");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.container = null;
    }
}
