package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kucing.salim.FetchJasoJSON;
import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;
import com.example.kucing.salim.jadwalSholatAdapter;
import com.example.kucing.salim.modelListJadwalSolat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Jadwal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Jadwal extends Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ListView daftar;
    jadwalSholatAdapter adapter;
    public Jadwal jawa = null;
    public ArrayList<modelListJadwalSolat> CustomListViewValuesArr = new ArrayList<>();
    FetchJasoJSON mTask;
    SharedPreferences SharePref;
    JSONObject harian,bulanan;
    SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy");
    String expire_jason = sdf.format(new Date());

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final String TAG = "SALAH";

    private OnFragmentInteractionListener mListener;


    public Jadwal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Jadwal.
     */
    // TODO: Rename and change types and number of parameters
    public static Jadwal newInstance(String param1, String param2) {
        Jadwal fragment = new Jadwal();
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

    public void setListData(String jason_string) throws JSONException {
        CustomListViewValuesArr.clear();
        Log.d(TAG, "setListData: "+jason_string);
        bulanan = new JSONObject(jason_string);
        SimpleDateFormat df = new SimpleDateFormat("d");
        harian = bulanan.getJSONObject(df.format(new Date()));
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
        daftar = (ListView) this.getActivity().findViewById(R.id.Jaso);
        adapter = new jadwalSholatAdapter(this.getActivity(),CustomListViewValuesArr,res);
        Log.d("SALAH", "atau di sini?");
        daftar.setAdapter(adapter);
    }

    public void simpanJason(String Jaso){
        SharePref = this.getActivity().getSharedPreferences("Jaso", this.getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = SharePref.edit();
        editor.putString(expire_jason,Jaso);
        editor.apply();
    }

    public String bacaJason(){
        SharePref = this.getActivity().getSharedPreferences("Jaso", this.getActivity().MODE_PRIVATE);
        String default_jaso = getResources().getString(R.string.default_jaso);
        return SharePref.getString(expire_jason,default_jaso);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        jawa = this;
        View v = inflater.inflate(R.layout.fragment_jadwal, container, false);

        mTask = new FetchJasoJSON(this);
        daftar = (ListView)getActivity().findViewById(R.id.Jaso);
        mTask.execute();

        mListener.setNowDate(expire_jason);

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

    public void onFragmentInteraction(Uri uri){

    }

    public void setNowDate(String te) {

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
}
