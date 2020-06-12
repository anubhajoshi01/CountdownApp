package com.example.countdownapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.Calendar;

public class AddtaskActivity extends AppCompatActivity implements
        View.OnClickListener {

    private Spinner monthSpinner, daySpinner, hourSpinner, minuteSpinner, secondSpinner;
    private Button addButton;


    DatabaseHelper mDbHelper;
   public SQLiteDatabase mDb;

    private EditText setYearEd, enterTaskEd;

    private int monthSelected, daySelected, hourSelected, minuteSelected, secondSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        mDbHelper = new DatabaseHelper(AddtaskActivity.this);
        //SQLiteDatabase mDb = new DatabaseHelper(AddtaskActivity.this);

        setYearEd = (EditText)findViewById(R.id.ed_setyear);
        enterTaskEd = (EditText)findViewById(R.id.ed_entertask);


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
        final ArrayList<Integer> secondList = new ArrayList<>();

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
            minuteList.add(i-1);
            secondList.add(i-1);
        }

        ArrayAdapter<Integer> monthListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, monthList);
        monthSpinner.setAdapter(monthListAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
              String msg = monthSelected + " ";
                Toast.makeText(AddtaskActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monthSelected = 1;
            }
        });

        ArrayAdapter<Integer> dayListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, dayList);
        daySpinner.setAdapter(dayListAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daySelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
                Toast.makeText(AddtaskActivity.this, "Day " + daySelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                daySelected = 1;

            }
        });

        ArrayAdapter<Integer> hourListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, hourList);
        hourSpinner.setAdapter(hourListAdapter);
        hourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hourSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
              //  Toast.makeText(AddtaskActivity.this, "hour "+ hourSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hourSelected = 24;
            }
        });

        ArrayAdapter<Integer> minuteListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, minuteList);
        minuteSpinner.setAdapter(minuteListAdapter);
        minuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                minuteSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
              //  Toast.makeText(AddtaskActivity.this, "min "+ minuteSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                minuteSelected = 1;
            }
        });

        ArrayAdapter<Integer> secondListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, secondList);
        secondSpinner.setAdapter(secondListAdapter);
        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secondSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
              //  Toast.makeText(AddtaskActivity.this, "sec "+secondSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                secondSelected = 1;
            }
        });



       // ArrayAdapter<Integer> monthsAdapter = new ArrayAdapter<>(this, monthsList);

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

                if(taskExists(taskName)){
                    String msg = "Task already scheduled";
                    Toast.makeText(AddtaskActivity.this, msg, Toast.LENGTH_SHORT).show();
                }

                else if(mDbHelper.insertData(taskName, year, monthSelected, daySelected, hourSelected,
                        minuteSelected, secondSelected)){
                    String logmsg = taskName + " " + year + " " + monthSelected + " " + daySelected +
                            " " + hourSelected + " " + minuteSelected + " " + secondSelected;
                    Log.d("Task Added", logmsg);
                  //  int count = DatabaseHelper.getEntryCount(mDb);
                   // Log.d("Entry count", count + " ");
                    Toast.makeText(AddtaskActivity.this, logmsg
                            , Toast.LENGTH_LONG)
                    .show();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthSelected);
                    calendar.set(Calendar.DAY_OF_MONTH, daySelected);
                    calendar.set(Calendar.HOUR_OF_DAY, hourSelected);
                    calendar.set(Calendar.MONTH, monthSelected);
                    calendar.set(Calendar.SECOND, secondSelected);

                    Cursor cursor = mDbHelper.getAllData();
                    int id = 0;
                    while(cursor.moveToNext()){
                        if(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TASK))
                            .equals(taskName)){
                            id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                        }
                    }

                    if(calendar.before(Calendar.getInstance())){
                        startAlarm(id, calendar, taskName);

                        Intent mainIntent = new Intent(AddtaskActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                    else{
                        Toast.makeText(AddtaskActivity.this, "Please enter a future date",
                                Toast.LENGTH_SHORT).show();
                    }


                   // mDb.close();
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

    private boolean taskExists(String task){

        Cursor cursor = mDbHelper.getAllData();

        if(cursor.getCount() == 0){
            return false;
        }

        while(cursor.moveToNext()){
            String thistask = cursor.getString(0);
            if(thistask.equals(task)){
                return true;
            }
        }
        return false;
    }

    public void startAlarm(int requestCode, Calendar calendar, String taskName){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AddtaskActivity.this, AlertReciever.class);
        intent.putExtra("channelID", taskName);
        intent.putExtra("taskName", taskName);
        intent.putExtra("taskID", requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddtaskActivity.this,
                requestCode, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }
}
