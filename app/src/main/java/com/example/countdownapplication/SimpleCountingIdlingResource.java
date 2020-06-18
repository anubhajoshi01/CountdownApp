package com.example.countdownapplication;

import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCountingIdlingResource implements IdlingResource {

    private final String mResourceName = this.getClass().getName();
    private final AtomicInteger counter = new AtomicInteger(0);
    private volatile ResourceCallback mCallback;

    @Override
    public String getName() {
        return mResourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    public void increment(){
        counter.getAndIncrement();
    }

    public void decrement(){
        int counterVal =  counter.getAndDecrement();

        if(counterVal == 0){
            if (mCallback != null){
                mCallback.onTransitionToIdle();
            }
        }
    }
}
