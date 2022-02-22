package com.example.andrew.fitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrew on 7/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "FIT";
    public static final String WORKOUT_TABLE_TITLE = "WORKOUTS";
    public static final String EVENT_TABLE_TITLE = "EVENT";
    public static final String SETTINGS_TABLE_TITLE = "SETTINGS";

    //Workout Table
    private static final String WORKOUT_TABLE_NAME = "Name";
    private static final String WORKOUT_TABLE_TYPE = "Type";
    private static final String WORKOUT_TABLE_MEASUREMENT = "Measurement";
    //Activity Table
    private static final String EVENT_TABLE_ID = "ID";
    private static final String EVENT_TABLE_NAME = "Name";
    private static final String EVENT_TABLE_DATE = "Date";
    private static final String EVENT_TABLE_TIME = "Time";
    private static final String EVENT_TABLE_DISTANCE = "Distance";
    private static final String EVENT_TABLE_WEIGHT = "Weight";
    private static final String EVENT_TABLE_REPS = "Reps";
    private static final String EVENT_TABLE_SETS = "Sets";
    //Settings Table
    private static final String SETTINGS_TABLE_MEASUREMENT = "Measurement";

    private static final String WORKOUT_TABLE_INITIAL_ROWS =
        "('running', 'Cardio', 3), " +
        "('swimming', 'Cardio', 3), " +
        "('biking', 'Cardio', 3), " +
        "('walking', 'Cardio', 3), " +
        "('barbell curl', 'Biceps', 1), " +
        "('chin ups', 'Biceps', 1), " +
        "('concentration curl', 'Biceps', 1), " +
        "('incline dumbell curls', 'Biceps', 1), " +
        "('bench press', 'Chest', 1), " +
        "('inclide bench press', 'Chest', 1), " +
        "('decline bench press', 'Chest', 1), " +
        "('dumbell bench press', 'Chest', 1), " +
        "('fly', 'Chest', 1), " +
        "('push-ups', 'Chest', 2), " +
        "('squat', 'Legs', 1), " +
        "('calf raises', 'Legs', 1), " +
        "('dumbell lunges', 'Legs', 1), " +
        "('planks', 'Abs', 4), " +
        "('crunches', 'Abs', 2), " +
        "('sit-ups', 'Abs', 2), " +
        "('leg raises', 'Abs', 2), " +
        "('standing dumbell triceps extension', 'Triceps', 1), " +
        "('skull crushers', 'Triceps', 1), " +
        "('rope pull-down', 'Triceps', 1), " +
        "('tricep push-ups', 'Triceps', 2)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: started");
        String createWorkoutTable =
            "CREATE TABLE IF NOT EXISTS " + WORKOUT_TABLE_TITLE + " (" +
            WORKOUT_TABLE_NAME          + " TEXT, " +
            WORKOUT_TABLE_TYPE          + " TEXT, " +
            WORKOUT_TABLE_MEASUREMENT   + " INTEGER, " +
            "UNIQUE(" + WORKOUT_TABLE_NAME + ", " + WORKOUT_TABLE_TYPE + "))";
        Log.d(TAG, createWorkoutTable);

        String initializeWorkoutTableData =
            "INSERT INTO " + WORKOUT_TABLE_TITLE + " ("+
            WORKOUT_TABLE_NAME          + ", " +
            WORKOUT_TABLE_TYPE          + ", " +
            WORKOUT_TABLE_MEASUREMENT   + ") VALUES " +
            WORKOUT_TABLE_INITIAL_ROWS;

        String createActivityTable =
            "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_TITLE + " (" +
            EVENT_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EVENT_TABLE_NAME         + " TEXT, " +
            EVENT_TABLE_DATE         + " TEXT, " +
            EVENT_TABLE_TIME         + " INTEGER, " +
            EVENT_TABLE_DISTANCE     + " REAL, " +
            EVENT_TABLE_WEIGHT       + " TEXT, " +
            EVENT_TABLE_REPS         + " INTEGER, " +
            EVENT_TABLE_SETS         + " INTEGER, " +
            "UNIQUE(" + EVENT_TABLE_NAME + ", " + EVENT_TABLE_DATE + "))";

        String createSettingsTable =
            "CREATE TABLE IF NOT EXISTS " + SETTINGS_TABLE_TITLE + " (" +
            SETTINGS_TABLE_MEASUREMENT + " INTEGER)";

        String initializeSettingsTableData =
            "INSERT INTO " + SETTINGS_TABLE_TITLE + " (" +
            SETTINGS_TABLE_MEASUREMENT + ") VALUES " +
            "(1)";

        db.execSQL(createWorkoutTable);
        db.execSQL(initializeWorkoutTableData);
        db.execSQL(createActivityTable);
        db.execSQL(createSettingsTable);
        db.execSQL(initializeSettingsTableData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addWorkoutData(WorkoutData workout){
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(workout.name);
        if (m.find()){
            return false;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(WORKOUT_TABLE_NAME, workout.name);
        contentValues.put(WORKOUT_TABLE_TYPE, workout.type);
        contentValues.put(WORKOUT_TABLE_MEASUREMENT, workout.measurement);

        Log.d(TAG, "addData: Adding " +
        workout.name + ", " +
        workout.type + ", " +
        workout.measurement + " to " +
        WORKOUT_TABLE_TITLE);

        long result = db.insert(WORKOUT_TABLE_TITLE, null, contentValues);

        if (result == -1) {
            Log.d(TAG, "addData: Failure");
            return false;
        }
        else{
            Log.d(TAG, "addData: Success");
            return true;
        }
    }

    public boolean addEventData(EventData activity){
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(activity.name);
        if (m.find()){
            return false;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(EVENT_TABLE_NAME, activity.name);
        contentValues.put(EVENT_TABLE_DATE, activity.date);
        contentValues.put(EVENT_TABLE_TIME, activity.time);
        contentValues.put(EVENT_TABLE_DISTANCE, activity.distance);
        contentValues.put(EVENT_TABLE_WEIGHT, activity.weight);
        contentValues.put(EVENT_TABLE_SETS, activity.sets);
        contentValues.put(EVENT_TABLE_REPS, activity.reps);

        long result = db.insert(EVENT_TABLE_TITLE, null, contentValues);

        if (result == -1) {
            Log.d(TAG, "addData: Failure");
            return false;
        }
        else {
            Log.d(TAG, "addData: Success");
            return true;
        }
    }

    public EventData getEventDataById(String id){
        String logMessage = "getData: Getting data from " + EVENT_TABLE_TITLE;
        Log.d(TAG, logMessage);
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^0-9]");

        Matcher m = p.matcher(id);
        if (m.find()){
            Log.d(TAG, "REGEX FOUND MATCH. INVALID EVENT");
            return null;
        }

        String query = "SELECT ID, Name, Date, Time, Distance, Weight, Reps, Sets FROM " +
                DatabaseHelper.EVENT_TABLE_TITLE +
                " WHERE ID = '" + id + "'";

        Log.d(TAG, query);

        Cursor cur = db.rawQuery(query, null);

        EventData ad = null;

        if (cur.getCount() != 0) {
            cur.moveToFirst();

            ad = new EventData(
                cur.getInt(0),
                cur.getString(1),
                cur.getString(2),
                cur.getInt(3),
                cur.getString(4),
                cur.getString(5),
                cur.getString(6),
                cur.getString(7));
        }

        cur.close();

        return ad;
    }

    public List<EventData> getEventDataByName(String activityName, String metric, String order) {
        String logMessage = "getData: Getting data from " + EVENT_TABLE_TITLE;
        Log.d(TAG, logMessage);
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(activityName);
        if (m.find()){
            Log.d(TAG, "REGEX FOUND MATCH. INVALID ACTIVITY");
            return null;
        }

        String query = "SELECT ID, Name, Date, Time, Distance, Weight, Reps, Sets FROM " +
                        DatabaseHelper.EVENT_TABLE_TITLE +
                        " WHERE Name = '" + activityName + "'";

        String queryOrder = " ORDER BY ";

        if (order.equals("Recent")){
            queryOrder += "Date";
        } else {
            switch(metric) {
                case "Distance":
                    queryOrder += "Distance";
                case "Time":
                    queryOrder += "Time";
                case "Pace":
                    queryOrder += "(Distance/Time)";
                case "Reps":
                    queryOrder += "(Reps * Sets)";
                case "Total":
                    queryOrder += "(Weight * Reps * Sets)";
            }
        }

        queryOrder += " DESC";
        query += queryOrder;

        Log.d(TAG, query);

        List<EventData> res = new ArrayList<>();
        Cursor cur = db.rawQuery(query, null);

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                EventData ad = new EventData(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getInt(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getString(6),
                    cur.getString(7));

                res.add(ad);
            } while (cur.moveToNext());
        }

        cur.close();

        return res;
    }

    public List<WorkoutData> getWorkoutsByType(String type){
        String logMessage = "getData: Getting data from " + WORKOUT_TABLE_TITLE;
        Log.d(TAG, logMessage);
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(type);
        if (m.find()){
            Log.d(TAG, "REGEX FOUND MATCH. INVALID EVENT");
            return null;
        }

        String query = "SELECT Name, Type, Measurement FROM " + WORKOUT_TABLE_TITLE + " WHERE Type = '" + type + "'";
        Cursor cur = db.rawQuery(query, null);

        List<WorkoutData> workoutList = new ArrayList<>();

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {

                WorkoutData wd = new WorkoutData(
                        cur.getString(0),
                        cur.getString(1),
                        cur.getInt(2));

                workoutList.add(wd);
            } while (cur.moveToNext());
        }

        cur.close();

        return workoutList;
    }

    public WorkoutData getWorkoutDataByName(String workoutName){
        String logMessage = "getData: Getting data from " + WORKOUT_TABLE_TITLE;
        Log.d(TAG, logMessage);
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(workoutName);
        if (m.find()){
            return null;
        }

        String query = "SELECT Name, Type, Measurement FROM " + WORKOUT_TABLE_TITLE + " WHERE Name = '" + workoutName + "'";

        Cursor cur = db.rawQuery(query, null);
        WorkoutData wd = new WorkoutData();

        if (cur.getCount() != 0) {
            cur.moveToFirst();

            wd.name = cur.getString(0);
            wd.type = cur.getString(1);
            wd.measurement = cur.getInt(2);
        }

        cur.close();

        return wd;
    }

    public void setMeasurementSettings(boolean measurementInput){
        //true/1 for US, false/0 for metric
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int databaseValue;
        if (measurementInput){
            databaseValue = 1;
        } else{
            databaseValue = 0;
        }

        Log.d(TAG, "DATABASE NEW VALUE IS: " + databaseValue);

        //delete old settings record (max 1)
        db.delete(SETTINGS_TABLE_TITLE, null, null);

        contentValues.put(SETTINGS_TABLE_MEASUREMENT, databaseValue);

        long result = db.insert(SETTINGS_TABLE_TITLE, null, contentValues);

        if (result == -1) {
            Log.d(TAG, "setMeasurementSettings: Failure");
            return;
        }
        else {
            Log.d(TAG, "setMeasurementSettings: Success");
        }

        //Convert existing event data into the right units of measurement

        String distanceQuery;
        String weightQuery;

        if (measurementInput) { //Converting to US

            distanceQuery = "UPDATE " + EVENT_TABLE_TITLE + " SET " + EVENT_TABLE_DISTANCE +
                    " = 0.621 * " + EVENT_TABLE_DISTANCE + " WHERE " + EVENT_TABLE_DISTANCE + " IS NOT NULL";

            weightQuery = "UPDATE " + EVENT_TABLE_TITLE + " SET " + EVENT_TABLE_WEIGHT +
                    " = 2.205 * " + EVENT_TABLE_WEIGHT + " WHERE " + EVENT_TABLE_WEIGHT + " IS NOT NULL";
        } else{ //Converting to metric
            distanceQuery = "UPDATE " + EVENT_TABLE_TITLE + " SET " + EVENT_TABLE_DISTANCE +
                    " = 1.609 * " + EVENT_TABLE_DISTANCE + " WHERE " + EVENT_TABLE_DISTANCE + " IS NOT NULL";

            weightQuery = "UPDATE " + EVENT_TABLE_TITLE + " SET " + EVENT_TABLE_WEIGHT +
                    " = 0.454 * " + EVENT_TABLE_WEIGHT + " WHERE " + EVENT_TABLE_WEIGHT + " IS NOT NULL";
        }
        Log.d(TAG, distanceQuery + "\n" + weightQuery);
        db.execSQL(distanceQuery);
        db.execSQL(weightQuery);
    }

    public boolean getMeasurementSettings(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT Measurement FROM " + SETTINGS_TABLE_TITLE;
        Log.d(TAG, query);

        Cursor cur = db.rawQuery(query, null);

        boolean result;

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            result = cur.getInt(0) == 1;
        } else{
            result = true;
        }

        Log.d(TAG, String.valueOf(result));

        cur.close();
        return result;
    }

    public void deleteEvent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(id);
        if (m.find()){
            return;
        }

        long result = db.delete(EVENT_TABLE_TITLE, "ID = '" + id + "'", null);
        Log.d(TAG, "deleteEvent: Deleted ID " + id + " " + "from " + EVENT_TABLE_TITLE);

        if (result == -1) {
            Log.d(TAG, "deleteEvent: Failure");
        }
        else {
            Log.d(TAG, "deleteEvent: Success");
        }
    }

    public void deleteWorkout(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        Pattern p = Pattern.compile("[^a-zA-Z -]");

        Matcher m = p.matcher(name);
        if (m.find()){
            return;
        }

        long result = db.delete(WORKOUT_TABLE_TITLE, "Name = '" + name + "'", null);
        Log.d(TAG, "deleteEvent: Deleting " + name + " " + "from " + WORKOUT_TABLE_TITLE);

        if (result == -1) {
            Log.d(TAG, "deleteWorkout: Failure");
        }
        else {
            Log.d(TAG, "deleteWorkout: Success");
        }

        result = db.delete(EVENT_TABLE_TITLE, EVENT_TABLE_NAME + " = '" + name + "'", null);
        Log.d(TAG, "deleteEvent: Deleting all" + name + " workouts from " + EVENT_TABLE_TITLE);

        if (result == -1) {
            Log.d(TAG, "deleteEvent: Failure");
        }
        else {
            Log.d(TAG, "deleteEvent: Success");
        }
    }
}
