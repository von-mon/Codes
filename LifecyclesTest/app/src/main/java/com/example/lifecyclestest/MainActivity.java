package com.example.lifecyclestest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public MyObserver myObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myObserver = new MyObserver(getLifecycle());
        getLifecycle().addObserver(myObserver);
        Lifecycle.State currentState = myObserver.getCurrentState();
        Log.d("MainActivityState", "onCreate: "+currentState);
    }
}