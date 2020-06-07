package com.example.countdownapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;

public class AddtaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner monthSpinner, daySpinner, hourSpinner, minuteSpinner, secondSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

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
        //monthSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> dayListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, dayList);
        daySpinner.setAdapter(dayListAdapter);
        //daySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> hourListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, hourList);
        hourSpinner.setAdapter(hourListAdapter);
        //hourSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> minuteListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, minuteList);
        minuteSpinner.setAdapter(minuteListAdapter);
        //minuteSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> secondListAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, secondList);
        secondSpinner.setAdapter(secondListAdapter);
      //  secondSpinner.setOnItemSelectedListener(this);



       // ArrayAdapter<Integer> monthsAdapter = new ArrayAdapter<>(this, monthsList);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
