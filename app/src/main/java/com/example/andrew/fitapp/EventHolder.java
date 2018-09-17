package com.example.andrew.fitapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Andrew on 9/3/2018.
 */

public class EventHolder extends RecyclerView.ViewHolder {

    private TextView label1, label2, label3;

    public EventHolder(View v) {
        super(v);
        label1 = (TextView) v.findViewById(R.id.number);
        label2 = (TextView) v.findViewById(R.id.rawData);
        label3 = (TextView) v.findViewById(R.id.date);
    }

    public TextView getLabel1() {
        return label1;
    }

    public void setLabel1(TextView label1) {
        this.label1 = label1;
    }

    public TextView getLabel2() {
        return label2;
    }

    public void setLabel2(TextView label2) {
        this.label2 = label2;
    }

    public TextView getLabel3() {
        return label3;
    }

    public void setLabel3(TextView label3) {
        this.label3 = label3;
    }
}
