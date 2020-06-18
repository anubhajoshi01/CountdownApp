package com.example.countdownapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView emptyWarningTv;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private static Context mContext;
    private MessageDelayer.DelayerCallback mMessageDelayer;

    private ArrayList<String> mTaskList = new ArrayList<>();
    private ArrayList<String> mTimeList = new ArrayList<>();
    private ArrayList<String> mDateList = new ArrayList<>();

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        this.initRecyclerView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT |
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("Swiped", "on swiped");
                String taskRemoved = mTaskList.get(viewHolder.getAdapterPosition());
                Log.d("taskremoved", taskRemoved);
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);

              /*  Cursor cursor = db.getAllData();
                int taskID = 0;
                while(cursor.moveToNext()){
                    if(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TASK)
                        ).equals(taskRemoved)){
                        taskID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    }
                }*/

                int taskID = -1;
                try {
                    EspressoIdlingResource.increment();
                    taskID = new AsyncTasks.CursorAsyncTask(MainActivity.this).execute(taskRemoved).get();
                    EspressoIdlingResource.decrement();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (taskID > -1) {
                    cancelAlarm(taskID);

                    db.deleteData(taskRemoved);

                    mTaskList.remove(viewHolder.getAdapterPosition());
                    mDateList.remove(viewHolder.getAdapterPosition());
                    mTimeList.remove(viewHolder.getAdapterPosition());
                    Log.d("Removing", "All removed");

                    mAdapter.notifyDataSetChanged();
                    Log.d("Notify", "Data set changed method ran");
                } else {
                    Toast.makeText(MainActivity.this, "Error removing task", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int thisid = v.getId();

        if (thisid == R.id.fab) {
            Intent addtaskActivityIntent = new Intent(MainActivity.this, AddtaskActivity.class);
            startActivity(addtaskActivityIntent);
        }
    }

    private void initRecyclerView() {

        emptyWarningTv = (TextView) findViewById(R.id.tv_emptywarning);

        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        Cursor cursor = db.getAllData();

        if (cursor.getCount() == 0) {
            emptyWarningTv.setVisibility(View.VISIBLE);
        } else {


            while (cursor.moveToNext()) {

                mTaskList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TASK)));
                mTimeList.add(format(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_HOUR)))
                        + ":" + format(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MIN))));

                mDateList.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_YEAR))
                        + "/" + format(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MONTH)))
                        + "/" + format(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DAY))));

            }

            mRecyclerView = (RecyclerView) findViewById(R.id.rv_tasklist);
            mAdapter = new RecyclerViewAdapter(mTimeList, mTaskList, mDateList, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        }
    }

    private void cancelAlarm(int requestCode) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    private static String format(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return String.valueOf(i);
    }

    public static Context getContext(){
        return mContext;
    }

}