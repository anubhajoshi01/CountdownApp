package com.example.countdownapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "scheduled_events_table";
    private static final String COL_1 = "Task";
    private static final String COL_2 = "Year";
    private static final String COL_3 = "Month";
    private static final String COL_4 = "Day";
    private static final String COL_5 = "Hour";
    private static final String COL_6 = "Minute";
    private static final String COL_7 = "Second";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " TEXT,"
                + COL_2 + " INT,"
                + COL_3 + " INT,"
                + COL_3 + " INT,"
                + COL_4 + " INT,"
                + COL_5 + " INT,"
                + COL_6 + " INT,"
                + COL_7 + " INT "
                + ");";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
