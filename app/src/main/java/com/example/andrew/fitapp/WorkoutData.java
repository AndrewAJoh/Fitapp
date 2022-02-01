package com.example.andrew.fitapp;

import java.util.Date;

public class WorkoutData {
    public String name;
    public String type;
    public int measurement;

    public WorkoutData(String nameInput, String typeInput, int measurementInput){
        name = nameInput;
        type = typeInput;
        measurement = measurementInput;
    }
}
