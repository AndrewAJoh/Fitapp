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

public class AddEventActivity extends AppCompatActivity {
    private static final String TAG = "AddEvent";
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static int workoutMeasurement;
    private static String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        workoutName = getIntent().getStringExtra("workoutName");
        WorkoutData workoutData = dbHelper.getWorkoutDataByName(workoutName);
        workoutMeasurement = workoutData.measurement;
        getSupportActionBar().setTitle(workoutName);
        if (workoutMeasurement == 1) { //Reps and Weight
            setContentView(R.layout.activity_add_event_weighted);
            final EditText sets = findViewById(R.id.setsText);
            final EditText reps = findViewById(R.id.repsText);
            sets.addTextChangedListener(new TextWatcher() { //TODO: Re-evaluate this
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (sets.getText().toString().trim().length() == 2) {
                        reps.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });
        } else if (workoutMeasurement == 2){
                setContentView(R.layout.activity_add_event_reps);
        } else{
            //Timed activity
            if (workoutMeasurement == 3){ //Time and Distance
                setContentView(R.layout.activity_add_event);

            } else if (workoutMeasurement == 4){ //Time
                setContentView(R.layout.activity_add_event_timed_simple);
            }

            //Automatically move from minutes to seconds
            final EditText hours = findViewById(R.id.hhText);
            final EditText minutes = findViewById(R.id.mmText);
            final EditText seconds = findViewById(R.id.ssText);

            hours.addTextChangedListener(new TextWatcher() { //TODO: Re-evaluate this
                public void onTextChanged (CharSequence s, int start, int before, int count){
                    if (hours.getText().toString().trim().length() == 2)
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
                public void onTextChanged (CharSequence s, int start, int before, int count){
                    if (minutes.getText().toString().trim().length() == 2)
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

        EventData newActivity;
        String newActivityName = getIntent().getStringExtra("workoutName");

        if (workoutMeasurement == 1){
            //Reps and Weights Activity
            EditText setsText = findViewById(R.id.setsText);
            EditText repsText = findViewById(R.id.repsText);
            EditText weightText = findViewById(R.id.weightText);

            String setsString = setsText.getText().toString();
            String repsString = repsText.getText().toString();
            String weightString = weightText.getText().toString();


            Date currentDate = new Date();
            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(currentDate);

            newActivity = new EventData(null, newActivityName, formattedDate, null, null, weightString, repsString, setsString);


        } else if (workoutMeasurement == 2){
            //Reps Activity
            EditText setsText = findViewById(R.id.setsText);
            EditText repsText = findViewById(R.id.repsText);

            String setsString = setsText.getText().toString();
            String repsString = repsText.getText().toString();

            Date currentDate = new Date();
            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(currentDate);

            newActivity = new EventData(null, newActivityName, formattedDate, null, null, null, repsString, setsString);

        } else if (workoutMeasurement == 3){
            //Time and Distance Activity
            EditText hoursText = findViewById(R.id.hhText);
            String hoursString = hoursText.getText().toString();

            EditText minutesText = findViewById(R.id.mmText);
            String minutesString = minutesText.getText().toString();

            EditText secondsText = findViewById(R.id.ssText);
            String secondsString = secondsText.getText().toString();

            EditText distanceText = findViewById(R.id.distanceText);
            String distanceEditText = distanceText.getText().toString();

            if (hoursString.matches("") ||
                minutesString.matches("") ||
                secondsString.matches("") ||
                distanceEditText.matches("")){
                acceptable = false;
            }

            int hours = Integer.parseInt(hoursText.getText().toString());
            int minutes = Integer.parseInt(minutesText.getText().toString());
            int seconds = Integer.parseInt(secondsText.getText().toString());

            if (minutes > 59 || seconds > 59){
                acceptable = false;
            }

            int time = hours*3600;
            time += (minutes * 60);
            time += seconds;

            String distanceString = distanceText.getText().toString();

            Date currentDate = new Date();
            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(currentDate);

            newActivity = new EventData(null, newActivityName, formattedDate, time, distanceString, null, null, null);

            if (!acceptable) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please enter a valid time",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        } else{
            //Time Activity
            EditText hoursText = findViewById(R.id.hhText);
            String hoursString = hoursText.getText().toString();

            EditText minutesText = findViewById(R.id.mmText);
            String minutesString = minutesText.getText().toString();

            EditText secondsText = findViewById(R.id.ssText);
            String secondsString = secondsText.getText().toString();

            if (hoursString.matches("") ||
                minutesString.matches("") ||
                secondsString.matches("")){
                acceptable = false;
            }
            int hours = Integer.parseInt(hoursText.getText().toString());
            int minutes = Integer.parseInt(minutesText.getText().toString());
            int seconds = Integer.parseInt(secondsText.getText().toString());

            if (minutes > 59 || seconds > 59){
                acceptable = false;
            }

            int time = hours*3600;
            time += (minutes * 60);
            time += seconds;

            Date currentDate = new Date();
            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(currentDate);

            newActivity = new EventData(null, newActivityName, formattedDate, time, null, null, null, null);

            if (!acceptable){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please enter a valid time",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (acceptable) {
            dbHelper.addEventData(newActivity);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onCancelClick(View view){
        finish();
    }


}
