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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 7/17/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private static final String TAG = "Adapter";
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private List<String> workoutTypeList = new ArrayList<String>();


    public Adapter(Context context, List<String> workoutNames){
        workoutTypeList = workoutNames;
    }
    Context mContext;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_single_layout, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Called every time a view is displayed
        final String result = workoutTypeList.get(position);
        holder.workoutType.setText(result);
        holder.workoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked on button");
                Intent intent = new Intent(v.getContext(), EventsOverviewActivity.class);
                intent.putExtra("workoutName", result);
                ((Activity) mContext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView workoutType;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            //Gets layout from workout_single_layout
            workoutType = (Button) itemView.findViewById(R.id.workoutTypeButton);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
