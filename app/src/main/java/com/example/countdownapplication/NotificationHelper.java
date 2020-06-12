package com.example.countdownapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static String channelID;
    public static String channelName;

    private NotificationManager mManager;

    public NotificationHelper(Context base, String channelID) {
        super(base);
        this.channelID = channelID;
        channelName = "Channel " + channelID;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel(){
        NotificationChannel channel = new NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH );
        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager(){
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String message){

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Task scheduled due")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_watch_black_24dp);
    }
}
