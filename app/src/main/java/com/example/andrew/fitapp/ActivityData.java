package com.example.andrew.fitapp;

import java.util.Comparator;
import java.util.Date;

public class ActivityData{
    public Integer id;
    public String name;
    public String date; //MM/dd/yyyy
    public Integer time;
    public String distance;
    public String weight;
    public String reps;
    public String sets;

    public ActivityData(Integer idInput, String nameInput, String dateInput, Integer timeInput, String distanceInput, String weightInput, String repsInput, String setsInput){
        id = idInput;
        name = nameInput;
        date = dateInput;
        time = timeInput;
        distance = distanceInput;
        weight = weightInput;
        reps = repsInput;
        sets = setsInput;
    }
}
