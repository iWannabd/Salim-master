package layout;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kucing.salim.FetchJasoJSON;
import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;
import com.example.kucing.salim.jadwalSholatAdapter;
import com.example.kucing.salim.modelListJadwalSolat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    ListView daftar;
    jadwalSholatAdapter adapter;
    public Jadwal jawa = null;
    public ArrayList<modelListJadwalSolat> CustomListViewValuesArr = new ArrayList<>();
    FetchJasoJSON mTask;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public void setListData(JSONObject jaso) throws JSONException {
        CustomListViewValuesArr.clear();
        final modelListJadwalSolat subuh = new modelListJadwalSolat();
        //subuh
        subuh.setImage(R.drawable.night);
        subuh.setSolatna("Subuh");
        subuh.setWaktuna(jaso.getString("fajr"));
        CustomListViewValuesArr.add(subuh);
        //dzuhr
        final modelListJadwalSolat dzuhr = new modelListJadwalSolat();
        dzuhr.setImage(R.drawable.day);
        dzuhr.setSolatna("Dzuhr");
        dzuhr.setWaktuna(jaso.getString("zuhr"));
        CustomListViewValuesArr.add(dzuhr);
        //ashar
        final modelListJadwalSolat ashar = new modelListJadwalSolat();
        ashar.setImage(R.drawable.day);
        ashar.setSolatna("Ashar");
        ashar.setWaktuna(jaso.getString("asr"));
        CustomListViewValuesArr.add(ashar);
        //maghrib
        final modelListJadwalSolat maghr = new modelListJadwalSolat();
        maghr.setImage(R.drawable.night);
        maghr.setSolatna("Maghrib");
        maghr.setWaktuna(jaso.getString("maghrib"));
        CustomListViewValuesArr.add(maghr);
        //isya
        final modelListJadwalSolat isya = new modelListJadwalSolat();
        isya.setImage(R.drawable.night);
        isya.setSolatna("Isya");
        isya.setWaktuna(jaso.getString("isha"));
        CustomListViewValuesArr.add(isya);

        Resources res = getResources();
        daftar = (ListView)getActivity().findViewById(R.id.Jaso);
        adapter = new jadwalSholatAdapter(getActivity(),CustomListViewValuesArr,res);
        daftar.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        jawa = this;
        View v = inflater.inflate(R.layout.fragment_jadwal, container, false);

        mTask = new FetchJasoJSON(this);
        daftar= (ListView)getActivity().findViewById(R.id.Jaso);
        mTask.execute();

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
