package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 8/14/2018.
 */

public class TopWorkoutsFragment extends Fragment implements EventsOverviewActivity.FragmentListener{
    private static final String TAG = "TopWorkouts";
    private List<DataSet> topList;
    private ComplexAdapter adapter;
    private static String weightedOrTimed;
    private static String simpleBoolean;
    private static String name;
    View view;
    DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.top_workout_tab, container, false);
        Log.d(TAG, "OnCreateView: Started");
        name = getActivity().getIntent().getStringExtra("workoutName");
        name = name.toLowerCase();
        dbHelper = new DatabaseHelper(getContext());
        weightedOrTimed = dbHelper.getData("SELECT Measurement FROM NameTable4 WHERE Name = '" + name + "'");
        simpleBoolean = dbHelper.getData("SELECT Simple FROM NameTable4 WHERE Name = '" + name + "'");
        if (weightedOrTimed.equals("Weighted")) {
            if (simpleBoolean.equals("False")) {
                initData("Weighted", null);
            } else{
                initData("WeightedSimple", null);
            }
        } else{
            if (simpleBoolean.equals("False")){
                initData("Timed", "Distance");
            } else if (simpleBoolean.equals("True")){
                initData("TimedSimple", null);
            }
        }
        initRecyclerView();
        ((EventsOverviewActivity) getActivity()).setFragmentListener2(this);
        return view;


    }

    private void initData(String type, String measurement) {
        String date;
        String newDate = "";
        int k = 1;
        topList = new ArrayList<DataSet>();
        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
        String data;
        String [] arrOfStr;
        if (type.equals("TimedSimple")) {
            data = dbHelper.getData("SELECT Time, Date, ID FROM TimedTable4 WHERE Name = '" + name + "' ORDER BY Time DESC");
            arrOfStr = data.split(",");
            String newString;
            String resultString;
            if (!arrOfStr[0].equals("")) {
                topList = new ArrayList<DataSet>();
                for (int i = 0; i < arrOfStr.length; i += 3) {
                    newString = arrOfStr[i];
                    resultString = "";
                    int totalSeconds = Integer.parseInt(newString);
                    int hours = totalSeconds/3600;
                    if (hours == 0){
                        resultString += "00";
                    } else if (hours < 10 && hours > 0){
                        resultString += "0";
                        resultString += Integer.toString(hours);
                    } else{
                        resultString+= Integer.toString(hours);
                    }
                    totalSeconds = totalSeconds - (hours * 3600);
                    int minutes = totalSeconds/60;
                    totalSeconds = totalSeconds - (minutes * 60);
                    if (minutes == 0){
                        resultString += "00";
                    } else if (minutes < 10 && minutes > 0){
                        resultString += "0";
                        resultString += Integer.toString(minutes);
                    } else{
                        resultString+= Integer.toString(minutes);
                    }
                    int seconds = totalSeconds;
                    if (seconds == 0){
                        resultString += "00";
                    } else if (seconds < 10 && seconds > 0){
                        resultString += "0";
                        resultString += Integer.toString(seconds);
                    } else{
                        resultString += Integer.toString(seconds);
                    }
                    int tempInt = Integer.parseInt(resultString);
                    resultString = Integer.toString(tempInt);
                    newString = resultString;
                    resultString = "";
                    if (newString.length() % 2 == 0) {
                        for (int j = 0; j < newString.length(); j++) {
                            resultString += newString.charAt(j);
                            if (j % 2 == 1 && j > 0 && (j != newString.length() - 1)) {
                                resultString += ':';
                            }
                        }
                    } else {
                        for (int j = 0; j < newString.length(); j++) {
                            resultString += newString.charAt(j);
                            if (j % 2 == 0 && (j != newString.length() - 1)) {
                                resultString += ':';
                            }
                        }
                    }
                    date = arrOfStr[i + 1];
                    newDate = "";
                    if (date.charAt(5) == '0') {
                        newDate += date.charAt(6);
                    } else {
                        newDate += date.charAt(5);
                        newDate += date.charAt(6);
                    }
                    newDate += "/";
                    if (date.charAt(8) == '0') {
                        newDate += date.charAt(9);
                    } else {
                        newDate += date.charAt(8);
                        newDate += date.charAt(9);
                    }
                    newDate += "/";
                    newDate += date.charAt(2);
                    newDate += date.charAt(3);
                    String id = arrOfStr[i+2];
                    DataSet dataset = new DataSet(Integer.toString(k), resultString, newDate, id);
                    k++;
                    System.out.println("added " + dataset.getRawData() + " to the list");
                    topList.add(dataset);
                }
            } else {
                DataSet noItems = new DataSet("", "No data available", "", "");
                topList.add(noItems);
            }
        } else if (type.equals("Timed")) {
            if (measurement.equals("Distance")){
                data = dbHelper.getData("SELECT Distance, Date, ID FROM TimedTable4 WHERE Name = '" + name + "' ORDER BY Distance DESC");
                arrOfStr = data.split(",");
                if (!arrOfStr[0].equals("")){
                    topList = new ArrayList<DataSet>();
                    for (int i = 0; i < arrOfStr.length; i+=3){
                        date = arrOfStr[i + 1];
                        newDate = "";
                        if (date.charAt(5) == '0') {
                            newDate += date.charAt(6);
                        } else {
                            newDate += date.charAt(5);
                            newDate += date.charAt(6);
                        }
                        newDate += "/";
                        if (date.charAt(8) == '0') {
                            newDate += date.charAt(9);
                        } else {
                            newDate += date.charAt(8);
                            newDate += date.charAt(9);
                        }
                        newDate += "/";
                        newDate += date.charAt(2);
                        newDate += date.charAt(3);
                        String id = arrOfStr[i+2];
                        DataSet dataset = new DataSet(Integer.toString(k), arrOfStr[i], newDate, id);
                        k++;
                        topList.add(dataset);
                    }
                } else{
                    DataSet noItems = new DataSet("", "No data available", "", "");
                    topList.add(noItems);
                }

            } else if (measurement.equals("Time")){
                data = dbHelper.getData("SELECT Time, Date, ID FROM TimedTable4 WHERE Name = '" + name + "' ORDER BY Time DESC");
                arrOfStr = data.split(",");
                String newString;
                String resultString;
                if (!arrOfStr[0].equals("")) {
                    topList = new ArrayList<DataSet>();
                    for (int i = 0; i < arrOfStr.length; i += 3) {
                        newString = arrOfStr[i];
                        resultString = "";
                        int totalSeconds = Integer.parseInt(newString);
                        int hours = totalSeconds/3600;
                        if (hours == 0){
                            resultString += "00";
                        } else if (hours < 10 && hours > 0){
                            resultString += "0";
                            resultString += Integer.toString(hours);
                        } else{
                            resultString+= Integer.toString(hours);
                        }
                        totalSeconds = totalSeconds - (hours * 3600);
                        int minutes = totalSeconds/60;
                        totalSeconds = totalSeconds - (minutes * 60);
                        if (minutes == 0){
                            resultString += "00";
                        } else if (minutes < 10 && minutes > 0){
                            resultString += "0";
                            resultString += Integer.toString(minutes);
                        } else{
                            resultString+= Integer.toString(minutes);
                        }
                        int seconds = totalSeconds;
                        if (seconds == 0){
                            resultString += "00";
                        } else if (seconds < 10 && seconds > 0){
                            resultString += "0";
                            resultString += Integer.toString(seconds);
                        } else{
                            resultString += Integer.toString(seconds);
                        }
                        int tempInt = Integer.parseInt(resultString);
                        resultString = Integer.toString(tempInt);
                        newString = resultString;
                        resultString = "";
                        if (newString.length() % 2 == 0) {
                            for (int j = 0; j < newString.length(); j++) {
                                resultString += newString.charAt(j);
                                if (j % 2 == 1 && j > 0 && (j != newString.length() - 1)) {
                                    resultString += ':';
                                }
                            }
                        } else {
                            for (int j = 0; j < newString.length(); j++) {
                                resultString += newString.charAt(j);
                                if (j % 2 == 0 && (j != newString.length() - 1)) {
                                    resultString += ':';
                                }
                            }
                        }
                        date = arrOfStr[i + 1];
                        newDate = "";
                        if (date.charAt(5) == '0') {
                            newDate += date.charAt(6);
                        } else {
                            newDate += date.charAt(5);
                            newDate += date.charAt(6);
                        }
                        newDate += "/";
                        if (date.charAt(8) == '0') {
                            newDate += date.charAt(9);
                        } else {
                            newDate += date.charAt(8);
                            newDate += date.charAt(9);
                        }
                        newDate += "/";
                        newDate += date.charAt(2);
                        newDate += date.charAt(3);
                        String id = arrOfStr[i+2];
                        DataSet dataset = new DataSet(Integer.toString(k), resultString, newDate, id);
                        k++;
                        System.out.println("added " + dataset.getRawData() + " to the list");
                        topList.add(dataset);
                    }
                } else {
                    DataSet noItems = new DataSet("", "No data available", "", "");
                    topList.add(noItems);
                }
            } else if (measurement.equals("Pace")){
                data = dbHelper.getData("SELECT Time, Distance, Date, (Distance/Time) AS Pace, ID FROM TimedTable4 WHERE Name = '" + name + "' ORDER BY PACE DESC");
                arrOfStr = data.split(",");
                String newString;
                String resultString = "";
                double distanceDouble;
                String distanceString = "";
                int seconds = 0;
                double paceDouble;
                int paceInt;
                String id = "";
                String paceString;
                int perMile;
                String additionalSeconds;
                String perMileString = "";
                if (!arrOfStr[0].equals("")) {
                    topList = new ArrayList<DataSet>();
                    for (int i = 0; i < arrOfStr.length; i += 1) {
                        newString = arrOfStr[i];
                        if (i % 5 == 0) {
                            resultString = "";
                            seconds = Integer.parseInt(newString);
                        }
                        if (i % 5 == 1) {
                            distanceString = arrOfStr[i];
                            distanceDouble = Double.parseDouble(distanceString);
                            paceDouble = seconds / distanceDouble;
                            paceString = Double.toString(paceDouble);
                            String[] paceSplit = paceString.split("\\.");
                            paceString = paceSplit[0];
                            paceInt = Integer.parseInt(paceString);
                            perMile = paceInt / 60;
                            perMileString = Integer.toString(perMile);
                            perMileString += ":";
                            additionalSeconds = Integer.toString(paceInt % 60);
                            if (additionalSeconds.equals("0")) {
                                perMileString += "00";
                            } else if (additionalSeconds.length() == 1) {
                                perMileString += "0";
                                perMileString += additionalSeconds;
                            } else {
                                perMileString += additionalSeconds;
                            }
                            perMileString += " for " + distanceString + " mi";
                        }
                        if (i % 5 == 2) {
                            date = arrOfStr[i];
                            newDate = "";
                            if (date.charAt(5) == '0') {
                                newDate += date.charAt(6);
                            } else {
                                newDate += date.charAt(5);
                                newDate += date.charAt(6);
                            }
                            newDate += "/";
                            if (date.charAt(8) == '0') {
                                newDate += date.charAt(9);
                            } else {
                                newDate += date.charAt(8);
                                newDate += date.charAt(9);
                            }
                            newDate += "/";
                            newDate += date.charAt(2);
                            newDate += date.charAt(3);

                        }
                        if (i % 5 == 4){
                            id = arrOfStr[i];
                            DataSet dataset = new DataSet(Integer.toString(k), perMileString, newDate, id);
                            k++;
                            perMileString = "";
                            System.out.println("added " + dataset.getRawData() + " to the list");
                            topList.add(dataset);
                            seconds = 0;
                        }
                    }
                } else {
                    DataSet noItems = new DataSet("", "No data available", "", "");
                    topList.add(noItems);
                }
            }

        } else if (type.equals("Weighted")){
            data = dbHelper.getData("SELECT Weight, Sets, Reps, Date, ID, (Weight * Sets * Reps) AS Total FROM WeightedTable2 WHERE Name = '" + name + "' ORDER BY Total DESC");
            arrOfStr = data.split(",");
            String newString = "";
            String id = "";
            if (!arrOfStr[0].equals("")) {
                topList = new ArrayList<DataSet>();
                for (int i = 0; i < arrOfStr.length; i += 1) {
                    if (i % 6 == 3){
                        date = arrOfStr[i];
                        newDate = "";
                        if (date.charAt(5) == '0') {
                            newDate += date.charAt(6);
                        } else {
                            newDate += date.charAt(5);
                            newDate += date.charAt(6);
                        }
                        newDate += "/";
                        if (date.charAt(8) == '0') {
                            newDate += date.charAt(9);
                        } else {
                            newDate += date.charAt(8);
                            newDate += date.charAt(9);
                        }
                        newDate += "/";
                        newDate += date.charAt(2);
                        newDate += date.charAt(3);

                    } else {
                        if (i % 6 == 0){
                            newString+= arrOfStr[i];
                            newString+= "  ";
                        } else if (i % 6 == 1){
                            newString+= arrOfStr[i];
                            newString+= " x ";
                        } else if (i % 6 == 2) {
                            newString += arrOfStr[i];
                        } else if (i % 6 == 4){
                            id = arrOfStr[i];
                            DataSet dataset = new DataSet(Integer.toString(k), newString, newDate, id);
                            System.out.println("added " + dataset.getRawData() + " to the list");
                            topList.add(dataset);
                            newString = "";
                            k++;
                        }
                    }
                }
            } else {
                DataSet noItems = new DataSet("", "No data available", "", "");
                topList.add(noItems);
            }
        } else if (type.equals("WeightedSimple")){
            data = dbHelper.getData("SELECT Sets, Reps, Date, ID, (Reps * Sets) AS Total FROM WeightedTable2 WHERE Name = '" + name + "' ORDER BY Total DESC");
            arrOfStr = data.split(",");
            String newString = "";
            String id = "";
            if (!arrOfStr[0].equals("")) {
                topList = new ArrayList<DataSet>();
                for (int i = 0; i < arrOfStr.length; i += 1) {
                    if (i % 5 == 2){
                        date = arrOfStr[i];
                        System.out.println("DATE IS: "+date);
                        newDate = "";
                        if (date.charAt(5) == '0') {
                            newDate += date.charAt(6);
                        } else {
                            newDate += date.charAt(5);
                            newDate += date.charAt(6);
                        }
                        newDate += "/";
                        if (date.charAt(8) == '0') {
                            newDate += date.charAt(9);
                        } else {
                            newDate += date.charAt(8);
                            newDate += date.charAt(9);
                        }
                        newDate += "/";
                        newDate += date.charAt(2);
                        newDate += date.charAt(3);

                    } else {
                        if (i % 5 == 0){
                            newString+= arrOfStr[i];
                            newString+= " x ";
                        } else if (i % 5 == 1){
                            newString+= arrOfStr[i];
                        } else if (i % 5 == 3){
                            id = arrOfStr[i];
                            DataSet dataset = new DataSet(Integer.toString(k), newString, newDate, id);
                            System.out.println("added " + dataset.getRawData() + " to the list");
                            topList.add(dataset);
                            newString = "";
                            k++;
                        }
                    }
                }
            } else {
                DataSet noItems = new DataSet("", "No data available", "", "");
                topList.add(noItems);
            }
        }
    }

    public void updateFragmentList(String type, String measurement){
        initData(type, measurement);
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init top workouts");
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_top);
        String name = getActivity().getIntent().getStringExtra("workoutName");
        adapter = new ComplexAdapter(getActivity(), topList, name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


}
