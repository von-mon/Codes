package com.example.tangzhuolin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tangzhuolin.R;
import com.example.tangzhuolin.bean.Student;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {

    private List<Student> studentList;
    private Context context;

     public ScoreAdapter(List<Student> studentList, Context context){
        this.studentList = studentList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        /**
         * 对ListView的优化，convertView为空时，创建一个新视图；
         * convertView不为空时，代表它是滚出屏幕，放入Recycler中的视图
         * 若需要用到其他layout，则用inflate(),同一视图，用findViewBy()
         */
        View v;
        if (view == null) {
            v = View.inflate(context, R.layout.activity_list, null);
        } else {
           v = view;
        }
        //取出每一行的数据
        Student stu = studentList.get(i);
        assert v != null;
        TextView score_name = v.findViewById(R.id.score_name);
        TextView score_number = v.findViewById(R.id.score_number);
        TextView score_subject = v.findViewById(R.id.score_subject);
        TextView score_grade = v.findViewById(R.id.score_grade);
        score_name.setText(stu.getStuName());
        score_number.setText(stu.getStuNumber());
        score_subject.setText(stu.getStuSubject());
        score_grade.setText(stu.getStuGrade());
        return v;
    }
}
