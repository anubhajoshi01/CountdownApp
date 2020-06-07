package com.example.countdownapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class AddtaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    private Spinner monthSpinner, daySpinner, hourSpinner, minuteSpinner, secondSpinner;
    private Button addButton;
    public SQLiteDatabase mDb;
    private EditText setYearEd, enterTaskEd;

    private int monthSelected, daySelected, hourSelected, minuteSelected, secondSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        setYearEd = (EditText)findViewById(R.id.ed_setyear);
        enterTaskEd = (EditText)findViewById(R.id.ed_entertask);

        DatabaseHelper mDbHelper = new DatabaseHelper(AddtaskActivity.this);
        mDb = mDbHelper.getWritableDatabase();

        addButton = findViewById(R.id.btn_add);
        addButton.setOnClickListener(this);

        monthSpinner = findViewById(R.id.spinner_month);
        daySpinner = findViewById(R.id.spinner_day);
        hourSpinner = findViewById(R.id.spinner_hour);
        minuteSpinner = findViewById(R.id.spinner_minute);
        secondSpinner = findViewById(R.id.spinner_second);

        ArrayList<Integer> monthList = new ArrayList<>();
        ArrayList<Integer> dayList = new ArrayList<>();
        ArrayList<Integer> hourList = new ArrayList<>();
        ArrayList<Integer> minuteList = new ArrayList<>();
        ArrayList<Integer> secondList = new ArrayList<>();

        for(int i = 1; i <= 60; i++){
            if(i <= 12){
                monthList.add(i);
            }
            if(i <= 24){
                hourList.add(i);
            }
            if(i <= 31){
                dayList.add(i);
            }
            minuteList.add(i);
            secondList.add(i);
        }

        ArrayAdapter<Integer> monthListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, monthList);
        monthSpinner.setAdapter(monthListAdapter);
        monthSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> dayListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, dayList);
        daySpinner.setAdapter(dayListAdapter);
        daySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> hourListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, hourList);
        hourSpinner.setAdapter(hourListAdapter);
        hourSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> minuteListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, minuteList);
        minuteSpinner.setAdapter(minuteListAdapter);
        minuteSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> secondListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, secondList);
        secondSpinner.setAdapter(secondListAdapter);
        secondSpinner.setOnItemSelectedListener(this);



       // ArrayAdapter<Integer> monthsAdapter = new ArrayAdapter<>(this, monthsList);

    }
    private long addValuesToTable(String taskName, int year, int month, int day,
                                  int hour, int minute, int second){

        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.COL_TASK, taskName);
        cv.put(DatabaseHelper.COL_YEAR, year);
        cv.put(DatabaseHelper.COL_MONTH, month);
        cv.put(DatabaseHelper.COL_HOUR, hour);
        cv.put(DatabaseHelper.COL_MIN, minute);
        cv.put(DatabaseHelper.COL_SEC, second);

        return mDb.insert(DatabaseHelper.TABLE_NAME, null, cv);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(id == R.id.spinner_month){
            monthSelected = (int)(monthSpinner.getItemAtPosition(position));
            Toast.makeText(this, monthSelected, Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.spinner_day){
            daySelected = (int)(daySpinner.getItemAtPosition(position));
            Toast.makeText(this, daySelected, Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.spinner_hour){
            hourSelected = (int)(hourSpinner.getItemAtPosition(position));
            Toast.makeText(this, hourSelected, Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.spinner_minute){
            minuteSelected = (int)(hourSpinner.getItemAtPosition(position));
            Toast.makeText(this, minuteSelected, Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.spinner_second){
            secondSelected = (int)(secondSpinner.getItemAtPosition(position));
            Toast.makeText(this, secondSelected, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int thisId = v.getId();

        if(thisId == R.id.btn_add){

            try{
                String taskName = enterTaskEd.getText().toString();

                if(taskName.length() == 0 || taskName.isEmpty()){
                    String emptyMsg = "Please enter a task";
                    Toast.makeText(AddtaskActivity.this, emptyMsg, Toast.LENGTH_SHORT).show();
                }

                int year = Integer.parseInt(setYearEd.getText().toString());

                if(addValuesToTable(taskName, year, monthSelected, daySelected, hourSelected,
                        minuteSelected, secondSelected) > -1){
                    Toast.makeText(AddtaskActivity.this, "Made task", Toast.LENGTH_SHORT)
                    .show();
                    String logmsg = taskName + " " + year + " " + monthSelected + " " + daySelected +
                            " " + hourSelected + " " + minuteSelected + " " + secondSelected;
                    Log.d("Task Added", logmsg);
                    mDb.close();
                }
                else{
                    Toast.makeText(AddtaskActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            catch (NumberFormatException e){
                String msg = "Please enter a valid year";
                Toast.makeText(AddtaskActivity.this, msg, Toast.LENGTH_LONG).show();
            }


        }
    }
}
