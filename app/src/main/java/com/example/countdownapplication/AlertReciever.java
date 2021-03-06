package com.example.countdownapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm", "Alert reciever");

        if(intent.hasExtra("channelID") && intent.hasExtra("taskName")
            && intent.hasExtra("taskID")){
            Log.d("Alarm", "extras from intent recieved");
            String channelID = intent.getStringExtra("channelID");
            String taskName = intent.getStringExtra("taskName");
            int taskID = intent.getIntExtra("taskID", 1);
            NotificationHelper notificationHelper = new NotificationHelper(context, channelID);
            NotificationCompat.Builder builder = notificationHelper.getChannelNotification(taskName);

            DatabaseHelper db = new DatabaseHelper(context);
            db.deleteData(taskName);

            notificationHelper.getManager().notify(taskID, builder.build());

        }
    }
}
