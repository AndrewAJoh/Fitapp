package com.example.andrew.fitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.fitapp.ComplexAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andrew on 8/14/2018.
 */

public class RecentWorkoutsFragment extends Fragment implements EventsOverviewActivity.FragmentListener{
    private static final String TAG = "RecentWorkouts";
    private List<DataSet> recentList;
    private ComplexAdapter adapter;
    private static int workoutMeasurement;
    private static String workoutName;
    View view;
    DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Log.d(TAG, "OnCreateView: Started");
        view = inflater.inflate(R.layout.recent_workout_tab, container, false);
        dbHelper = new DatabaseHelper(getContext());

        workoutName = getActivity().getIntent().getStringExtra("workoutName").toLowerCase();

        workoutName = getActivity().getIntent().getStringExtra("workoutName").toLowerCase();

        String query = "SELECT Measurement FROM " + DatabaseHelper.ACTIVITY_TABLE_TITLE + " WHERE Name = '" + workoutName + "'";
        WorkoutData workoutData = dbHelper.getWorkoutData(query);
        workoutMeasurement = workoutData.measurement;

        if (workoutMeasurement == 1){
            initData(null);
        } else if (workoutMeasurement == 2){
            initData(null);
        } else if (workoutMeasurement == 3) {
            initData("Distance");
        } else {
            initData(null);
        }

        initRecyclerView();
        ((EventsOverviewActivity) getActivity()).setFragmentListener1(this);
        return view;
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

    private void initData(String measurement) {
        List<DataSet> topList = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
        List<ActivityData> data = dbHelper.getActivityDataByName(workoutName);

        if (workoutMeasurement == 4) { //Timed simple
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records
                    String time = formatSecondsIntoDate(data.get(i).time);
                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    DataSet dataset = new DataSet(String.valueOf(i + 1), time, date, id);
                    System.out.println("added " + dataset.getRawData() + " to the list");
                    topList.add(dataset);
                }
            } else {
                DataSet noItems = new DataSet("", "No data available", "", "");
                topList.add(noItems);
            }
        } else if (workoutMeasurement == 3) {
            if (measurement.equals("Distance")){
                Collections.sort(data, (object1, object2) -> object1.distance.compareTo(object2.distance));
                if (!data.isEmpty()) {
                    for (int i = 0; i < data.size(); i++) { //Loop through records
                        String time = formatSecondsIntoDate(data.get(i).time);
                        String date = data.get(i).date;
                        String id = String.valueOf(data.get(i).id);

                        DataSet dataset = new DataSet(String.valueOf(i + 1), time, date, id);
                        System.out.println("added " + dataset.getRawData() + " to the list");
                        topList.add(dataset);
                    }
                } else {
                    DataSet noItems = new DataSet("", "No data available", "", "");
                    topList.add(noItems);
                }
            } else if (measurement.equals("Time")){
                Collections.sort(data, (object1, object2) -> object1.time.compareTo(object2.time));

                if (!data.isEmpty()) {
                    for (int i = 0; i < data.size(); i++) { //Loop through records
                        String time = formatSecondsIntoDate(data.get(i).time);
                        String date = data.get(i).date;
                        String id = String.valueOf(data.get(i).id);

                        DataSet dataset = new DataSet(String.valueOf(i + 1), time, date, id);
                        System.out.println("added " + dataset.getRawData() + " to the list");
                        topList.add(dataset);
                    }
                } else {
                    DataSet noItems = new DataSet("", "No data available", "", "");
                    topList.add(noItems);
                }
            } else if (measurement.equals("Pace")){
                //data = dbHelper.getData("SELECT Time, Distance, Date, (Distance/Time) AS Pace, ID FROM TimedTable4 WHERE Name = '" + name + "' ORDER BY PACE DESC");
                Collections.sort(data, (object1, object2) -> object1.time.compareTo(object2.time)); //TODO: Fix this

                if (!data.isEmpty()) {
                    for (int i = 0; i < data.size(); i++) { //Loop through records
                        String time = formatSecondsIntoDate(data.get(i).time);
                        String date = data.get(i).date;
                        String id = String.valueOf(data.get(i).id);

                        DataSet dataset = new DataSet(String.valueOf(i + 1), time, date, id);
                        System.out.println("added " + dataset.getRawData() + " to the list");
                        topList.add(dataset);
                    }
                } else {
                    DataSet noItems = new DataSet("", "No data available", "", "");
                    topList.add(noItems);
                }
            }
        } else if (workoutMeasurement == 1){
            //data = dbHelper.getData("SELECT Weight, Sets, Reps, Date, ID, (Weight * Sets * Reps) AS Total FROM WeightedTable2 WHERE Name = '" + name + "' ORDER BY Total DESC");

            Collections.sort(data, (object1, object2) -> object1.weight.compareTo(object2.weight)); //TODO: Fix this

            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records
                    String time = formatSecondsIntoDate(data.get(i).time);
                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    DataSet dataset = new DataSet(String.valueOf(i + 1), time, date, id);
                    System.out.println("added " + dataset.getRawData() + " to the list");
                    topList.add(dataset);
                }
            } else {
                DataSet noItems = new DataSet("", "No data available", "", "");
                topList.add(noItems);
            }
        } else if (workoutMeasurement == 2){
            //data = dbHelper.getData("SELECT Sets, Reps, Date, ID, (Reps * Sets) AS Total FROM WeightedTable2 WHERE Name = '" + name + "' ORDER BY Total DESC");

            Collections.sort(data, (object1, object2) -> object1.sets.compareTo(object2.sets)); //TODO: Fix this

            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records
                    String time = formatSecondsIntoDate(data.get(i).time);
                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    DataSet dataset = new DataSet(String.valueOf(i + 1), time, date, id);
                    System.out.println("added " + dataset.getRawData() + " to the list");
                    topList.add(dataset);
                }
            } else {
                DataSet noItems = new DataSet("", "No data available", "", "");
                topList.add(noItems);
            }
        }
    }

    public void updateFragmentList(String measurement){
        initData(measurement);
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recent workouts");
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_recent);
        String name = getActivity().getIntent().getStringExtra("workoutName");
        adapter = new ComplexAdapter(getActivity(), recentList, name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

}
