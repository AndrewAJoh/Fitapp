package com.example.andrew.fitapp;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Andrew on 9/3/2018.
 */

public class EventItem {
    public String number, data, date, id;

    public EventItem(String numberInput, String dataInput, String dateInput, String idInput){
        number = numberInput;
        data = dataInput;
        date = dateInput;
        id = idInput;
    }

}
