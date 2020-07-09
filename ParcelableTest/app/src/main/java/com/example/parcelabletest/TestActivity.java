package com.example.parcelabletest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parcelabletest.bean.Book;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();

//       Book book = (Book) intent.getSerializableExtra("data");
       Book book = (Book) intent.getParcelableExtra("data");

        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);

        assert book != null;
        name.setText(book.getName());
        price.setText(book.getPrice());
    }
}
