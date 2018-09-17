package com.example.andrew.fitapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.andrew.fitapp.Adapter;
import com.example.andrew.fitapp.DataSet;
import com.example.andrew.fitapp.EventHolder;
import com.example.andrew.fitapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 9/3/2018.
 */
public class ComplexAdapter extends RecyclerView.Adapter<ComplexAdapter.SecondViewHolder> {

    private static final String TAG = "ComplexAdapter";
    private List<DataSet> items = new ArrayList<DataSet>();
    private static String name;
    private static String weightedOrTimed;
    private static String simpleBoolean;
    DatabaseHelper dbHelper;


    public ComplexAdapter(Context context, List<DataSet> workoutNames, String workoutName){
        items = workoutNames;
        name = workoutName;
    }
    Context mContext;
    @NonNull
    @Override
    public SecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_eventholder, parent, false);
        mContext = parent.getContext();
        return new SecondViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondViewHolder holder, final int position) {
        //Called every time a view is displayed
        final String number = items.get(position).getNumber();
        final String rawData = items.get(position).getRawData();
        final String date = items.get(position).getDate();
        final String id = items.get(position).getId();
        holder.text1.setText(number);
        holder.text2.setText(rawData);
        holder.text3.setText(date);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked on button");
                Intent intent = new Intent(v.getContext(), EntryDetail.class);
                intent.putExtra("workoutName", name);
                intent.putExtra("date", date);
                intent.putExtra("id", id);
                if (!date.equals("")) {
                    ((Activity) mContext).startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout layout;
        public TextView text1;
        public TextView text2;
        public TextView text3;

        public SecondViewHolder(View itemView) {
            super(itemView);
            //Gets layout from layout_eventholder
            layout = (RelativeLayout) itemView.findViewById(R.id.llContainer);
            text1 = itemView.findViewById(R.id.number);
            text2 = itemView.findViewById(R.id.rawData);
            text3 = itemView.findViewById(R.id.date);
        }
    }

}
