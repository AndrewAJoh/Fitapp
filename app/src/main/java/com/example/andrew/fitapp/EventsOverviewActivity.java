package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Andrew on 7/19/2018.
 */

public class EventsOverviewActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private static final String TAG = "NewEntry";
    private ViewPager mViewPager;
    private FragmentListener eventListFragmentListener;
    private FragmentListener calendarFragmentListener;
    private boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String workoutName = getIntent().getStringExtra("workoutName");
        String formattedWorkoutNameoutput = workoutName.substring(0, 1).toUpperCase() + workoutName.substring(1);
        getSupportActionBar().setTitle(formattedWorkoutNameoutput);

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new WorkoutListFragment(), "List");
        adapter.addFragment(new CalendarFragment(), "Calendar");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.extras, menu);
        return true;
    }

    public void deleteWorkout(MenuItem item) {
        String name = getIntent().getStringExtra("workoutName");
        dbHelper.deleteWorkout(name);
        finish();
    }

    public void onButtonAdd(View view){
        String name = getIntent().getStringExtra("workoutName");
        Intent intent = new Intent(view.getContext(), AddEventActivity.class);
        intent.putExtra("workoutName", name);
        startActivityForResult(intent, 0);
    }

    public interface FragmentListener {
        void updateFragmentList();
    }

    public void setEventListFragmentListener(FragmentListener listener){
        this.eventListFragmentListener = listener;
    }

    public void setCalendarFragmentListener(FragmentListener listener){
        this.calendarFragmentListener = listener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                this.eventListFragmentListener.updateFragmentList();
                this.calendarFragmentListener.updateFragmentList();
                boolean added = data.getBooleanExtra("added", false);
                if (!added){
                    Toast toast = Toast.makeText(getApplicationContext(),
                    "Workout already exists for the current day",
                        Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (this.eventListFragmentListener != null){
            this.eventListFragmentListener.updateFragmentList();
        }
        if (this.calendarFragmentListener != null){
            this.calendarFragmentListener.updateFragmentList();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moreOptions:
                Intent i = new Intent(this, AddWorkoutActivity.class);
                i.putExtra("workoutType", getIntent().getStringExtra("workoutType"));
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

