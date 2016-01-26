package com.example.kucing.salim;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;

import layout.Jadwal;

/**
 * Created by node06 on 26/01/2016.
 */
public class FetchJasoJSON extends AsyncTask<String, Void, String> {
    Jadwal container;
    JSONObject date,jaso;
    HttpURLConnection urlcon;

    public FetchJasoJSON(Jadwal j){
        this.container = j;
    }

    @Override
    protected String doInBackground(String... params){
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL("http://api.xhanch.com/islamic-get-prayer-time.php?lng=107.6098111&lat=-6.9147444&yy=2016&mm=1&gmt=7&m=json");
            urlcon = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlcon.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
            date = new JSONObject(result.toString());
            SimpleDateFormat df = new SimpleDateFormat("dd");
            jaso = date.getJSONObject(df.format(new Date()));

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            urlcon.disconnect();
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
        try {
            container.setListData(jaso);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    this.container = null;
    }
}
