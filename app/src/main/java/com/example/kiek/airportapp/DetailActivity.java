package com.example.kiek.airportapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Kiek on 14-3-2016.
 */

public class DetailActivity extends Activity implements OnMapReadyCallback {

    String passedVar2 = null;
    private TextView testView = null;
    private TextView testView2 = null;
    private GoogleMap mMap;
    double dou1;
    double dou2;

    private final static String TAG2 = "DetailActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get passed var from intent
        passedVar2 = getIntent().getStringExtra(MainActivity.airport);

        //find out text view
        testView = (TextView) findViewById(R.id.test);
        //testView2 = (TextView) findViewById(R.id.test2);

        // Init database and query
        AirportsDatabase adb = new AirportsDatabase(this);
        final Cursor cursor2 = adb.getAirportDetail(passedVar2);

        cursor2.moveToFirst();

        String str1 = cursor2.getString(cursor2.getColumnIndex("name"));
        String str2 = cursor2.getString(cursor2.getColumnIndex("municipality"));
        dou1 = cursor2.getDouble(cursor2.getColumnIndex("longitude"));
        dou2 = cursor2.getDouble(cursor2.getColumnIndex("latitude"));
        String str3 = cursor2.getString(cursor2.getColumnIndex("iso_country"));
        Log.i(TAG2, str1);
        Log.i(TAG2, str2);

        //display passed data
        testView.setText("The airport is " + str1 + " in " + str2 + " (" + str3+ ").");
        //testView2.setText("Longitude: " + dou1 + " & latitude: " + dou2 +".");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng schiphol = new LatLng(52, 5);
        mMap.addMarker(new MarkerOptions().position(schiphol).title("Marker on Schiphol"));

        // Add a marker on the selected airport
        LatLng detailAirport = new LatLng(this.dou2, this.dou1);
        mMap.addMarker(new MarkerOptions().position(detailAirport).title("Marker on selected airport"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(schiphol));
    }
}
