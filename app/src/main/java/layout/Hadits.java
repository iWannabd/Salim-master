package layout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kucing.salim.AlertRecivier;
import com.example.kucing.salim.ItemJadwalSholat;
import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Hadits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Hadits extends Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public Hadits() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Hadits.
     */
    // TODO: Rename and change types and number of parameters
    public static Hadits newInstance(String param1, String param2) {
        Hadits fragment = new Hadits();
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

    TextView hadits;
    Button shareit;
    ImageButton fb,tw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hadits, container, false);
        hadits = (TextView) v.findViewById(R.id.hadits);
        shareit = (Button) v.findViewById(R.id.shareit);
        fb = (ImageButton) v.findViewById(R.id.postfb);
        tw = (ImageButton) v.findViewById(R.id.tweet);

        shareit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bagi = new Intent();
                bagi.setAction(Intent.ACTION_SEND);
                bagi.putExtra(Intent.EXTRA_TEXT,hadits.getText());
                bagi.setType("text/plain");
                startActivity(bagi);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm(v);
            }
        });

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sape = getActivity().getSharedPreferences("Jaso", Activity.MODE_PRIVATE);
                String ha = sape.getString("2/2016","Empty");
                Toast.makeText(getActivity(),ha,Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    public void setAlarm(View v){
        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent alertIntent = new Intent(v.getContext(), AlertRecivier.class);
        AlarmManager alma = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent penten = PendingIntent.getBroadcast(v.getContext(), 0, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alma.set(AlarmManager.RTC_WAKEUP, alertTime,penten);

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

    public void onFragmentInteraction(Uri uri){}

    @Override
    public void setNowDate(String te, ArrayList<ItemJadwalSholat> jaso) {

    }




}
