package com.example.andrew.fitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Andrew on 9/6/2018.
 */

public class EventDetailActivity extends AppCompatActivity {
    private static final String TAG = "EventDetailActivity";
    DatabaseHelper dbHelper;
    private int measurement;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);

        String workoutName = getIntent().getStringExtra("workoutName");
        String date = getIntent().getStringExtra("date");
        eventId = getIntent().getStringExtra("id");
        Log.d(TAG, "EVENT ID IS");
        Log.d(TAG, eventId);

        WorkoutData workoutData = dbHelper.getWorkoutDataByName(workoutName);
        measurement = workoutData.measurement;

        EventData eventData = dbHelper.getEventDataById(eventId);

        TextView workoutNameDisplayRight;
        TextView workoutTypeDisplayRight;

        if (measurement == 1) { //Reps, sets, weight
            setContentView(R.layout.activity_entry_detail_weights);

            workoutTypeDisplayRight = findViewById(R.id.workoutTypeDisplayRight);

            String weight = eventData.weight;
            String setsReps = eventData.sets + " x " + eventData.reps;

            workoutTypeDisplayRight.setText("Weight and Reps");

            TextView dateRight = findViewById(R.id.dateRight);
            dateRight.setText(date);

            TextView weightRight = findViewById(R.id.weightRight);
            weightRight.setText(weight);

            TextView repsSetsRight = findViewById(R.id.repsSetsRight);

            repsSetsRight.setText(setsReps);
        } else if (measurement == 2){ //Reps, sets
            setContentView(R.layout.activity_entry_detail_weights_simple);

            workoutTypeDisplayRight = findViewById(R.id.workoutTypeDisplayRight);

            String setsReps = eventData.sets + " x " + eventData.reps;

            workoutTypeDisplayRight.setText("Reps");

            TextView dateRight = findViewById(R.id.dateRight);
            dateRight.setText(date);

            TextView repsRight = findViewById(R.id.repsRight);
            repsRight.setText(setsReps);
        } else {
            if (measurement == 3) { //Time and Distance
                setContentView(R.layout.activity_entry_detail_distance);
                workoutTypeDisplayRight = findViewById(R.id.workoutTypeDisplayRight);

                workoutTypeDisplayRight.setText("Time and Distance");
                TextView distanceRight = findViewById(R.id.distanceRight);
                String distance = eventData.distance;
                distanceRight.setText(distance);
            } else { //Time
                setContentView(R.layout.activity_entry_detail_simple);
                workoutTypeDisplayRight = findViewById(R.id.workoutTypeDisplayRight);

                workoutTypeDisplayRight.setText("Time");
            }

            TextView dateRight = findViewById(R.id.dateRight);
            dateRight.setText(date);

            TextView timeRight = findViewById(R.id.timeRight);
            int time = eventData.time;
            String formattedTime = formatSecondsIntoDate(time);

            timeRight.setText(formattedTime);
        }

        workoutNameDisplayRight = findViewById(R.id.workoutNameDisplayRight);
        String formattedWorkoutName = workoutName.substring(0, 1).toUpperCase() + workoutName.substring(1);
        workoutNameDisplayRight.setText(formattedWorkoutName);

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
        dbHelper.deleteEvent(eventId);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
