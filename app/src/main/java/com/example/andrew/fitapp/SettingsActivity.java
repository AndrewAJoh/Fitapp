package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private static RadioButton metric;
    private static RadioButton us;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        metric = findViewById(R.id.metricButton);
        us = findViewById(R.id.usButton);

        if (db.getMeasurementSettings()){
            us.setChecked(true);
        } else{
            metric.setChecked(true);
        }

        metric.setOnClickListener(v -> {
            Log.d(TAG, "clicked on metric radio button");
            MainActivity.useUS = false;
            db.setMeasurementSettings(false);
        });

        us.setOnClickListener(v -> {
            Log.d(TAG, "clicked on us radio button");
            MainActivity.useUS = true;
            db.setMeasurementSettings(true);
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
