package com.example.lifecyclestest;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import static android.content.ContentValues.TAG;

public class MyObserver implements LifecycleObserver {

    private Lifecycle lifecycle;

    public MyObserver(Lifecycle lifecycle){
        this.lifecycle = lifecycle;
    }

    public Lifecycle.State getCurrentState(){
        return lifecycle.getCurrentState();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void activityStart(){
        Log.d(TAG, "activityStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void activityStop(){
        Log.d(TAG, "activityStop");
    }
}
