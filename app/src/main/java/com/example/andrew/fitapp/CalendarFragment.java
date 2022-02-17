package com.example.andrew.fitapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 8/14/2018.
 */

public class CalendarFragment extends Fragment implements EventsOverviewActivity.FragmentListener{
    private static final String TAG = "Calendar";
    private static String workoutName;
    View view;
    DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Log.d(TAG, "OnCreateView: Started");
        view = inflater.inflate(R.layout.calendar_tab, container, false);
        dbHelper = new DatabaseHelper(getContext());

        workoutName = getActivity().getIntent().getStringExtra("workoutName").toLowerCase();

        WorkoutData workoutData = dbHelper.getWorkoutDataByName(workoutName);

        initData();

        initRecyclerView();
        ((EventsOverviewActivity) getActivity()).setCalendarFragmentListener(this);
        return view;
    }

    private void initData() {

        List<EventData> activityList = dbHelper.getEventDataByName(workoutName, null, "Recent");

        HashMap<String,Integer> activityDateMap = new HashMap<>();
        HashMap<Button,String> buttonDateMap = new HashMap<>();

        for (int i = 0; i < activityList.size(); i++){
            activityDateMap.put(activityList.get(i).date, activityList.get(i).id); //MM/dd/yyyy
        }

        TableLayout tl = view.findViewById(R.id.table);
        tl.setShrinkAllColumns(true);
        TableRow tr;

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate); //need to set to Sunday of the current week
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        c.add(Calendar.DAY_OF_YEAR, -1 * (dayOfWeek-1));
        int month = c.get(Calendar.MONTH);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        int buttonStyle = R.style.Base_Widget_AppCompat_Button_Borderless; //Create button without shadow
        TableRow.LayoutParams llp = new TableRow.LayoutParams(95, 95);
        llp.setMargins(0, 0, 5, 0);//2px right-margin


        for (int j = 0; j < 16; j++){
            tr = new TableRow(getContext());
            tr.setPadding(0, 0, 0, 5); //Border between rows
            tr.setGravity(Gravity.CENTER_HORIZONTAL);
            for (int i = 0; i < 8; i++){
                if (j == 0 && i >= dayOfWeek + 1){     //Future date - set as background color
                    Button button = new Button(new ContextThemeWrapper(getContext(), buttonStyle), null, buttonStyle);
                    button.setLayoutParams(llp);
                    button.setBackgroundColor(Color.parseColor("#FAFAFA"));
                    tr.addView(button);
                    buttonDateMap.put(button, dateFormat.format(c.getTime()));
                } else if (i == 0){     //Month text
                    TextView text = new TextView(getContext());
                    text.setLayoutParams(llp);

                    int newMonth = c.get(Calendar.MONTH);
                    if (month == newMonth){
                        text.setText("");
                    } else{
                        switch (newMonth) {
                            case 0:   text.setText("Feb");
                                break;
                            case 1:   text.setText("Mar");
                                break;
                            case 2:   text.setText("Apr");
                                break;
                            case 3:   text.setText("May");
                                break;
                            case 4:   text.setText("Jun");
                                break;
                            case 5:   text.setText("Jul");
                                break;
                            case 6:   text.setText("Aug");
                                break;
                            case 7:   text.setText("Sep");
                                break;
                            case 8:   text.setText("Oct");
                                break;
                            case 9:   text.setText("Nov");
                                break;
                            case 10:  text.setText("Dec");
                                break;
                            case 11:  text.setText("Jan");
                                break;
                            default:  text.setText("");
                                break;
                        }
                        month = newMonth;
                    }

                    tr.addView(text);
                } else{ //Day of the week - decrement calendar by a day
                    Date date = c.getTime();

                    String dateString = dateFormat.format(date);

                    Button button = new Button(new ContextThemeWrapper(getContext(), buttonStyle), null, buttonStyle);
                    button.setLayoutParams(llp);

                    if (activityDateMap.containsKey(dateString)){
                        button.setBackgroundColor(Color.parseColor("#FF7A05"));
                    } else{
                        button.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    }

                    button.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                    button.setTextSize(13);
                    tr.addView(button);
                    buttonDateMap.put(button, dateFormat.format(c.getTime()));

                    button.setOnClickListener(v -> {
                        Log.d(TAG, "clicked on button");
                        //get date from button
                        String buttonDate = buttonDateMap.get(button);
                        int id = activityDateMap.get(buttonDate);

                        Intent intent = new Intent(v.getContext(), EventDetailActivity.class);
                        intent.putExtra("workoutName", workoutName);
                        intent.putExtra("date", buttonDate);
                        intent.putExtra("id", String.valueOf(id));
                        getContext().startActivity(intent);
                    });

                }
                if (i == 7){
                    c.add(Calendar.DAY_OF_YEAR, -13);
                }
                else if (i > 0){
                    c.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
            tl.addView(tr);
        }
    }

    public void updateFragmentList(){
        initData();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recent workouts");
    }

}
