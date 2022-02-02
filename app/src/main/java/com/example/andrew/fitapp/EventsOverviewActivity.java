package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Andrew on 7/19/2018.
 */

public class EventsOverviewActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static final String TAG = "NewEntry";
    private ViewPager mViewPager;
    private FragmentListener fragmentListener1;
    private FragmentListener fragmentListener2;
    private static WorkoutData workoutData;
    private boolean created = false;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name = getIntent().getStringExtra("workoutName");
        String output = name.substring(0, 1).toUpperCase() + name.substring(1);
        getSupportActionBar().setTitle(output);

        //Determine what kind of spinner is needed based on the measurement type
        workoutData = dbHelper.getWorkoutDataByName(name);

        ArrayAdapter<CharSequence> adapter = null;
        spinner = findViewById(R.id.spinner);

        int measurement = workoutData.measurement;

        if (measurement == 3) {
            adapter = ArrayAdapter.createFromResource(this, R.array.WeightedMeasurements, android.R.layout.simple_spinner_item);
        } else if (measurement == 4){
            adapter = ArrayAdapter.createFromResource(this, R.array.SimpleWeightedMeasurements, android.R.layout.simple_spinner_item);
        } else if (measurement == 1) {
            adapter = ArrayAdapter.createFromResource(this, R.array.TimeMeasurements, android.R.layout.simple_spinner_item);
        } else {
            adapter = ArrayAdapter.createFromResource(this, R.array.SimpleTimeMeasurements, android.R.layout.simple_spinner_item);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopWorkoutsFragment(), "Top");
        adapter.addFragment(new RecentWorkoutsFragment(), "Recent");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.extras, menu);
        return true;
    }

    public void deleteWorkout(MenuItem item) {
        String name = getIntent().getStringExtra("workoutName");
        dbHelper.deleteActivity("Weight", name); //TODO: Fix this
        finish();
    }

    public void onButtonAdd(View view){
        String name = getIntent().getStringExtra("workoutName");
        Intent intent = new Intent(view.getContext(), AddEventActivity.class);
        intent.putExtra("workoutName", name);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();
        sendDataToFragment(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface FragmentListener {
        void updateFragmentList(String measurement);
    }

    public void setFragmentListener1(FragmentListener listener){
        this.fragmentListener1 = listener;
    }

    public void setFragmentListener2(FragmentListener listener){
        this.fragmentListener2 = listener;
    }

    public void sendDataToFragment(String measurement){
        this.fragmentListener1.updateFragmentList(measurement);
        this.fragmentListener2.updateFragmentList(measurement);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check that it is the SecondActivity with an OK result
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                // get String data from Intent
                String text = spinner.getSelectedItem().toString();
                sendDataToFragment(text);
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moreOptions:
                Intent i = new Intent(this, AddActivity.class);
                i.putExtra("workoutType", getIntent().getStringExtra("workoutType"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
