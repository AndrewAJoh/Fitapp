package com.example.andrew.fitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrew on 8/19/2018.
 */

public class AddEvent extends AppCompatActivity {
    private static final String TAG = "AddEvent";
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static String weightedOrTimed;
    private static String simpleBoolean;
    private static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("workoutName");
        System.out.println("name of workout: " + name);
        weightedOrTimed = dbHelper.getData("SELECT Measurement FROM NameTable4 WHERE Name = '" + name + "'");
        simpleBoolean = dbHelper.getData("SELECT Simple FROM NameTable4 WHERE Name = '" + name + "'");
        getSupportActionBar().setTitle(name);
        if (weightedOrTimed.equals("Weighted")) {
            //Weighted Activity
            if (simpleBoolean.equals("False")) {
                setContentView(R.layout.activity_add_event_weighted);
                final EditText sets = findViewById(R.id.setsText);
                final EditText reps = findViewById(R.id.repsText);
                sets.addTextChangedListener(new TextWatcher() {
                    public void onTextChanged (CharSequence s,int start, int before, int count){
                        if (sets.getText().toString().trim().length() == 2)     //size as per your requirement
                        {
                            reps.requestFocus();
                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void afterTextChanged(Editable s) {
                    }
                });
            } else{
                setContentView(R.layout.activity_add_event_reps);
            }
        } else{
            //Timed activity
            if (simpleBoolean.equals("False")){
                setContentView(R.layout.activity_add_event);

            } else if (simpleBoolean.equals("True")){
                setContentView(R.layout.activity_add_event_timed_simple);
            }

            //Automatically move from minutes to seconds
            final EditText hours = findViewById(R.id.hhText);
            final EditText minutes = findViewById(R.id.mmText);
            final EditText seconds = findViewById(R.id.ssText);
            hours.addTextChangedListener(new TextWatcher() {
                public void onTextChanged (CharSequence s,int start, int before, int count){
                    if (hours.getText().toString().trim().length() == 2)     //size as per your requirement
                    {
                        minutes.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });
            minutes.addTextChangedListener(new TextWatcher() {
                public void onTextChanged (CharSequence s,int start, int before, int count){
                    if (minutes.getText().toString().trim().length() == 2)     //size as per your requirement
                    {
                        seconds.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });
        }


    }

    public void onAddClick(View view) {
        Boolean acceptable = true;
        Intent intent = new Intent();
        if (weightedOrTimed.equals("Weighted")) {
            if (simpleBoolean.equals("False")) {
                //Reps and Weights Activity
                EditText setsText = findViewById(R.id.setsText);
                EditText repsText = findViewById(R.id.repsText);
                String setsString = setsText.getText().toString();
                String repsString = repsText.getText().toString();
                EditText weightText = findViewById(R.id.weightText);
                String weightString = weightText.getText().toString();
                String newEventName = getIntent().getStringExtra("workoutName");
                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                dbHelper.addData("C", newEventName, weightString, repsString, setsString, fDate);
            } else{
                //Reps Activity
                EditText setsText = findViewById(R.id.setsText);
                EditText repsText = findViewById(R.id.repsText);
                String setsString = setsText.getText().toString();
                String repsString = repsText.getText().toString();
                String newEventName = getIntent().getStringExtra("workoutName");
                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                dbHelper.addData("C", newEventName, null, repsString, setsString, fDate);
            }
        } else{
            //Time
            if (simpleBoolean.equals("False")){
                //Time and Distance Activity
                EditText hoursText = findViewById(R.id.hhText);
                EditText minutesText = findViewById(R.id.mmText);
                EditText secondsText = findViewById(R.id.ssText);
                EditText distanceText = findViewById(R.id.distanceText);
                int hours = Integer.parseInt(hoursText.getText().toString());
                int minutes = Integer.parseInt(minutesText.getText().toString());
                if (minutes > 59){
                    acceptable = false;
                }
                int seconds = Integer.parseInt(secondsText.getText().toString());
                if (seconds > 59){
                    acceptable = false;
                }
                int total = hours*3600;
                total += (minutes * 60);
                total += seconds;
                String timeString = Integer.toString(total);
                String distanceString = distanceText.getText().toString();
                String newEventName = getIntent().getStringExtra("workoutName");
                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                if (acceptable) {
                    dbHelper.addData("B", newEventName, timeString, distanceString, null, fDate);
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter a valid time",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else if (simpleBoolean.equals("True")){
                //Time Activity
                EditText hoursText = findViewById(R.id.hhText);
                EditText minutesText = findViewById(R.id.mmText);
                EditText secondsText = findViewById(R.id.ssText);
                int hours = Integer.parseInt(hoursText.getText().toString());
                int minutes = Integer.parseInt(minutesText.getText().toString());
                if (minutes > 59 ){
                    acceptable = false;
                }
                int seconds = Integer.parseInt(secondsText.getText().toString());
                if (seconds > 59){
                    acceptable = false;
                }
                int total = hours*3600;
                total += (minutes * 60);
                total += seconds;
                String timeString = Integer.toString(total);
                String newEventName = getIntent().getStringExtra("workoutName");
                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                if (acceptable) {
                    dbHelper.addData("B", newEventName, timeString, null, null, fDate);
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter a valid time",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        if (acceptable) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }



    public void onCancelClick(View view){
        finish();
    }


}
