package com.example.kiek.airportapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Kiek on 14-3-2016.
 */
public class AirportCursorAdapter extends CursorAdapter {

    // private LayoutInflater infalter;

    public AirportCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, 0);

    }

    // Inflate the new view. Geen databinding
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //
        //View view = infalter.inflate(R.layout.listview_row, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.listview_row, parent, false);
        return view;
    }

    // Hier wordt de data gebind aan de view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //ViewHolder holder = new ViewHolder();
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView icao = (TextView) view.findViewById(R.id.icao);

        //String name = cursor.getString(cursor.getColumnIndex("name"));
        //String icao = cursor.getString(cursor.getColumnIndex("icao"));

        name.setText("Hoi");
        icao.setText("Allemaal");

        //view.setTag(holder);

    }

   //public static class ViewHolder {
    //    TextView icao;
     //   TextView name;
   //}
}
