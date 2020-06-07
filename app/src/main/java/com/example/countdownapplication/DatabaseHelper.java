package com.example.countdownapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "scheduled_events_table";
    public static final String COL_TASK = "Task";
    public static final String COL_YEAR = "Year";
    public static final String COL_MONTH = "Month";
    public static final String COL_DAY = "Day";
    public static final String COL_HOUR = "Hour";
    public static final String COL_MIN = "Minute";
    public static final String COL_SEC = "Second";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL_TASK + " TEXT,"
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
