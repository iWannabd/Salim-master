package com.example.kucing.salim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TowerBuilder extends AppCompatActivity {
    AlertDialog.Builder builder;
    Boolean udah;
    TextView timer,ajakan;
    Button nyerah;

//    @Bind(R.id.textView2)
//    TextView timer;
//    @Bind(R.id.circle)
//    ImageView circ;
//    @Bind(R.id.asking)
//    TextView ajakan;
//    @Bind(R.id.giveup)
//    Button nyerah;
    @Bind(R.id.down)
    TextView down;
    @Bind(R.id.asking)
    TextView asking;


    @OnClick(R.id.confirm)
    public void confirm(){
        startCounting();
    }
    @OnClick(R.id.unconfirm)
    public void unconvirm(){
        finish();
    }

    protected void startCounting(){
        setContentView(R.layout.activity_tower_builder);
        //setting the dialog
        builder = new AlertDialog.Builder(TowerBuilder.this);
        builder.setMessage("Anda yakin tidak ingin solat tepat waktu?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ImageView circ = (ImageView) findViewById(R.id.circle);
        timer = (TextView) findViewById(R.id.textView2);
        ajakan = (TextView) findViewById(R.id.asking);
        nyerah = (Button) findViewById(R.id.giveup);

        nyerah.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        //setting the timer
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
                timer.setText(remaining_minute + ":" + remaining_second);
            }

            @Override
            public void onFinish() {
                StatsHandler st = new StatsHandler(getBaseContext());
                st.putSolat(whatSolat);
                ajakan.setText("Anda telah melaksanakan salat " + whatSolat + " tepat waktu");
                timer.setText("");
                udah = true;
                nyerah.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_builder_asking);
        ButterKnife.bind(this);
        udah = false;
        new CountDownTimer(1*60*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished/1000;
                long remaining_minute = second/60;
                long remaining_second = second%60;
                down.setText(remaining_minute + ":" + remaining_second);
            }

            @Override
            public void onFinish() {
                asking.setText("Anda Terlambat :(");
            }
        }.start();

    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            // do something
            if (!udah) builder.show();
            else finish();
            return false;
        }

        if (keyCode == KeyEvent.KEYCODE_HOME){
            builder.show();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

}
