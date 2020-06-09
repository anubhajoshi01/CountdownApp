package com.example.countdownapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView emptyWarningTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        this.initRecyclerView();
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

        if(thisid == R.id.fab){
            Intent addtaskActivityIntent = new Intent(MainActivity.this, AddtaskActivity.class);
            startActivity(addtaskActivityIntent);
        }
    }

    private void initRecyclerView(){

        emptyWarningTv = (TextView)findViewById(R.id.tv_emptywarning);

        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        Cursor cursor = db.getAllData();

        if(cursor.getCount() == 0){
            emptyWarningTv.setVisibility(View.VISIBLE);
        }
        else{

            ArrayList<String> taskList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> dateList = new ArrayList<>();

            while(cursor.moveToNext()){

                taskList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TASK)));
                timeList.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_HOUR))
                    + ":" + cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MIN)));

                dateList.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_YEAR))
                + "/" + cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MONTH))
                + "/" + cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DAY)));

            }

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_tasklist);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(timeList,taskList,dateList,MainActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
