package com.example.kucing.salim;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import layout.Hadits;
import layout.Jadwal;
import layout.Kalendar;

public class Utama extends AppCompatActivity
        implements OnFragmentInteractionListener {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Bind(R.id.now_date) TextView now_date;
    @Bind(R.id.next_pray) TextView next_pray;
    @Bind(R.id.user_name) TextView username;
    @Bind(R.id.mainheader) ImageView header;
    @Bind(R.id.headericon) ImageView hicon;
    SimpleDateFormat waktu = new SimpleDateFormat("HH:mm");

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences getGeneralPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        username.setText(getGeneralPrefs.getString("username", "Hamba Allah"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        ButterKnife.bind(this);

        //PUT DUMMY DATA
//        StatsHandler sh = new StatsHandler(this);
//        sh.putAll("{\n" +
//                "\t\"24/02/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"25/02/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"26/02/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"maghrib\",\"isya\"],\n" +
//                "\t\"27/02/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"28/02/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"isya\"],\n" +
//                "\t\"29/02/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"01/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"maghrib\",\"isya\"],\n" +
//                "\t\"02/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"03/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\"],\n" +
//                "\t\"04/03/2016\":\n" +
//                "\t[\"subuh\",\"ashar\",\"maghrib\"],\n" +
//                "\t\"05/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"06/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"07/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\"],\n" +
//                "\t\"08/03/2016\":\n" +
//                "\t[\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"09/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"10/03/2016\":\n" +
//                "\t[\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"],\n" +
//                "\t\"11/03/2016\":\n" +
//                "\t[\"subuh\",\"dzuhr\",\"ashar\",\"maghrib\",\"isya\"]\n" +
//                "}\n");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //hide toolbar
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // Set up the drawer menu for setting and about activity
        SharedPreferences getGeneralPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        username.setText(getGeneralPrefs.getString("example_text", "Hamba Allah"));
        String[] prefmen = {"About","Preference", "Statistic"};
        ListView prefMenu = (ListView) findViewById(R.id.drawerMenu);
        prefMenu.setDivider(null);
        prefMenu.setAdapter(new DrawerMenu(this, prefmen));
        prefMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        startActivity(new Intent(Utama.this, SettingsActivity.class));
                        break;
                    case 0:
                        startActivity(new Intent(Utama.this, AboutUs.class));
                        break;
                    case 2:
                        startActivity(new Intent(Utama.this,UserStats.class));
                }
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });
        setFiveTimes(this);

    }

    //alarm setter for setting each pray alarm
    public static void setFiveTimes(Context context){
        //unggal poe
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY,0);

        Intent alertIntent = new Intent(context, AlertRecivier.class);
        AlarmManager alma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent penten = PendingIntent.getBroadcast(context, 7, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alma.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, penten);

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Log.d("warong", "setFiveTimes: sip");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
  

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Jadwal();
                case 1:
                    return new Kalendar();
                case 2:
                    return new Hadits();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Jadwal";
                case 1:
                    return "Kalender";
                case 2:
                    return "Hadits";
                default:
                    return null;
            }
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    public void ChangeAllAboutHeader(ArrayList<ItemJadwalSholat> jaso) {
        //used for change the main timer in the main activity
        try {
            Date a = waktu.parse(waktu.format(new Date()));
            Date[] b = new Date[5];
            for (int i=0;i<5;i++){
                b[i] = waktu.parse(jaso.get(i).getWaktuna());
                Log.d("wrong", "ChangeAllAboutHeader: "+b[i]);
            }
            Log.d("wrong", "ChangeAllAboutHeader: "+a);
            //mengganti tulisan header
            if (a.before(b[1])&&a.after(b[0])) {
                now_date.setText(jaso.get(1).getWaktuna());
                next_pray.setText(jaso.get(1).getSolatna());
            }
            if (a.before(b[2])&&a.after(b[1])) {
                now_date.setText(jaso.get(2).getWaktuna());
                next_pray.setText(jaso.get(2).getSolatna());
            }
            if (a.before(b[3])&&a.after(b[2])) {
                now_date.setText(jaso.get(3).getWaktuna());
                next_pray.setText(jaso.get(3).getSolatna());
            }
            if (a.before(b[4])&&a.after(b[3])) {
                now_date.setText(jaso.get(4).getWaktuna());
                next_pray.setText(jaso.get(4).getSolatna());
            }
            if (a.after(b[4])||a.before(b[0])){
                now_date.setText(jaso.get(0).getWaktuna());
                next_pray.setText(jaso.get(0).getSolatna());
            }
            //mengganti gambar header
            if (a.after(b[0]) && a.before(b[3])){
                header.setImageResource(R.drawable.headerbh);
                hicon.setImageResource(R.drawable.day_big);
                ActionBar bar = getActionBar();
                if (bar != null) {
                    bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDay)));
                }
                Window win = this.getWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkDay));
                }
            }
            if (a.after(b[3]) || a.before(b[0])){
                header.setImageResource(R.drawable.headerbg);
                hicon.setImageResource(R.drawable.night_big);
                ActionBar bar = getActionBar();
                if (bar != null) {
                    bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }
                Window win = this.getWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}

