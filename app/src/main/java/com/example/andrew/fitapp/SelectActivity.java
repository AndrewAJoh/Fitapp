package com.example.andrew.fitapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 7/17/2018.
 */

public class SelectActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static final String TAG = "SelectActivity";
    public String workoutType;
    private boolean created = false;
    Adapter adapter;

    private List<String> workoutTypeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_screen);
        workoutType = getIntent().getStringExtra("workoutType");
        initData();

        initRecyclerView();
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

    private void initData() {
        List<WorkoutData> workoutData = dbHelper.getWorkoutsByType(workoutType);

        for (int i = 0; i < workoutData.size(); i++){
            workoutTypeList.add(workoutData.get(i).name);
        }
    }


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
                workoutType = getIntent().getStringExtra("workoutType");
                if (added) {
                    workoutTypeList.add(addedWorkout);
                } else{
                    Toast.makeText(this, "Workout already exists", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        String logMessage = "initRecyclerView: init " + workoutType + " recyclerView";
        Log.d(TAG, logMessage);
        adapter = new Adapter(this, workoutTypeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setTitle(workoutType);
    }


}
