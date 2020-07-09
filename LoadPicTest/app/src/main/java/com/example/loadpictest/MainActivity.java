package com.example.loadpictest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getPic = findViewById(R.id.test);

        getPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectUtil.getPic();
            }
        });
    }
}
