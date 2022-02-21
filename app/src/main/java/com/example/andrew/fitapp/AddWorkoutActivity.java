package com.example.andrew.fitapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Andrew on 7/19/2018.
 */

public class AddWorkoutActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static final String TAG = "AddActivity";
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radioButton3);
    }

    public void onAddClick(View view) {
        EditText addText = findViewById(R.id.submissionText);
        String workoutName = addText.getText().toString().toLowerCase();
        String workoutType = getIntent().getStringExtra("workoutType");

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        int workoutMeasurement;

        if (radioButton.getText().toString().equals("Reps and Weight")){
            workoutMeasurement = 1;
        } else if (radioButton.getText().toString().equals("Reps")){
            workoutMeasurement = 2;
        } else if (radioButton.getText().toString().equals("Time and Distance")){
            workoutMeasurement = 3;
        } else { //Time
            workoutMeasurement = 4;
        }

        WorkoutData w = new WorkoutData(workoutName, workoutType, workoutMeasurement);

        boolean addWorkoutDataResult = dbHelper.addWorkoutData(w);

        Intent intent = new Intent();
        intent.putExtra("addedWorkout", workoutName);
        intent.putExtra("added", addWorkoutDataResult);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelClick(View view){
        finish();
    }

}
