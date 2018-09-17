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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    public static final String EXTRA_MESSAGE = "com.example.fitapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onActivityClick(View view) {
        Intent intent = new Intent(this, OpenActivity.class);
        String idString = view.getResources().getResourceEntryName(view.getId());
        intent.putExtra("workoutType", idString);
        startActivity(intent);
    }
}