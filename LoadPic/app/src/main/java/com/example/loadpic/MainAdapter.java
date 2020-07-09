package com.example.loadpic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> myList;
    private View view1;

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picItem);

        }
    }

    MainAdapter(List<String> myList, Context context){
        this.myList = myList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyViewHolder){
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            if(myList!=null&&myList.size()>0){
                final String url = myList.get(position);
                //String url1 = myList.get(position+1);
                Glide.with(context)
                        .load(url)
                        .into(myViewHolder.imageView);

                myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),LargeActivity.class);
                        intent.putExtra("url",myList.get(position));
                        view.getContext().startActivity(intent);
                    }
                });

            }
        }

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
