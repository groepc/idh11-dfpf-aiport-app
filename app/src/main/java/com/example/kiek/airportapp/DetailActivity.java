package com.example.kiek.airportapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    String passedVar2 = null;
    private TextView name = null;
    private TextView icao = null;
    private TextView countryCode = null;
    private TextView location = null;
    private TextView distance = null;
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
        name = (TextView) findViewById(R.id.textName);
        icao = (TextView) findViewById(R.id.textIcao);
        location = (TextView) findViewById(R.id.textLocation);
        countryCode = (TextView) findViewById(R.id.textCountryCode);
        distance = (TextView) findViewById(R.id.textDistance);


        // Init database and query
        AirportsDatabase adb = new AirportsDatabase(this);
        final Cursor cursor2 = adb.getAirportDetail(passedVar2);

        cursor2.moveToFirst();

        String dbName = cursor2.getString(cursor2.getColumnIndex("name"));
        String dbIcao = cursor2.getString(cursor2.getColumnIndex("icao"));
        String dbLocation = cursor2.getString(cursor2.getColumnIndex("municipality"));
        dou1 = cursor2.getDouble(cursor2.getColumnIndex("longitude"));
        dou2 = cursor2.getDouble(cursor2.getColumnIndex("latitude"));
        String dbCountryCode = cursor2.getString(cursor2.getColumnIndex("iso_country"));

        Double distanceCalc = Haversine.distance(52, 5, dou2, dou1);


        name.setText(dbName);
        icao.setText(dbIcao);
        location.setText(dbLocation);
        countryCode.setText(dbCountryCode);
        distance.setText(distanceCalc.toString());




        //testView2.setText("The distance to Schiphol Airport (NL) is " + distance + "km.");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

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
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(52, 5), new LatLng(this.dou2, this.dou1))
                .width(5)
                .color(Color.RED));
        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(52, 5), new LatLng(this.dou2, this.dou1))
                .width(5)
                .color(Color.BLUE));
    }
}
