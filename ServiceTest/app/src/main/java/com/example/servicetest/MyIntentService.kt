package com.example.servicetest

import android.app.IntentService
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {
    override fun onHandleIntent(p0: Intent?) {
        Log.d(TAG, "onHandleIntent: Thread id is ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: executed")
    }
}