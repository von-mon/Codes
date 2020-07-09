package com.example.loadpic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.loadpic.MyApplication.getContext;

@SuppressLint("Registered")
public class LargeActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image);
        Intent intent = getIntent();
        ImageView imageView = findViewById(R.id.show_large);
        Glide.with(LargeActivity.this).load(intent.getStringExtra("url")).into(imageView);
    }
}
