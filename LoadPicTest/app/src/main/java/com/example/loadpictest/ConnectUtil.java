package com.example.loadpictest;

import android.util.Log;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class ConnectUtil {

    public static void getPic(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder()
                        .url("https://test-web.blackbirdsport.com/public/api/pics/page")
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String temp = Objects.requireNonNull(response.body()).string();
                    Log.d(TAG, "run: " + temp);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
