package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Andrew on 9/6/2018.
 */

public class EntryDetail extends AppCompatActivity {
    private static final String TAG = "EntryDetail";
    private static String id;
    private static String name;
    private static String date;
    private static String time;
    private static String distance;
    private static String weightedOrTimed;
    private static String simpleBoolean;
    private static String type;
    private static String weight;
    private static String sets;
    private static String reps;
    private static String setsReps;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        name = getIntent().getStringExtra("workoutName");
        String outputName = name.substring(0, 1).toUpperCase() + name.substring(1);
        date = getIntent().getStringExtra("date");
        id = getIntent().getStringExtra("id");
        weightedOrTimed = dbHelper.getData("SELECT Measurement FROM NameTable4 WHERE Name = '" + name + "'");
        simpleBoolean = dbHelper.getData("SELECT Simple FROM NameTable4 WHERE Name = '" + name + "'");
        type = dbHelper.getData("SELECT Type FROM NameTable4 WHERE Name = '" + name + "'");
        if (weightedOrTimed.equals("Weighted")) {
            if (simpleBoolean.equals("False")) {
                setContentView(R.layout.activity_entry_detail_weights);
                TextView workoutTypeDisplayRight = (TextView) findViewById(R.id.workoutTypeDisplayRight);
                workoutTypeDisplayRight.setText(type);
                TextView workoutNameDisplayRight = (TextView) findViewById(R.id.workoutNameDisplayRight);
                workoutNameDisplayRight.setText(outputName);
                TextView dateRight = (TextView) findViewById(R.id.dateRight);
                dateRight.setText(date);
                TextView weightRight = (TextView) findViewById(R.id.weightRight);
                weight = dbHelper.getData("SELECT Weight FROM WeightedTable2 WHERE ID = '" + id + "'");
                weightRight.setText(weight);
                TextView repsSetsRight = (TextView) findViewById(R.id.repsSetsRight);
                sets = dbHelper.getData("SELECT Sets FROM WeightedTable2 WHERE ID = '" + id + "'");
                reps = dbHelper.getData("SELECT Reps FROM WeightedTable2 WHERE ID = '" + id + "'");
                setsReps = sets + " x " + reps;
                repsSetsRight.setText(setsReps);
            } else{
                setContentView(R.layout.activity_entry_detail_weights_simple);
                TextView workoutTypeDisplayRight = (TextView) findViewById(R.id.workoutTypeDisplayRight);
                workoutTypeDisplayRight.setText(type);
                TextView workoutNameDisplayRight = (TextView) findViewById(R.id.workoutNameDisplayRight);
                workoutNameDisplayRight.setText(outputName);
                TextView dateRight = (TextView) findViewById(R.id.dateRight);
                dateRight.setText(date);
                TextView repsRight = (TextView) findViewById(R.id.repsRight);
                sets = dbHelper.getData("SELECT Sets FROM WeightedTable2 WHERE ID = '" + id + "'");
                reps = dbHelper.getData("SELECT Reps FROM WeightedTable2 WHERE ID = '" + id + "'");
                setsReps = sets + " x " + reps;
                repsRight.setText(setsReps);
            }
        } else{
            if (simpleBoolean.equals("False")) {
                setContentView(R.layout.activity_entry_detail_distance);
            } else {
                setContentView(R.layout.activity_entry_detail_simple);
            }
            TextView workoutTypeDisplayRight = (TextView) findViewById(R.id.workoutTypeDisplayRight);
            workoutTypeDisplayRight.setText(type);
            TextView workoutNameDisplayRight = (TextView) findViewById(R.id.workoutNameDisplayRight);
            workoutNameDisplayRight.setText(outputName);
            TextView dateRight = (TextView) findViewById(R.id.dateRight);
            dateRight.setText(date);
            TextView timeRight = (TextView) findViewById(R.id.timeRight);
            time = dbHelper.getData("SELECT Time FROM TimedTable4 WHERE ID = '" + id + "'");
            //edit the time
            String finalTime = "";
            int totalSeconds = Integer.parseInt(time);
            int hours = totalSeconds/3600;
            if (hours == 0){
                finalTime += "00";
            } else if (hours < 10 && hours > 0){
                finalTime += "0";
                finalTime += Integer.toString(hours);
            } else{
                finalTime+= Integer.toString(hours);
            }
            totalSeconds = totalSeconds - (hours * 3600);
            int minutes = totalSeconds/60;
            totalSeconds = totalSeconds - (minutes * 60);
            if (minutes == 0){
                finalTime += "00";
            } else if (minutes < 10 && minutes > 0){
                finalTime += "0";
                finalTime += Integer.toString(minutes);
            } else{
                finalTime+= Integer.toString(minutes);
            }
            int seconds = totalSeconds;
            if (seconds == 0){
                finalTime += "00";
            } else if (seconds < 10 && seconds > 0){
                finalTime += "0";
                finalTime += Integer.toString(seconds);
            } else{
                finalTime += Integer.toString(seconds);
            }
            int tempInt = Integer.parseInt(finalTime);
            finalTime = Integer.toString(tempInt);
            time = finalTime;
            finalTime = "";
            if (time.length() % 2 == 0) {
                for (int j = 0; j < time.length(); j++) {
                    finalTime += time.charAt(j);
                    if (j % 2 == 1 && j > 0 && (j != time.length() - 1)) {
                        finalTime += ':';
                    }
                }
            } else {
                for (int j = 0; j < time.length(); j++) {
                    finalTime += time.charAt(j);
                    if (j % 2 == 0 && (j != time.length() - 1)) {
                        finalTime += ':';
                    }
                }
            }
            timeRight.setText(finalTime);
            if (simpleBoolean.equals("False")) {
                TextView distanceRight = (TextView) findViewById(R.id.distanceRight);
                distance = dbHelper.getData("SELECT distance FROM TimedTable4 WHERE ID = '" + id + "'");
                distanceRight.setText(distance);
            }
        }
        getSupportActionBar().setTitle("Workout Detail");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.extras, menu);
        return true;
    }

    public void deleteWorkout(MenuItem item) {
        if (weightedOrTimed.equals("Weighted")){
            dbHelper.deleteData("1", "Weight", id);
        } else{
            dbHelper.deleteData("1", "Time", id);
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
