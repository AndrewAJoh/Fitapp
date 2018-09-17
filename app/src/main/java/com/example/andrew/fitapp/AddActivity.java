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

public class AddActivity extends AppCompatActivity {

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
        //result contains the new Workout name
        String result = addText.getText().toString();
        result = result.toLowerCase();
        String newWorkoutType = getIntent().getStringExtra("workoutType");
        boolean resultBoolean = false;

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String simpleBoolean;
        String weightedOrTimed;
        if (radioButton.getText().toString().equals("Reps")){
            simpleBoolean = "True";
            weightedOrTimed = "Weighted";
        } else if (radioButton.getText().toString().equals("Time")){
            simpleBoolean = "True";
            weightedOrTimed = "Timed";
        } else if (radioButton.getText().toString().equals("Time and Distance")){
            simpleBoolean = "False";
            weightedOrTimed = "Timed";
        } else{
            simpleBoolean = "False";
            weightedOrTimed = "Weighted";
        }
        resultBoolean = dbHelper.addData("A", result, newWorkoutType, weightedOrTimed, simpleBoolean, null);

        Intent intent = new Intent();
        intent.putExtra("addedWorkout", result);
        intent.putExtra("added", resultBoolean);
        //Duplicate values are being added to the list regardless, need to set added to false if a pair (name, type) already exists
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelClick(View view){
        finish();
    }

}
