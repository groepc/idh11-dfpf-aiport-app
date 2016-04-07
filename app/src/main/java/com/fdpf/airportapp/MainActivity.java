package com.fdpf.airportapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    public ListView airportListView;
    ArrayList list;

    public final static String airport = "com.fdpf.airportapp._name";

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
                R.layout.my_listview, list);
        airportListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        airportListView.setOnItemClickListener(onListClick);

    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            Log.i("Button click", "");
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            //i.putExtra(ID_EXTRA, String.valueOf(id));

            String value = (String) parent.getItemAtPosition(position);
            String arr[] = value.split(" ", 2);

            Log.i(TAG, arr[0]);
            i.putExtra(airport, arr[0]);

            startActivity(i);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        ComponentName cn = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_search) {
            onSearchRequested();
            return true;
        };

        return super.onOptionsItemSelected(item);

    }
}