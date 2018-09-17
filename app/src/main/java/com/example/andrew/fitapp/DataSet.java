package com.example.andrew.fitapp;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Andrew on 9/3/2018.
 */

public class DataSet {
    private String number, rawData, date, id;

    public DataSet(String string1, String string2, String string3, String string4){
        this.number = string1;
        this.rawData = string2;
        this.date = string3;
        this.id = string4;
    }

    public String getNumber() {
        return number;
    }

    public void setNumbber(String newNumber) {
        this.number = newNumber;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String newRawData) {
        this.rawData = newRawData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public String getId(){return id;}

    public void setId(String newId){this.id = newId;}

}
