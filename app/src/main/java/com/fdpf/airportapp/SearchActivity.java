package com.fdpf.airportapp;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private final static String TAG = "SearchActivity";
    public final static String airport = "com.fdpf.airportapp._name";

    AirportsDatabase adb;
    public ListView searchListView;
    ArrayList list2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Inflate listview
        list2 = new ArrayList<String>();
        searchListView = (ListView) findViewById(R.id.searchListView);
        adb = new AirportsDatabase(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            showResults(query);
            Log.i(TAG, query);
        }
    }

    private void showResults(String query) {

        // Init database and query
        final Cursor cursor4 = adb.getSearchResult(query);
        System.out.print(cursor4);

        cursor4.moveToFirst();
        while (cursor4.moveToNext()) {
            String str = cursor4.getString(cursor4.getColumnIndex("icao"));
            String str1 = cursor4.getString(cursor4.getColumnIndex("name"));
            list2.add(str + " - " + str1);
            Log.i(TAG, str);
        }
        Log.i(TAG, "count: " + list2.size());

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.my_listview, list2);
        searchListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        searchListView.setOnItemClickListener(onListClick);

    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            Log.i("Button click", "");
            Intent i = new Intent(SearchActivity.this, DetailActivity.class);

            String value = (String) parent.getItemAtPosition(position);
            String arr[] = value.split(" ", 2);

            Log.i(TAG, arr[0]);
            i.putExtra(airport, arr[0]);

            startActivity(i);

        }
    };
}
