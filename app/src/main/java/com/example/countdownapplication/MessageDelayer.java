package com.example.countdownapplication;

import android.os.Handler;

import androidx.annotation.Nullable;

public class MessageDelayer {

    interface DelayerCallback{
        void onDone(String text);
    }

    static void processMessage(final String message, final DelayerCallback callback,
                               @Nullable final SimpleIdlingResource idlingResource){

        if(idlingResource != null){
            idlingResource.setIdleState(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.onDone(message);

                    if(idlingResource != null){
                        idlingResource.setIdleState(true);
                    }
                }

            }
        }, 2500);

    }


}
