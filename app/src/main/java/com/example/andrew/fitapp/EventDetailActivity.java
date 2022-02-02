package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew on 9/6/2018.
 */

public class EventDetailActivity extends AppCompatActivity {
    private static final String TAG = "EntryDetail";
    DatabaseHelper dbHelper;
    private static int measurement;
    private static String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        String workoutName = getIntent().getStringExtra("workoutName");
        String formattedWorkoutName = workoutName.substring(0, 1).toUpperCase() + workoutName.substring(1);
        String date = getIntent().getStringExtra("date");
        activityId = getIntent().getStringExtra("id");

        String query = "SELECT Measurement FROM " + DatabaseHelper.WORKOUT_TABLE_TITLE + " WHERE Name = '" + workoutName + "'";
        WorkoutData workoutData = dbHelper.getWorkoutDataByName(workoutName);
        measurement = workoutData.measurement;

        TextView workoutNameDisplayRight = findViewById(R.id.workoutNameDisplayRight);
        TextView workoutTypeDisplayRight = findViewById(R.id.workoutTypeDisplayRight);
        workoutNameDisplayRight.setText(formattedWorkoutName);

        ActivityData activityData = dbHelper.getActivityDataById(activityId);

        if (measurement == 1) {
            setContentView(R.layout.activity_entry_detail_weights);

            String weight = activityData.weight;
            String setsReps = activityData.sets + " x " + activityData.reps;

            workoutTypeDisplayRight.setText("Weight and Reps");

            TextView dateRight = findViewById(R.id.dateRight);
            dateRight.setText(date);

            TextView weightRight = findViewById(R.id.weightRight);
            weightRight.setText(weight);

            TextView repsSetsRight = findViewById(R.id.repsSetsRight);

            repsSetsRight.setText(setsReps);
        } else if (measurement == 2){
            setContentView(R.layout.activity_entry_detail_weights_simple);

            String setsReps = activityData.sets + " x " + activityData.reps;

            workoutTypeDisplayRight.setText("Reps");

            TextView dateRight = findViewById(R.id.dateRight);
            dateRight.setText(date);

            TextView repsRight = findViewById(R.id.repsRight);
            repsRight.setText(setsReps);
        } else {
            if (measurement == 3) {
                setContentView(R.layout.activity_entry_detail_distance);
                workoutTypeDisplayRight.setText("Time and Distance");
                TextView distanceRight = findViewById(R.id.distanceRight);
                String distance = activityData.distance;
                distanceRight.setText(distance);
            } else {
                setContentView(R.layout.activity_entry_detail_simple);
                workoutTypeDisplayRight.setText("Time");
            }

            TextView dateRight = findViewById(R.id.dateRight);
            dateRight.setText(date);

            TextView timeRight = findViewById(R.id.timeRight);
            int time = activityData.time;
            String formattedTime = formatSecondsIntoDate(time);

            timeRight.setText(formattedTime);
        }
        getSupportActionBar().setTitle("Workout Detail");
    }

    private String formatSecondsIntoDate(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds / 60) % 60;
        seconds = seconds % 60;

        String hourString = (hours < 10)? "0" + Integer.toString(hours) : Integer.toString(hours);
        String minutesString = (minutes < 10)? "0" + Integer.toString(minutes) : Integer.toString(minutes);
        String secondsString = (seconds < 10)? "0" + Integer.toString(seconds) : Integer.toString(seconds);

        String res = hourString + ":" + minutesString + ":" + secondsString;
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.extras, menu);
        return true;
    }

    public void deleteWorkout(MenuItem item) {
        if (measurement == 1 || measurement == 2){
            dbHelper.deleteEvent("Weight", activityId);
        } else{
            dbHelper.deleteEvent("Time", activityId);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moreOptions:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
