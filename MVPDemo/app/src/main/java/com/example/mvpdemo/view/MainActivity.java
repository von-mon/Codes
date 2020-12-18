package com.example.mvpdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvpdemo.R;
import com.example.mvpdemo.presenter.IPresenter;
import com.example.mvpdemo.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements IView {

    private TextView mShowText;
    private IPresenter mPresenter;

    private final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new Presenter(MainActivity.this,MainActivity.this);

        Button mTest = findViewById(R.id.btn_getData);
        mShowText = findViewById(R.id.showText);

        mTest.setOnClickListener(view -> {
            mPresenter.loadData();
        });


    }

    @Override
    public void showLoadingProgress(String message) {

        handler.post(()->{
            mShowText.setText(message);
        });

    }

    @Override
    public void showData(String text) {
        handler.post(()->{
            mShowText.setText(text);
        });

    }
}