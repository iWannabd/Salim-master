package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kucing.salim.ItemJadwalSholat;
import com.example.kucing.salim.JadwalSolatParser;
import com.prolificinteractive.materialcalendarview.*;

import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    public ArrayList<ItemJadwalSholat> CustomListViewValuesArr = new ArrayList<>();
    JSONObject harian,bulanan;
    public Date selectedDate;
    SimpleDateFormat d = new SimpleDateFormat("d");

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
        ButterKnife.bind(this, v);
        kalendar.setOnDateChangedListener(this);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        kalendar.setMaximumDate(cal);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        kalendar.setMinimumDate(cal);
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
        daftar = (ListView) this.getActivity().findViewById(R.id.jasoall);
        daftar.setAdapter(JadwalSolatParser.getAdapter(jason_string,d.format(selectedDate),getResources(),getActivity()));
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
    public void ChangeAllAboutHeader(ArrayList<ItemJadwalSholat> jaso) {

    }


//dari kalendar material
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget,@NonNull CalendarDay date, boolean selected) {
        selectedDate = kalendar.getSelectedDate().getDate();
        SharedPreferences sape = getActivity().getSharedPreferences("Jaso",Context.MODE_PRIVATE);
        String jason = sape.getString(new SimpleDateFormat("M/yyyy").format(new Date()),"Empty");
        try {
            if (!jason.equals("Empty")){
                setListData(jason);
                jasonow.setText("Jadwal Sholat Tanggal "+d.format(selectedDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
