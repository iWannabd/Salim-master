package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kucing.salim.ItemJadwalSholat;
import com.example.kucing.salim.JadwalSolatParser;
import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;
import com.example.kucing.salim.jadwalSholatAdapter;
import com.example.kucing.salim.PrayTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Bind(R.id.Jaso) ListView jaso;
    jadwalSholatAdapter adapter;
    ArrayList<String> PrayTimesSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jadwal, container, false);
        ButterKnife.bind(this, v);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location",Context.MODE_PRIVATE);

        Double deflat = -6.9147;
        Float lat = sharedPreferences.getFloat("latloc", deflat.floatValue());

        Double deflon = 107.6098;
        Float lon = sharedPreferences.getFloat("lonloc", deflon.floatValue());

        Log.d("loc", "onCreateView: "+lat.floatValue()+" "+lon.floatValue());

        PrayTimesSet = PrayTime.getFiveTimes(lon, lat, new Date());
        adapter = JadwalSolatParser.getAdapter(PrayTimesSet,getResources(),getActivity());
        jaso.setAdapter(adapter);

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
