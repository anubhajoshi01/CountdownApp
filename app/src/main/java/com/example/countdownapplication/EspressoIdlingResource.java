package com.example.countdownapplication;

public class EspressoIdlingResource {

    private static SimpleCountingIdlingResource mIdlingResource = new SimpleCountingIdlingResource();

    public static void increment(){
        mIdlingResource.increment();
    }

    public static void decrement(){
        mIdlingResource.decrement();
    }

    public static SimpleCountingIdlingResource getIdlingResource(){
        return mIdlingResource;
    }
}
