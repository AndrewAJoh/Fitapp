package com.example.andrew.fitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewDataEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
    }

    public void onButtonClick(View view){
        //Distance
        EditText distanceText = (EditText) findViewById(R.id.distanceText);
        String distanceString = distanceText.getText().toString();
        EditText timeText = (EditText) findViewById(R.id.timeText);
        String timeString = timeText.getText().toString();


        // put the String to pass back into an Intent and close this activity
        Intent intent = new Intent();
        intent.putExtra("distance", distanceString);
        intent.putExtra("time", timeString);
        setResult(RESULT_OK, intent);
        finish();
    }


}
