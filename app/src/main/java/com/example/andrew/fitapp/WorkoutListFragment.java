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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 8/14/2018.
 */

public class WorkoutListFragment extends Fragment implements EventsOverviewActivity.FragmentListener{
    private static final String TAG = "WorkoutList";
    private List<EventItem> activityList;
    private ComplexAdapter adapter;
    private static int workoutMeasurement;
    private String workoutName;
    private WorkoutData workoutData;
    View view;
    DatabaseHelper dbHelper;
    Spinner metricSpinner;
    Spinner orderSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Log.d(TAG, "OnCreateView: Started");
        view = inflater.inflate(R.layout.list_tab, container, false);

        dbHelper = new DatabaseHelper(getContext());
        metricSpinner = view.findViewById(R.id.metricSpinner);
        orderSpinner = view.findViewById(R.id.orderSpinner);
        workoutName = getActivity().getIntent().getStringExtra("workoutName").toLowerCase();

        ArrayAdapter<CharSequence> arrayAdapter;

        //Determine what kind of spinner is needed based on the measurement type

        workoutData = dbHelper.getWorkoutDataByName(workoutName);
        int measurement = workoutData.measurement;

        if (measurement == 1) {
            arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.WeightedMeasurements, android.R.layout.simple_spinner_dropdown_item);
        } else if (measurement == 2){
            arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.SimpleWeightedMeasurements, android.R.layout.simple_spinner_dropdown_item);
        } else if (measurement == 3) {
            arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.TimeMeasurements, android.R.layout.simple_spinner_dropdown_item);
        } else {
            arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.SimpleTimeMeasurements, android.R.layout.simple_spinner_dropdown_item);
        }

        metricSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<CharSequence> orderAdapter;

        orderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ActivityOrder, android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderAdapter);

        metricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //New spinner value has been selected - initialize data again
                updateFragmentList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //New spinner value has been selected - initialize data again
                updateFragmentList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        WorkoutData workoutData = dbHelper.getWorkoutDataByName(workoutName);
        workoutMeasurement = workoutData.measurement;

        //Set initial data
        if (workoutMeasurement == 1){
            initData(null, "Recent");
        } else if (workoutMeasurement == 2){
            initData(null, "Recent");
        } else if (workoutMeasurement == 3) {
            initData("Distance", "Recent");
        } else {
            initData(null, "Recent");
        }

        initRecyclerView();
        ((EventsOverviewActivity) getActivity()).setEventListFragmentListener(this);

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

    private void initData(String metric, String order) {
        activityList = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
        List<EventData> data = dbHelper.getEventDataByName(workoutName, metric, order);

        if (workoutMeasurement == 4) { //Time only - sort by time descending
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records
                    String time = formatSecondsIntoDate(data.get(i).time);
                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    EventItem dataset = new EventItem(String.valueOf(i + 1), time, date, id);
                    System.out.println("added " + dataset.data + " to the list");
                    activityList.add(dataset);
                }
            }
        } else if (workoutMeasurement == 3) { //Time with distance - sort by distance, total time or pace descending
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records

                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    String value;
                    if (metric.equals("Time")){
                        value = formatSecondsIntoDate(data.get(i).time);
                    } else if (metric.equals("Distance")){
                        DecimalFormat df = new DecimalFormat("0.00");
                        String unitsString;
                        if (MainActivity.useUS){
                            unitsString = " mi";
                        } else{
                            unitsString = " km";
                        }
                        value = String.valueOf(df.format(Float.valueOf(data.get(i).distance))) + unitsString;
                    } else { //Pace
                        DecimalFormat df = new DecimalFormat("0.00");
                        String unitsString;
                        if (MainActivity.useUS){
                            unitsString = " mi/h";
                        } else{
                            unitsString = " km/h";
                        }
                        value = String.valueOf(df.format((Float.valueOf(data.get(i).distance)/data.get(i).time) * 3600)) + unitsString;
                    }
                    EventItem dataset = new EventItem(String.valueOf(i + 1), value, date, id);

                    System.out.println("added " + dataset.data + " to the list");
                    activityList.add(dataset);
                }
            }
        } else if (workoutMeasurement == 1){ //Weight, sets, reps
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records
                    String value = data.get(i).weight + " lbs " + data.get(i).sets + " x " + data.get(i).reps;
                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    EventItem dataset = new EventItem(String.valueOf(i + 1), value, date, id);
                    System.out.println("added " + dataset.data + " to the list");
                    activityList.add(dataset);
                }
            }
        } else if (workoutMeasurement == 2){ //Sets and Reps only - no weight
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) { //Loop through records
                    String value = data.get(i).sets + " x " + data.get(i).reps;
                    String date = data.get(i).date;
                    String id = String.valueOf(data.get(i).id);

                    EventItem dataset = new EventItem(String.valueOf(i + 1), value, date, id);
                    System.out.println("added " + dataset.data + " to the list");
                    activityList.add(dataset);
                }
            }
        }
    }

    public void updateFragmentList(){
        initData(metricSpinner.getSelectedItem().toString(), orderSpinner.getSelectedItem().toString());
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init top workouts");
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_top);
        String name = getActivity().getIntent().getStringExtra("workoutName");
        adapter = new ComplexAdapter(getActivity(), activityList, name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


}
