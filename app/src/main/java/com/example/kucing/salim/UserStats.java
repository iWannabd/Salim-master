package com.example.kucing.salim;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserStats extends AppCompatActivity {
    int weekrange_size,selected;
    ArrayList<BarEntry> valsol1 = new ArrayList<>();
    ArrayList<IBarDataSet> daset = new ArrayList<>();
    ArrayList<String> xva = new ArrayList<>();
    ArrayList<Date> weekrange = new ArrayList<>();
    ArrayList<Integer> week = new ArrayList<>();
    ArrayList<Date> dates_of_week = new ArrayList<>();
    ArrayList<String> solats = new ArrayList<>();
    StatsHandler statsHandler;
    String TAG = "HAH";
    DateTimeFormatter day =  DateTimeFormat.forPattern("d");
    SimpleDateFormat month_year =  new SimpleDateFormat("MMMM yyyy");
    String meessage = "";
    String range_date;
    int[] towers_images = {R.color.transparent,R.drawable.done1,R.drawable.done2,R.drawable.done3,R.drawable.done4,R.drawable.done};
    ImageView towers;

    @Bind(R.id.chart) com.github.mikephil.charting.charts.BarChart chart;
    @Bind(R.id.date)TextView date;

    @OnClick(R.id.next)
    public void next(){
        if (selected< weekrange_size -1){
            selected++;
            Log.d(TAG, "next: "+selected);
        } else {
            selected = weekrange_size -1;
        }
        setChartData();
    }

    @OnClick(R.id.prev)
    public void prev(){
        if (selected>-1){
            selected--;
            Log.d(TAG, "prev: "+selected);
            if (selected == -1) selected = 0;
        } else {
            selected = 0;
        }
        setChartData();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        ButterKnife.bind(this);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        statsHandler = new StatsHandler(getBaseContext());
        towers = (ImageView) findViewById(R.id.towers);

        try {
            weekrange = statsHandler.getWeekRange();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        weekrange_size = weekrange.size();
        Log.d(TAG, "onCreate: weekrange "+weekrange);
        if (weekrange_size >0) selected = weekrange_size -1;
        else selected = 0;
        setChartData();
    }

    public void setChartData(){
        if (!statsHandler.json_string.equals("")) {
            week = statsHandler.getWeeklyData(weekrange.get(selected));
            dates_of_week = statsHandler.getDateRange(weekrange.get(selected));
            LocalDate monday_date = new LocalDate(weekrange.get(selected));
            Log.d(TAG, "setChartData: "+selected);
            LocalDate sunday_date = monday_date.plusDays(7);
            range_date = day.print(monday_date) + "-" + day.print(sunday_date) + " " + month_year.format(new Date());
            date.setText(range_date);
            valsol1.clear();
            xva.clear();
            daset.clear();
            for (int i = 0; i < week.size(); i++) {
                valsol1.add(new BarEntry(week.get(i), i));
            }
            BarDataSet badaset = new BarDataSet(valsol1, "");
            badaset.setColor(getResources().getColor(R.color.Prime));
            daset.add(badaset);

            xva.add("Senin");
            xva.add("Selasa");
            xva.add("Rabu");
            xva.add("Kamis");
            xva.add("Jumat");
            xva.add("Sabtu");
            xva.add("Minggu");

            BarData bardat = new BarData(xva, daset);
            bardat.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            });
            chart.setData(bardat);
            chart.animateY(1000);
            chart.setPinchZoom(true);
            chart.getXAxis().setDrawGridLines(false);
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getAxisLeft().setDrawAxisLine(false);
            chart.getAxisLeft().setDrawGridLines(false);
            chart.getAxisLeft().setDrawLabels(false);
            chart.getAxisRight().setDrawAxisLine(false);
            chart.getAxisRight().setDrawGridLines(false);
            chart.getAxisRight().setDrawLabels(false);
            chart.setDescription("");
            chart.getLegend().setEnabled(false);
            chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Log.d(TAG, "onValueSelected: dipilih" + dataSetIndex);
                    int t = (int) e.getVal();
                    towers.setImageResource(towers_images[t]);
                    Date d = dates_of_week.get(e.getXIndex());
                    Log.d(TAG, "onValueSelected: dipilih" + d+" nilainya "+t);
                    solats = statsHandler.solatsOfSpecificDate(d);
                    Log.d(TAG, "onValueSelected: " + solats);
                    meessage = "";
                    for (int i=0;i<solats.size();i++) meessage = meessage + solats.get(i) + ", ";
                    Toast.makeText(getBaseContext(),"Anda telah mengerjakan salat "+meessage+"tepat waktu",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected() {

                }
            });
            chart.invalidate();
        }
    }



}
