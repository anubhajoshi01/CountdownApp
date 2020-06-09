package com.example.countdownapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewTaskActivity extends AppCompatActivity {

    private TextView yourTaskTv, countdownTv;
    private String task = "";
    private int[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        yourTaskTv = (TextView)findViewById(R.id.tv_your_task);
        countdownTv = (TextView)findViewById(R.id.tv_countdown);

        if(getIntent().hasExtra("task")){
            task = getIntent().getStringExtra("task");
            data = getData(task);
        }



    }

    private int[] getData(String task){

        DatabaseHelper db = new DatabaseHelper(ViewTaskActivity.this);
        Cursor cursor = db.getAllData();

        while(cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TASK)).equals(task)){
                return new int[]{cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_YEAR)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MONTH)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DAY)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_HOUR)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MIN)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_SEC))};
            }
        }
        return new int[] {0};
    }

}
