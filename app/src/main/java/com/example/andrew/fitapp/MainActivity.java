/**
 * 9/17/18
 * TO DO:
 * Clean up inefficient code
 * Test app UI on different devices
 * Add graphs
 * Fix crashes related to incorrect user input
 */



package com.example.andrew.fitapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static boolean useUS;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        useUS = db.getMeasurementSettings();
    }

    public void onActivityClick(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        String idString = view.getResources().getResourceEntryName(view.getId());
        intent.putExtra("workoutType", idString);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    //Handle menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsButton:
                Intent i = new Intent(this, SettingsActivity.class);
                this.startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}