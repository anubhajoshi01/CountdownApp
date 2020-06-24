package com.example.countdownapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ViewTaskActivity extends AppCompatActivity {

    private TextView yourTaskTv, countdownTv, showDateTv, countdownTimeTv;
    private String task = "";
    private int[] data;
    private CountDownTimer countDownTimer;
    private long timeLeftInMs;
    private LocalDate dateDue, dateNow;
   // private int secnow, minnow, hournow, daynow, monthnow, yearnow;
   //public int dayleft, monthleft, yearleft;
    Calendar calendar = Calendar.getInstance();
    private Calendar now, due = Calendar.getInstance();
    //private static final int TIME_TO_SUBTRACT = ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        yourTaskTv = (TextView)findViewById(R.id.tv_your_task);
        countdownTv = (TextView)findViewById(R.id.tv_countdown_date);
        showDateTv = (TextView)findViewById(R.id.tv_show_date);
        countdownTimeTv = (TextView)findViewById(R.id.tv_countdown_time);

        Intent intent = getIntent();
        Log.d("recieve intent", ">>>>>>>>>>>>>> Recieved intent");
        if(intent.hasExtra("task")){
            Log.d("has extra", ">>>>>>>>>> Intent has extra");
            task = getIntent().getStringExtra("task");
            Log.d("has extra", ">>>>>>>>>> Intent has extra task");
            yourTaskTv.setText(task);
            data = getData(task);
            Log.d("has extra", ">>>>>>>>>> Intent data recieved");

            showDateTv.setText(data[0] + "/" + data[1] + "/" + data[2]);
            Log.d("data", ">>>>>>>" + data[0] + " " + data[1] + " "
                + data[2] + " " + data[3] + " " + data[4] + " " + data[5]);

            due.set(Calendar.YEAR, data[0]);
            due.set(Calendar.MONTH, (data[1]-1));
            due.set(Calendar.DAY_OF_MONTH, data[2]);
            due.set(Calendar.HOUR_OF_DAY, data[3]);
            due.set(Calendar.MINUTE, data[4]);
            due.set(Calendar.SECOND, data[5]);

        }









      //  timeLeftInMs = second + (minute*60) + (hour*60*60) + (day*24*60*60) +
      //          getDaysInMonth(month, year)*24*60*60 + year;


        now = Calendar.getInstance();

        calendar.setTimeInMillis(due.getTimeInMillis() - now.getTimeInMillis());
       /* showDateTv.setText(String.valueOf(due.getTimeInMillis()) +" "+ String.valueOf(now.getTimeInMillis())
            + " " + String.valueOf(calendar.getTimeInMillis())); */
        /*
        yearnow = calendar.get(Calendar.YEAR);
        monthnow = calendar.get(Calendar.MONTH);
        daynow = calendar.get(Calendar.DAY_OF_MONTH);

        hournow = calendar.get(Calendar.HOUR_OF_DAY);
        minnow = calendar.get(Calendar.MINUTE);
        secnow = calendar.get(Calendar.SECOND);

        showDateTv.append(" " + yearnow + " "+ monthnow + " "+daynow + "Time:" + hournow + ":" + minnow
            + ":" + secnow);

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

        */
        timeLeftInMs = calendar.getTimeInMillis();
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
        Log.d("Task not found", ">>>>>>>>>>>> Task not found");
        return new int[] {0};
    }

    public static int getDaysInMonth(int month, int year){
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

    private void startNewTimer(long milliseconds){

        countDownTimer = new CountDownTimer(milliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {


               /* long time = timeLeftInMs/1000;
                long hourleft = time/3600;
                long minleft = time - (hourleft*3600)/60;
                long secleft = time - (minleft*60) - (hourleft*60);*/
               // String timeleft =  timeFormat.format(timeLeftInMs/1000);
               // int[] conversions = millisToTime(timeLeftInMs);

                long[] conversions = millisToTime(millisUntilFinished);
                String hms = conversions[1]+":"+conversions[2]+":"+conversions[3];
           /*     String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeLeftInMs),
                        TimeUnit.MILLISECONDS.toMinutes(timeLeftInMs) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeftInMs) % TimeUnit.MINUTES.toSeconds(1)); */
                countdownTv.setText(conversions[0] + "days");
                countdownTimeTv.setText(hms);
            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(ViewTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }



    private static long[] millisToTime(long millis){


        //Log.d("millistotime", ">>>>>>>>>>" + millis + " seconds init");
        long day = TimeUnit.MILLISECONDS.toDays(millis);
        //Log.d("millistotime", ">>>>>>>>>>"+day + " days");
        millis -= TimeUnit.DAYS.toMillis(day);
        //Log.d("millistotime", ">>>>>>>>>>"+millis + " seconds after days");
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        //Log.d("millistotime", ">>>>>>>>>>"+hours + " hours");
        millis -= TimeUnit.HOURS.toMillis(hours);
        //Log.d("millistotime", ">>>>>>>>>>"+millis + " seconds after hours");
        long minute = TimeUnit.MILLISECONDS.toMinutes(millis);
        //Log.d("millistotime", ">>>>>>>>>>"+minute + " minutes");
        millis -= TimeUnit.MINUTES.toMillis(minute);
        //Log.d("millistotime", ">>>>>>>>>>"+millis + "seconds");
        long second = TimeUnit.MILLISECONDS.toSeconds(millis);


        return new long[]{day, hours, minute, second};
    }
}
