package layout;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kucing.salim.jadwalSholatAdapter;
import com.example.kucing.salim.modelListJadwalSolat;
import com.prolificinteractive.materialcalendarview.*;

import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Kalendar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Kalendar extends Fragment implements OnFragmentInteractionListener,OnDateSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public ArrayList<modelListJadwalSolat> CustomListViewValuesArr = new ArrayList<>();
    JSONObject harian,bulanan;
    FetchCustomJason mTask;
    public Date selectedDate;
    SimpleDateFormat d = new SimpleDateFormat("d");
    SimpleDateFormat y = new SimpleDateFormat("yyyy");
    SimpleDateFormat m = new SimpleDateFormat("M");

    @Bind(R.id.jasoall) ListView daftar;
    @Bind(R.id.kalen) MaterialCalendarView kalendar;
    @Bind(R.id.jasonow) TextView jasonow;

    public Kalendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Kalendar.
     */
    // TODO: Rename and change types and number of parameters
    public static Kalendar newInstance(String param1, String param2) {
        Kalendar fragment = new Kalendar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_kalendar, container, false);
        ButterKnife.bind(this,v);
        kalendar.setOnDateChangedListener(this);
        Calendar harini = Calendar.getInstance();
        Date ini = new Date();
        harini.setTime(ini);
        harini.set(Calendar.MONTH, (harini.get(Calendar.MONTH) + 2));
        kalendar.setMaximumDate(harini);
        harini.setTime(ini);
        harini.set(Calendar.MONTH, (harini.get(Calendar.MONTH) - 2));
        kalendar.setMinimumDate(harini);
        kalendar.setDateSelected(new Date(), true);

        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setListData(String jason_string) throws JSONException {
        CustomListViewValuesArr.clear();
        bulanan = new JSONObject(jason_string);
        harian = bulanan.getJSONObject(d.format(selectedDate));
        modelListJadwalSolat subuh = new modelListJadwalSolat();
        //subuh
        subuh.setImage(R.drawable.night);
        subuh.setSolatna("Subuh");
        subuh.setWaktuna(harian.getString("fajr"));
        CustomListViewValuesArr.add(subuh);
        Log.d("SALAH", "setListData: "+harian.getString("fajr"));
        //dzuhr
        modelListJadwalSolat dzuhr = new modelListJadwalSolat();
        dzuhr.setImage(R.drawable.day);
        dzuhr.setSolatna("Dzuhr");
        dzuhr.setWaktuna(harian.getString("zuhr"));
        CustomListViewValuesArr.add(dzuhr);
        //ashar
        modelListJadwalSolat ashar = new modelListJadwalSolat();
        ashar.setImage(R.drawable.day);
        ashar.setSolatna("Ashar");
        ashar.setWaktuna(harian.getString("asr"));
        CustomListViewValuesArr.add(ashar);
        //maghrib
        modelListJadwalSolat maghr = new modelListJadwalSolat();
        maghr.setImage(R.drawable.night);
        maghr.setSolatna("Maghrib");
        maghr.setWaktuna(harian.getString("maghrib"));
        CustomListViewValuesArr.add(maghr);
        //isya
        modelListJadwalSolat isya = new modelListJadwalSolat();
        isya.setImage(R.drawable.night);
        isya.setSolatna("Isya");
        isya.setWaktuna(harian.getString("isha"));
        CustomListViewValuesArr.add(isya);
        Log.d("SALAH", "mungkin salah di sini kah? "+isya.getWaktuna());

        Resources res = getResources();
        daftar = (ListView) this.getActivity().findViewById(R.id.jasoall);
        jadwalSholatAdapter adapter = new jadwalSholatAdapter(this.getActivity(),CustomListViewValuesArr,res);
        Log.d("SALAH", "atau di sini?");
        daftar.setAdapter(adapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void onFragmentInteraction(Uri uri){

    }

    //dari onfragment interaction listener
    @Override
    public void setNowDate(String te, ArrayList<modelListJadwalSolat> jaso) {

    }


//dari kalendar material
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget,@NonNull CalendarDay date, boolean selected) {
        selectedDate = kalendar.getSelectedDate().getDate();
        new FetchCustomJason().execute();
    }

    public class FetchCustomJason extends AsyncTask<String,Integer,String>{
        HttpURLConnection urlcon;


        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();
            try {
                String urel = "http://api.xhanch.com/islamic-get-prayer-time.php?lng=107.6098111&lat=-6.9147444&yy=" + y.format(selectedDate) + "&mm=" + m.format(selectedDate) + "&gmt=7&m=json";
                URL url = new URL(urel);
                urlcon = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlcon.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) result.append(line);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlcon.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jasonow.setText("Tunggu...");
        }

        @Override
        protected  void onPostExecute(String result){
            try {
                setListData(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jasonow.setText("Jadwal Sholat Untuk "+new SimpleDateFormat("dd MMMM yyyy").format(selectedDate));
        }
    }
}
