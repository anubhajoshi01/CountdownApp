package com.example.countdownapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.hasExtra("channelID") && intent.hasExtra("taskName")
            && intent.hasExtra("taskID")){
            String channelID = intent.getStringExtra("channelID");
            String taskName = intent.getStringExtra("taskName");
            int taskID = intent.getIntExtra("taskID", 1);
            NotificationHelper notificationHelper = new NotificationHelper(context, channelID);
            NotificationCompat.Builder builder = notificationHelper.getChannelNotification(taskName);

            notificationHelper.getManager().notify(taskID, builder.build());

            DatabaseHelper db = new DatabaseHelper(context);
            db.deleteData(taskName);
        }
    }
}
