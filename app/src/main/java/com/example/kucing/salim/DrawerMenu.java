package com.example.kucing.salim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by node06 on 16/01/2016.
 */
public class DrawerMenu extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public DrawerMenu(Context c, String[] ss){
        super(c,R.layout.setiap_menu_drawer,ss);
        this.context = c;
        this.values = ss;
    }

    @Override
    public View getView(int Pos, View convrtView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.setiap_menu_drawer,parent,false);
        TextView nama = (TextView) rowView.findViewById(R.id.textView3);
        ImageView gambar = (ImageView) rowView.findViewById(R.id.imageView3);
        nama.setText(values[Pos]);

        String s = values[Pos];

        if (s.equals("About")) gambar.setImageResource(R.drawable.ic_info_outline_white_24dp);
        if (s.equals("Preference")) gambar.setImageResource(R.drawable.ic_settings_white_24dp);
        if (s.equals("Statistic")) gambar.setImageResource(R.drawable.ic_insert_chart_white_24dp);



        return rowView;
    }
}
