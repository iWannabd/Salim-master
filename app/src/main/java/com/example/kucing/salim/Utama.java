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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

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
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    final int PLACE_PICKER_REQUEST = 1;

    @Bind(R.id.now_date)
    TextView now_date;
    @Bind(R.id.next_pray)
    TextView next_pray;
    @Bind(R.id.user_name)
    TextView username;
    @Bind(R.id.mainheader)
    ImageView header;
    @Bind(R.id.headericon)
    ImageView hicon;
    SimpleDateFormat waktu = new SimpleDateFormat("HH:mm");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences getGeneralPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        username.setText(getGeneralPrefs.getString("username", "Hamba Allah"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        ButterKnife.bind(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //hide toolbar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // Set up the drawer menu for setting and about activity
        SharedPreferences getGeneralPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        username.setText(getGeneralPrefs.getString("example_text", "Hamba Allah"));
        String[] prefmen = {"About", "Preference", "Statistic","Set Location"};
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
                        startActivity(new Intent(Utama.this, UserStats.class));
                        break;
                    case 3:
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        try {
                            startActivityForResult(builder.build(Utama.this),PLACE_PICKER_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });
        setFiveTimes(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("location", Context.MODE_PRIVATE);

        Double deflat = -6.9147;
        Float lat = sharedPreferences.getFloat("latloc", deflat.floatValue());

        Double deflon = 107.6098;
        Float lon = sharedPreferences.getFloat("lonloc", deflon.floatValue());

        Log.d("loc", "onCreateView: " + lat.floatValue() + " " + lon.floatValue());

        ChangeAllAboutHeader(PrayTime.getFiveTimes(lon,lat,new Date()));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,this);
                LatLng location = place.getLatLng();
                Double lat = location.latitude;
                Double lon = location.longitude;
                SharedPreferences sharedPreferences = this.getSharedPreferences("location",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.d("loc", "onActivityResult: "+lat.floatValue()+" "+lon.floatValue());

                editor.putFloat("latloc", lat.floatValue());
                editor.putFloat("lonloc",lon.floatValue());

                editor.commit();
            }
        }
    }

    //alarm setter for setting each pray alarm
    public static void setFiveTimes(Context context) {
        //unggal poe
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 0);

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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Utama Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kucing.salim/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Utama Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kucing.salim/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }


    public void ChangeAllAboutHeader(ArrayList<String> jaso) {
        try {
            ArrayList<Date> jasoTime = new ArrayList<>();
            for (int i=0; i< jaso.size();i++)
                jasoTime.add(waktu.parse(jaso.get(i)));

            Date a = waktu.parse(waktu.format(new Date()));
            ArrayList<String> nama = PrayTime.getFiveNames();

            for (int i =1; i<5;i++ )
                if (a.before(jasoTime.get(i)) && a.after(jasoTime.get(i-1))){
                    now_date.setText(jaso.get(i));
                    next_pray.setText(nama.get(i));
                }
            if (a.after(jasoTime.get(4))||a.before(jasoTime.get(0))){
                now_date.setText(jaso.get(0));
                next_pray.setText(nama.get(0));
            }

            //mengganti gambar header
            if (a.after(jasoTime.get(0)) && a.before(jasoTime.get(3))) {
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
            if (a.after(jasoTime.get(3)) || a.before(jasoTime.get(0))) {
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

