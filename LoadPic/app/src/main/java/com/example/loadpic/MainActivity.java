package com.example.loadpic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Button getPic = findViewById(R.id.test);

        MyTask myTask = new MyTask();
        myTask.execute();

//        getPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //ConnectUtil.getPic(0);
//            }
//        });
    }

    class MyTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://test-web.blackbirdsport.com/public/api/pics/page?pageNo=0")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String temp = Objects.requireNonNull(response.body()).string();
                Gson gson = new Gson();
                Result result = gson.fromJson(temp, Result.class);
                List<List<String>> list = result.getContent();
                List<String> list1 = new ArrayList<>();
                //List<String> list2 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    list1.addAll(list.get(i));
                }
                Log.d(TAG, "run: " + list1.size());
                return list1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            setContentView(R.layout.activity_main);
            RecyclerView recyclerView = findViewById(R.id.picDisplay);
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 6);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position % 6 == 0 ) {
                        return 6;
                    } else if (position >= 1 && position <= 2) {
                        return 3;
                    } else if (position >=3 && position <= 5) {
                        return 2;
                    } else {
                        return 2;
                    }
                }
            });
            recyclerView.setLayoutManager(layoutManager);
            MainAdapter adapter = new MainAdapter(strings, MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
    }
}
