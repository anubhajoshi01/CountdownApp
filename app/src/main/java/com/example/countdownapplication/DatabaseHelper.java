package com.example.countdownapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "tasks_table";
    public static final String COL_TASK = "Task";
    public static final String COL_YEAR = "Year";
    public static final String COL_MONTH = "Month";
    public static final String COL_DAY = "Day";
    public static final String COL_HOUR = "Hour";
    public static final String COL_MIN = "Minute";
    public static final String COL_SEC = "Second";
    public static final String COL_ID = "Task_ID";
    public static final String DATABASE_NAME = "All_Scheduled_tasks.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static int getEntryCount(SQLiteDatabase db){
        String getCount = "SELECT COUNT (" + COL_HOUR + ")" + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(getCount, null);
        return cursor.getCount();
    }

    public boolean insertData(String task, int year, int month, int day, int hour, int minute,
                              int second){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.COL_TASK, task);
        cv.put(DatabaseHelper.COL_YEAR, year);
        cv.put(DatabaseHelper.COL_MONTH, month);
        cv.put(DatabaseHelper.COL_DAY, day);
        cv.put(DatabaseHelper.COL_HOUR, hour);
        cv.put(DatabaseHelper.COL_MIN, minute);
        cv.put(DatabaseHelper.COL_SEC, second);

        Cursor cursor = getAllData();
       //cv.put(DatabaseHelper.COL_ID, cursor.getCount());

        return db.insert(DatabaseHelper.TABLE_NAME, null, cv) > -1;
    }

    public boolean deleteData(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(this.TABLE_NAME, COL_TASK + " = ?", new String[]{task}) > 0;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TASK + " TEXT UNIQUE,"
                + COL_YEAR + " INT,"
                + COL_MONTH + " INT,"
                + COL_DAY + " INT,"
                + COL_HOUR + " INT,"
                + COL_MIN + " INT,"
                + COL_SEC + " INT"
                + ");";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
