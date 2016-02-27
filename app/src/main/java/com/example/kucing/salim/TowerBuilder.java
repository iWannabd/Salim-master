package com.example.kucing.salim;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TowerBuilder extends AppCompatActivity {

    @Bind(R.id.textView2)
    TextView text;
    @Bind(R.id.circle)
    ImageView circ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_builder);
        ButterKnife.bind(this);

        SharedPreferences generalPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        long EstimatedTime = generalPrefs.getInt("estimasiSolat",10)*60*1000;

        RotateAnimation rotasi = new RotateAnimation(180,0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotasi.setDuration(EstimatedTime);
        circ.startAnimation(rotasi);

        final String whatSolat = getIntent().getStringExtra("Solat");

        new CountDownTimer(EstimatedTime,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished/1000;
                long remaining_minute = second/60;
                long remaining_second = second%60;

                text.setText(remaining_minute+":"+remaining_second);
            }

            @Override
            public void onFinish() {
                StatsHandler st = new StatsHandler(getBaseContext());
                st.putSolat(whatSolat);
                text.setText("Anda telah melaksanakan salat "+whatSolat+" tepat waktu");
            }
        }.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
