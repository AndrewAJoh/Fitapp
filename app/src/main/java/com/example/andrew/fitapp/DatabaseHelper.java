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
    private static final String TABLE_NAME = "NameTable4";
    private static final String TABLE2_NAME = "TimedTable4";
    private static final String TABLE3_NAME = "WeightedTable2";
    //Workout Name Table
    //private static final String COL0 = "ID";
    private static final String ACOL1 = "Name";
    private static final String ACOL2 = "Type";
    private static final String ACOL3 = "Measurement";
    private static final String ACOL4 = "Simple";
    //Timed Table
    private static final String BCOL0 = "ID";
    private static final String BCOL1 = "Name";
    private static final String BCOL4 = "Date";
    private static final String BCOL2 = "Time";
    private static final String BCOL3 = "Distance";
    //Weight Table
    private static final String CCOL0 = "ID";
    private static final String CCOL1 = "Name";
    private static final String CCOL5 = "Date";
    private static final String CCOL2 = "Weight";
    private static final String CCOL3 = "Reps";
    private static final String CCOL4 = "Sets";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Only called when you first create the database
        Log.d(TAG, "onCreate: started");
        //String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + " TEXT)";
        String createTable1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("+ACOL1 + " TEXT, " + ACOL2 + " TEXT, " + ACOL3 + " TEXT, " + ACOL4 + " TEXT, UNIQUE(" + ACOL1 + ", " + ACOL2 + "))";
        String initializeData1 = "INSERT INTO " + TABLE_NAME + " ("+ACOL1+", "+ACOL2+", "+ACOL3+", "+ACOL4+") VALUES " +
                "('running', 'Cardio', 'Timed', 'False'), " +
                "('swimming', 'Cardio', 'Timed', 'False'), " +
        "('biking', 'Cardio', 'Timed', 'False'), " +
        "('walking', 'Cardio', 'Timed', 'False'), " +
        "('barbell curl', 'Biceps', 'Weighted', 'False'), " +
        "('chin ups', 'Biceps', 'Weighted', 'False'), " +
        "('concentration curl', 'Biceps', 'Weighted', 'False'), " +
        "('incline dumbell curls', 'Biceps', 'Weighted', 'False'), " +
                "('bench press', 'Chest', 'Weighted', 'False'), " +
        "('inclide bench press', 'Chest', 'Weighted', 'False'), " +
        "('decline bench press', 'Chest', 'Weighted', 'False'), " +
        "('dumbell bench press', 'Chest', 'Weighted', 'False'), " +
        "('fly', 'Chest', 'Weighted', 'False'), " +
        "('push-ups', 'Chest', 'Weighted', 'True'), " +
        "('squat', 'Legs', 'Weighted', 'False'), " +
        "('calf raises', 'Legs', 'Weighted', 'False'), " +
        "('dumbell lunges', 'Legs', 'Weighted', 'False'), " +
        "('planks', 'Abs', 'Timed', 'True'), " +
        "('crunches', 'Abs', 'Weighted', 'True'), " +
        "('sit-ups', 'Abs', 'Weighted', 'True'), " +
        "('leg raises', 'Abs', 'Weighted', 'True'), " +
        "('standing dumbell triceps extension', 'Triceps', 'Weighted', 'False'), " +
        "('skull crushers', 'Triceps', 'Weighted', 'False'), " +
        "('rope pull-down', 'Triceps', 'Weighted', 'False'), " +
        "('tricep push-ups', 'Triceps', 'Weighted', 'True')";
        String createTable2 = "CREATE TABLE IF NOT EXISTS " + TABLE2_NAME + " (" + BCOL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BCOL1 + " TEXT, " + BCOL4 + " TEXT, " +
                BCOL2 + " REAL, " + BCOL3 + " REAL)";
        String initializeData2 = "INSERT INTO " + TABLE2_NAME + " ("+BCOL0+", "+BCOL1+", "+BCOL4+", "+BCOL2+", "+BCOL3+") VALUES " +
                "(1, 'running', '2018-09-01', 1800, 4.5), " +
                "(2, 'running', '2018-09-02', 2130, 5.5), " +
                "(3, 'running', '2018-09-03', 900, 2.0), " +
                "(4, 'running', '2018-09-04', 420, 1.2)";
        String initializeData3 = "INSERT INTO " + TABLE2_NAME + " ("+BCOL0+", "+BCOL1+", "+BCOL4+", "+BCOL2+") VALUES " +
                "(5, 'planks', '2018-09-05', 45), " +
                "(6, 'planks', '2018-09-06', 60), " +
                "(7, 'planks', '2018-09-07', 105), " +
                "(8, 'planks', '2018-09-08', 165)";
        String createTable3 = "CREATE TABLE IF NOT EXISTS " + TABLE3_NAME + " (" + CCOL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CCOL1 +
                " TEXT, " + CCOL5 + " TEXT, " + CCOL2 + " TEXT, " + CCOL3 + " TEXT, " + CCOL4 + " TEXT)";
        String initializeData4 = "INSERT INTO " + TABLE3_NAME + " (" + CCOL0 + ", " + CCOL1 + ", " + CCOL5 + ", " + CCOL2 + ", " + CCOL3 + ", " + CCOL4 + ") VALUES " +
                "(1, 'concentration curl', '2018-09-01', '45', '8', '3'), " +
                "(2, 'concentration curl', '2018-09-02', '40', '8', '3'), " +
                "(3, 'concentration curl', '2018-09-03', '40', '10', '3'), " +
                "(4, 'concentration curl', '2018-09-04', '45', '6', '3')";
        String initializeData5 = "INSERT INTO " + TABLE3_NAME + " (" + CCOL0 + ", " + CCOL1 + ", " + CCOL5 + ", " + CCOL3 + ", " + CCOL4 + ") VALUES " +
                "(5, 'push-ups', '2018-09-05', '2', '20'), " +
                "(6, 'push-ups', '2018-09-06', '3', '20'), " +
                "(7, 'push-ups', '2018-09-07', '3', '25'), " +
                "(8, 'push-ups', '2018-09-08', '4', '20')";
        System.out.println(createTable1);
        db.execSQL(createTable1);
        System.out.println(initializeData1);
        db.execSQL(initializeData1);
        System.out.println(createTable2);
        db.execSQL(createTable2);
        System.out.println(initializeData2);
        db.execSQL(initializeData2);
        System.out.println(createTable3);
        db.execSQL(createTable3);
        System.out.println(initializeData3);
        db.execSQL(initializeData3);
        System.out.println(initializeData4);
        db.execSQL(initializeData4);
        System.out.println(initializeData5);
        db.execSQL(initializeData5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String table, String item1, String item2, String item3, String item4, String item5){
        //Replaces if title already exists, otherwise doesn't
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String tableName = "none";
        if (table.equals("A")) {
            contentValues.put(ACOL1, item1);
            contentValues.put(ACOL2, item2);
            contentValues.put(ACOL3, item3);
            contentValues.put(ACOL4, item4);
            tableName = "NameTable4";
        } else if (table.equals("B")){
            contentValues.put(BCOL1, item1);
            contentValues.put(BCOL4, item5); //date
            contentValues.put(BCOL2, item2);
            contentValues.put(BCOL3, item3);
            tableName = "TimedTable4";
        } else if (table.equals("C")){
            contentValues.put(CCOL1, item1);
            contentValues.put(CCOL5, item5); //date
            contentValues.put(CCOL2, item2);
            contentValues.put(CCOL3, item3);
            contentValues.put(CCOL4, item4);
            tableName = "WeightedTable2";
        }
        Log.d(TAG, "addData: Adding " + item1 + ", " + item2 + ", " + item5 + ", " + item3 + ", " + item4 + " to " + tableName);
        //db.execSQL("REPLACE INTO " + TABLE_NAME + " (ID, Name, Type) VALUES (null, '" + item1 + "', '" + item2 + "')");
        long result = db.insert(tableName, null, contentValues);
        if (result == -1) {
            Log.d(TAG, "addData: Failure");
            return false;
        }
        else{
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

    public boolean deleteData(String type, String weightOrTime, String ID){
        //ID is either the workout name or the id of the entry to be deleted
        //Delete entry from database
        SQLiteDatabase db = this.getWritableDatabase();
        long result0 = 0;
        long result1 = 0;
        if (type.equals("0")) {
            //deleting workout. Also deletes all entries for that workout
            result0 = db.delete(TABLE_NAME, "Name = '" + ID + "'", null);
            Log.d(TAG, "deleteData: Deleting " + ID + " " + " from " + TABLE_NAME);
            if (weightOrTime.equals("weight")) {
                result1 = db.delete(TABLE3_NAME, "Name = '" + ID + "'", null);
                Log.d(TAG, "deleteData: Deleting " + ID + " " + " from " + TABLE3_NAME);
            } else {
                result1 = db.delete(TABLE2_NAME, "Name = '" + ID + "'", null);
                Log.d(TAG, "deleteData: Deleting " + ID + " " + " from " + TABLE2_NAME);
            }
        } else{
            if (weightOrTime.equals("Weight")){
                result0 = db.delete(TABLE3_NAME, "ID = '" + ID + "'", null);
            } else{
                result0 = db.delete(TABLE2_NAME, "ID = '" + ID + "'", null);
            }
        }
        if (result0 == -1 || result1 == -1) {
            Log.d(TAG, "deleteData: Failure");
            return false;
        }
        else{
            Log.d(TAG, "deleteData: Success");
            return true;
        }
    }
}
