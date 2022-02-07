package com.example.andrew.fitapp;

import android.os.Bundle;
import android.service.autofill.Dataset;
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

        WorkoutData workoutData = dbHelper.getWorkoutDataByName(workoutName);
        workoutMeasurement = workoutData.measurement;

        initRecyclerView();
        ((EventsOverviewActivity) getActivity()).setFragmentListener1(this);
        return view;
    }

    private void initData() {

    }

    public void updateFragmentList(String measurement){
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recent workouts");
    }

}
