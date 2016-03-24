package com.example.kiek.airportapp;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    public ListView airportListView;
    private AirportCursorAdapter airportCursorAdapter;
    ArrayList list;

    public final static String ID_EXTRA = "com.example.kiek.airportapp._icao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<String>();

        // Inflate listview
        airportListView = (ListView) findViewById(R.id.airPortListView);

        // Init database and query
        AirportsDatabase adb = new AirportsDatabase(this);
        final Cursor cursor = adb.getAirports();

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex("icao"));
            String str1 = cursor.getString(cursor.getColumnIndex("name"));
            list.add(str + " - " + str1);
            Log.i(TAG, str);
        }
        Log.i(TAG, "count: " + list.size());

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        airportListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        airportListView.setOnItemClickListener(onListClick);

    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){

        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

                Log.i("Button click", "");
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra(ID_EXTRA, String.valueOf(id));
                i.putExtra("name", String.valueOf("name"));
                startActivity(i);

            }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
