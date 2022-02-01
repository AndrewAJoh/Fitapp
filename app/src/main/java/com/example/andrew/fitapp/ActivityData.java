package com.example.andrew.fitapp;

import java.util.Date;

public class ActivityData {
    public String name;
    public Date date;
    public String time;
    public String distance;
    public String weight;
    public String reps;
    public String sets;

    public ActivityData(String nameInput, Date dateInput, String timeInput, String distanceInput, String weightInput, String repsInput, String setsInput){
        name = nameInput;
        date = dateInput;
        time = timeInput;
        distance = distanceInput;
        weight = weightInput;
        reps = repsInput;
        sets = setsInput;
    }

}
