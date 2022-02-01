package com.example.andrew.fitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by Andrew on 7/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "FIT";
    public static final String WORKOUT_TABLE_TITLE = "WORKOUTS";
    public static final String ACTIVITY_TABLE_TITLE = "ACTIVITY";

    //Workout Table
    private static final String WORKOUT_TABLE_NAME = "Name";
    private static final String WORKOUT_TABLE_TYPE = "Type";
    private static final String WORKOUT_TABLE_MEASUREMENT = "Measurement";
    //Activity Table
    private static final String ACTIVITY_TABLE_ID = "ID";
    private static final String ACTIVITY_TABLE_NAME = "Name";
    private static final String ACTIVITY_TABLE_DATE = "Date";
    private static final String ACTIVITY_TABLE_TIME = "Time";
    private static final String ACTIVITY_TABLE_DISTANCE = "Distance";
    private static final String ACTIVITY_TABLE_WEIGHT = "Weight";
    private static final String ACTIVITY_TABLE_REPS = "Reps";
    private static final String ACTIVITY_TABLE_SETS = "Sets";

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
            "CREATE TABLE IF NOT EXISTS " + ACTIVITY_TABLE_TITLE + " (" +
            ACTIVITY_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ACTIVITY_TABLE_NAME         + " TEXT, " +
            ACTIVITY_TABLE_DATE         + " DATE, " +
            ACTIVITY_TABLE_TIME         + " REAL, " +
            ACTIVITY_TABLE_DISTANCE     + " REAL, " +
            ACTIVITY_TABLE_WEIGHT       + " TEXT, " +
            ACTIVITY_TABLE_REPS         + " INTEGER, " +
            ACTIVITY_TABLE_SETS         + " INTEGER)";

        db.execSQL(createWorkoutTable);
        db.execSQL(initializeWorkoutTableData);
        db.execSQL(createActivityTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE_NAME);
        onCreate(db);
    }

    public boolean addWorkoutData(WorkoutData workout){
        SQLiteDatabase db = this.getWritableDatabase();
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

    public boolean addActivityData(ActivityData activity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ACTIVITY_TABLE_NAME, activity.name);
        //contentValues.put(ACTIVITY_TABLE_DATE, activity.date);
        contentValues.put(ACTIVITY_TABLE_TIME, activity.time);
        contentValues.put(ACTIVITY_TABLE_DISTANCE, activity.distance);
        contentValues.put(ACTIVITY_TABLE_WEIGHT, activity.weight);
        contentValues.put(ACTIVITY_TABLE_SETS, activity.sets);
        contentValues.put(ACTIVITY_TABLE_REPS, activity.reps);

        long result = db.insert(ACTIVITY_TABLE_TITLE, null, contentValues);

        if (result == -1) {
            Log.d(TAG, "addData: Failure");
            return false;
        }
        else {
            Log.d(TAG, "addData: Success");
            return true;
        }
    }

    public long getChangesCount() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("SELECT changes()");
        return statement.simpleQueryForLong();
    }

    public String getData(String query) {
        String row_values = "";
        Log.d(TAG, "getData: Getting data from a table");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(query, null);
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                for (int i = 0; i < cur.getColumnCount(); i++) {
                    row_values = row_values + "," + cur.getString(i);
                }

            } while (cur.moveToNext());
            row_values = row_values.substring(1);
            Log.d("TABLE VALUES: ", row_values);
        }
        return row_values;
    }

    public boolean deleteActivity(String weightOrTime, String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        long result0 = 0;
        long result1 = 0;
        result0 = db.delete(WORKOUT_TABLE_TITLE, "Name = '" + ID + "'", null);
        Log.d(TAG, "deleteActivity: Deleting " + ID + " " + "from " + WORKOUT_TABLE_TITLE);
        if (weightOrTime.equals("weight")) {
            result1 = db.delete(ACTIVITY_TABLE_TITLE, "Name = '" + ID + "'", null);
            Log.d(TAG, "deleteActivity: Deleting " + ID + " " + "from " + ACTIVITY_TABLE_TITLE);
        } else {
            result1 = db.delete(ACTIVITY_TABLE_TITLE, "Name = '" + ID + "'", null);
            Log.d(TAG, "deleteActivity: Deleting " + ID + " " + "from " + ACTIVITY_TABLE_TITLE);
        }
        if (result0 == -1 || result1 == -1) {
            Log.d(TAG, "deleteActivity: Failure");
            return false;
        }
        else{
            Log.d(TAG, "deleteActivity: Success");
            return true;
        }
    }

    public boolean deleteEvent(String weightOrTime, String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        if (weightOrTime.equals("Weight")) {
            result = db.delete(ACTIVITY_TABLE_TITLE, "ID = '" + ID + "'", null);
            Log.d(TAG, "deleteEvent: Deleting " + ID + " " + "from " + ACTIVITY_TABLE_TITLE);
        } else {
            result = db.delete(ACTIVITY_TABLE_TITLE, "ID = '" + ID + "'", null);
            Log.d(TAG, "deleteEvent: Deleting " + ID + " " + "from " + ACTIVITY_TABLE_TITLE);
        }
        if (result == -1) {
            Log.d(TAG, "deleteEvent: Failure");
            return false;
        }
        else {
            Log.d(TAG, "deleteEvent: Success");
            return true;
        }
    }
}
