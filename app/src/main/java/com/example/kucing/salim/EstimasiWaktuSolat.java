package com.example.kucing.salim;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.nio.channels.SelectionKey;
import java.sql.Time;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by node06 on 19/02/2016.
 */
public class EstimasiWaktuSolat extends DialogPreference implements SeekBar.OnSeekBarChangeListener {

    /** Default minute */
    private static final int DEFAULT_MINUTE = 8;

    public EstimasiWaktuSolat(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    SeekBar sb;
    TextView currvalue;
    @Override
    public void onBindDialogView(View view){
        super.onBindDialogView(view);
        sb = (SeekBar) view.findViewById(R.id.seekBar);
        currvalue = (TextView) view.findViewById(R.id.minutesetted);
        sb.setProgress(getSharedPreferences().getInt(getKey(),0));
        currvalue.setText(getSharedPreferences().getInt(getKey(),0)+" menit");
        sb.setMax(30);
        sb.setOnSeekBarChangeListener(this);
        Log.d("halo", "onBindDialogView: ");
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currvalue.setText(progress +" menit");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(getKey(), seekBar.getProgress());
        editor.commit();
    }
}
