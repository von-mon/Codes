package com.example.parcelabletest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parcelabletest.bean.Book;

import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button parcelable = findViewById(R.id.parcelable);

        parcelable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                Book book = new Book();

                book.setName("第一行代码");
                book.setPrice("89");

                //传递对象
                intent.putExtra("data",book);
                startActivity(intent);
            }
        });
    }
}
