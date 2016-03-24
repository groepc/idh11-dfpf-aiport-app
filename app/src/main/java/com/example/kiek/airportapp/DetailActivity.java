package com.example.kiek.airportapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Kiek on 14-3-2016.
 */

public class DetailActivity extends Activity {

    String passedVar = null;
    private TextView passedView = null;
    private TextView testView = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get passed var from intent
        passedVar = getIntent().getStringExtra(MainActivity.ID_EXTRA);

        //find out text view
        passedView = (TextView) findViewById(R.id.passed);
        testView = (TextView) findViewById(R.id.test);

        //display passed data
        passedView.setText("You're viewing airport=" + passedVar);

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

}
