package com.example.andrew.fitapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew on 9/3/2018.
 */
public class ComplexAdapter extends RecyclerView.Adapter<ComplexAdapter.SecondViewHolder> {

    private static final String TAG = "ComplexAdapter";
    private List<EventItem> activityList;
    private static String name;


    public ComplexAdapter(Context context, List<EventItem> activityListInput, String workoutName){
        activityList = activityListInput;
        String logName = "Complex adapter workoutNames size: " + String.valueOf(activityList.size());
        Log.d(TAG, logName);
        name = workoutName;

        if (getItemCount() == 0){
            EventItem blankActivity = new EventItem("", "No Data Available", "", "");
            activityList.add(blankActivity);
        }
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
        Log.d(TAG, "THIS WAS CALLED");
        final String number = activityList.get(position).number;
        holder.text1.setText(number);

        final String data = activityList.get(position).data;
        holder.text2.setText(data);

        final String date = activityList.get(position).date;
        holder.text3.setText(date);

        final String id = activityList.get(position).id;

        holder.layout.setOnClickListener(v -> {
            Log.d(TAG, "clicked on button");
            Intent intent = new Intent(v.getContext(), EventDetailActivity.class);
            intent.putExtra("workoutName", name);
            intent.putExtra("date", date);
            intent.putExtra("id", id);
            (mContext).startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout layout;
        public TextView text1;
        public TextView text2;
        public TextView text3;

        public SecondViewHolder(View itemView) {
            super(itemView);
            //Gets layout from layout_eventholder
            layout = itemView.findViewById(R.id.llContainer);
            text1 = itemView.findViewById(R.id.number);
            text2 = itemView.findViewById(R.id.rawData);
            text3 = itemView.findViewById(R.id.date);
        }
    }

}
