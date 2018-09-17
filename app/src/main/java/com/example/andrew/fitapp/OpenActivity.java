package com.example.andrew.fitapp;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 7/17/2018.
 */

public class OpenActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static final String TAG = "OpenActivity";
    public String buttonName;
    private boolean created = false;
    Adapter adapter;

    private List<String> cardioTypeList = new ArrayList<String>();
    private List<String> bicepsTypeList = new ArrayList<String>();
    private List<String> chestTypeList = new ArrayList<String>();
    private List<String> legsTypeList = new ArrayList<String>();
    private List<String> absTypeList = new ArrayList<String>();
    private List<String> tricepsTypeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_screen);
        initData(getIntent().getStringExtra("workoutType"));
        initRecyclerView();
        dbHelper.getData("SELECT * FROM TimedTable4 WHERE Name = 'running'");
        dbHelper.getData("SELECT * FROM WeightedTable2 WHERE Name = 'concentration curl'");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_workout, menu);
        return true;
    }

    //Handle menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addType:
                Intent i = new Intent(this, AddActivity.class);
                i.putExtra("workoutType", getIntent().getStringExtra("workoutType"));
                this.startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initData(String workoutType) {
        //take data from sql database and add it to list (so I don't have to change the recyclerview, too much work)
        String data;
        String [] arrOfStr;
        if (workoutType.equals("Cardio")) {
            data = dbHelper.getData("SELECT NAME FROM NameTable4 WHERE Type = 'Cardio'");
            arrOfStr = data.split(",");
            for (int i = 0; i < arrOfStr.length; i++) {
                System.out.println(arrOfStr[i]);
                cardioTypeList.add(arrOfStr[i]);
            }
        } else if (workoutType.equals("Biceps")) {
            data = dbHelper.getData("SELECT NAME FROM NameTable4 WHERE Type = 'Biceps'");
            arrOfStr = data.split(",");
            for (int i = 0; i < arrOfStr.length; i++) {
                System.out.println(arrOfStr[i]);
                bicepsTypeList.add(arrOfStr[i]);
            }
        } else if (workoutType.equals("Chest")) {
            data = dbHelper.getData("SELECT NAME FROM NameTable4 WHERE Type = 'Chest'");
            arrOfStr = data.split(",");
            for (int i = 0; i < arrOfStr.length; i++) {
                System.out.println(arrOfStr[i]);
                chestTypeList.add(arrOfStr[i]);
            }
        } else if (workoutType.equals("Legs")) {
            data = dbHelper.getData("SELECT NAME FROM NameTable4 WHERE Type = 'Legs'");
            arrOfStr = data.split(",");
            for (int i = 0; i < arrOfStr.length; i++) {
                System.out.println(arrOfStr[i]);
                legsTypeList.add(arrOfStr[i]);
            }
        } else if (workoutType.equals("Abs")) {
            data = dbHelper.getData("SELECT NAME FROM NameTable4 WHERE Type = 'Abs'");
            arrOfStr = data.split(",");
            for (int i = 0; i < arrOfStr.length; i++) {
                System.out.println(arrOfStr[i]);
                absTypeList.add(arrOfStr[i]);
            }
        } else if (workoutType.equals("Triceps")) {
            data = dbHelper.getData("SELECT NAME FROM NameTable4 WHERE Type = 'Triceps'");
            arrOfStr = data.split(",");
            for (int i = 0; i < arrOfStr.length; i++) {
                System.out.println(arrOfStr[i]);
                tricepsTypeList.add(arrOfStr[i]);
            }
        }

    }

    //Used when returning from a deleted workout to refresh the page
    @Override
    public void onResume(){
        super.onResume();
        if (created) {
            startActivity(getIntent());
            finish();
        }
        created = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check that it is the SecondActivity with an OK result
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // get String data from Intent
                String addedWorkout = data.getStringExtra("addedWorkout");
                boolean added = data.getBooleanExtra("added", false);
                buttonName = getIntent().getStringExtra("workoutType");
                if (added) {
                    System.out.println("ADDING TO THE RECYCLERVIEW");
                    if (buttonName.equals("Cardio")) {
                        cardioTypeList.add(addedWorkout);
                    } else if (buttonName.equals("Biceps")){
                        bicepsTypeList.add(addedWorkout);
                    }else if (buttonName.equals("Chest")){
                        chestTypeList.add(addedWorkout);
                    }else if (buttonName.equals("Legs")){
                        legsTypeList.add(addedWorkout);
                    }else if (buttonName.equals("Abs")){
                        absTypeList.add(addedWorkout);
                    }else if (buttonName.equals("Triceps")){
                        tricepsTypeList.add(addedWorkout);
                    }
                } else{
                    Toast.makeText(this, "Workout already exists", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void initRecyclerView(){
        buttonName = getIntent().getStringExtra("workoutType");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        if (buttonName.equals("Cardio")) {
            Log.d(TAG, "initRecyclerView: init cardio recyclerview");
            getSupportActionBar().setTitle("Cardio");
            //Adapter cardioAdapter = new Adapter(this, cardioTypeList);
            adapter = new Adapter(this, cardioTypeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);


        } else if (buttonName.equals("Biceps")) {
            Log.d(TAG, "initRecyclerView: init bicep recyclerview");
            getSupportActionBar().setTitle("Biceps");
            //Adapter bicepAdapter = new Adapter(this, bicepsTypeList);
            adapter = new Adapter(this, bicepsTypeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
        else if (buttonName.equals("Chest")) {
            Log.d(TAG, "initRecyclerView: init chest recyclerview");
            getSupportActionBar().setTitle("Chest");
            //Adapter chestAdapter = new Adapter(this, chestTypeList);
            adapter = new Adapter(this, chestTypeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
        else if (buttonName.equals("Legs")) {
            Log.d(TAG, "initRecyclerView: init legs recyclerview");
            getSupportActionBar().setTitle("Legs");
            // Adapter legsAdapter = new Adapter(this, legsTypeList);
            adapter = new Adapter(this, legsTypeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
        else if (buttonName.equals("Abs")) {
            Log.d(TAG, "initRecyclerView: init abs recyclerview");
            getSupportActionBar().setTitle("Abs");
            //Adapter absAdapter = new Adapter(this, absTypeList);
            adapter = new Adapter(this, absTypeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
        else if (buttonName.equals("Triceps")) {
            Log.d(TAG, "initRecyclerView: init tricep recyclerview");
            getSupportActionBar().setTitle("Triceps");
            //Adapter tricepsAdapter = new Adapter(this, tricepsTypeList);
            adapter = new Adapter(this, tricepsTypeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }



    }


}
