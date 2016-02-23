package com.example.kucing.salim;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kucing on 09/01/16.
 */
public class jadwalSholatAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ItemJadwalSholat> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ItemJadwalSholat tempValues= null;

    public jadwalSholatAdapter(Activity act, ArrayList<ItemJadwalSholat> al, Resources resLocal){
        activity = act;
        data = al;
        res = resLocal;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public ArrayList<ItemJadwalSholat> getData(){
        return data;
    }

    public int getCount() {
        if (data.size() < 0){
            return 1;
        }
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position){
        return position;
    }

    public static class ViewHolder{
        public TextView text;
        public TextView text1;
        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null){
            vi = inflater.inflate(R.layout.setiap_waktu_salat,null);

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.salatna);
            holder.text1 = (TextView) vi.findViewById(R.id.waktuna);
            holder.image = (ImageView) vi.findViewById(R.id.imageView);

            vi.setTag(holder);
        }else
            holder = (ViewHolder)vi.getTag();
        if (data.size()<=0)
            holder.text.setText("No Data");
        else {
            tempValues = null;
            tempValues = (ItemJadwalSholat) data.get(position);

            holder.text.setText(tempValues.getSolatna());
            holder.text1.setText(tempValues.getWaktuna());
            holder.image.setImageResource(tempValues.getImage());
        }
        return vi;
    }
}
