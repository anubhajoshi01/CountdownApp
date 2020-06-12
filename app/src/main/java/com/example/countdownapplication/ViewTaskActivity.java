package com.example.countdownapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ViewTaskActivity extends AppCompatActivity {

    private TextView yourTaskTv, countdownTv, showDateTv;
    private String task = "";
    private int[] data;
    private CountDownTimer countDownTimer;
    private long timeLeftInMs;
    private int secnow, minnow, hournow, daynow, monthnow, yearnow;
   public int dayleft, monthleft, yearleft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        yourTaskTv = (TextView)findViewById(R.id.tv_your_task);
        countdownTv = (TextView)findViewById(R.id.tv_countdown);
        showDateTv = (TextView)findViewById(R.id.tv_show_date);



        if(getIntent().hasExtra("task")){
            task = getIntent().getStringExtra("task");
            yourTaskTv.setText(task);
            data = getData(task);
        }

        showDateTv.setText(data[0] + "/" + data[1] + "/" + data[2]);

        int second = data[5];
        int minute = data[4];
        int hour = data[3];
        dayleft = data[2];
        monthleft = data[1];
        yearleft = data[0];




      //  timeLeftInMs = second + (minute*60) + (hour*60*60) + (day*24*60*60) +
      //          getDaysInMonth(month, year)*24*60*60 + year;


        Calendar calendar = Calendar.getInstance();

        yearnow = calendar.get(Calendar.YEAR);
        monthnow = calendar.get(Calendar.MONTH);
        daynow = calendar.get(Calendar.DAY_OF_MONTH);

        hournow = calendar.get(Calendar.HOUR);
        minnow = calendar.get(Calendar.MINUTE);
        secnow = calendar.get(Calendar.SECOND);

        showDateTv.append(" " + yearnow + " "+ monthnow + " "+daynow);

        yearleft -= yearnow;
        if(monthleft-monthnow < 0){
            yearleft-=1;
            monthleft = 12 - (monthnow-monthleft);
        }
        else{
            monthleft -= monthnow;
        }

        if(dayleft -daynow < 0){
            monthleft -= 1;
            dayleft = getDaysInMonth(monthnow, yearnow) - dayleft + daynow;
        }
        else{
            dayleft -= daynow;
        }
        Log.d("Date", yearleft + " "+ monthleft + " " + dayleft);


        timeLeftInMs = (secnow + minnow*60 + hournow*60*60);
        startNewTimer(timeLeftInMs);


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

    private int getDaysInMonth(int month, int year){
        switch (month){
            case 1: return 31;
            case 2: return (year%4==0) ? 29 : 28;
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: return 0;
        }
    }

    private void startNewTimer(long seconds){

        countDownTimer = new CountDownTimer(seconds*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMs = millisUntilFinished;
               /* long time = timeLeftInMs/1000;
                long hourleft = time/3600;
                long minleft = time - (hourleft*3600)/60;
                long secleft = time - (minleft*60) - (hourleft*60);*/
               // String timeleft =  timeFormat.format(timeLeftInMs/1000);
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeLeftInMs),
                        TimeUnit.MILLISECONDS.toMinutes(timeLeftInMs) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeftInMs) % TimeUnit.MINUTES.toSeconds(1));
                countdownTv.setText(yearleft + "/" + monthleft + "/" + dayleft + ";"
                + hms);
            }

            @Override
            public void onFinish() {
                dayleft -= 1;
                if (dayleft == 0) {
                    dayleft = getDaysInMonth(monthleft, data[0]-yearleft);
                    monthleft -= 1;
                    if(monthleft == 0){
                        if(yearleft == 0){
                            startNewTimer((24-data[3]*60*60) + (60-data[4]*60) + 60-data[5]);
                        }
                        else {
                            yearleft-=1;
                            monthleft = 11;
                            dayleft = getDaysInMonth(monthleft, data[0] - yearleft);
                            startNewTimer(86400);
                        }

                    }
                    else{
                        startNewTimer(86400);
                    }
                }
                else {
                    startNewTimer(86400);
                }
            }
        }.start();
    }
}
