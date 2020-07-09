/*
 * 版权所属TZL@2016080332072
 * <p>
 * 基于android的学生成绩管理系统
 * <p>
 * Date:2019\6\6
 */

package com.example.tangzhuolin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangzhuolin.adapter.ScoreAdapter;
import com.example.tangzhuolin.adapter.SearchAdapter;
import com.example.tangzhuolin.bean.Student;
import com.example.tangzhuolin.dbutils.CommonDatabase;

import java.util.ArrayList;

public class ScoreActivity extends Activity {
    private TextView number;
    private TextView identity;
    private ListView listView;
    //接收用户输入
    private EditText input;
    private static String TABLE_NAME = "student";
    private static String STU_NAME = "name";
    private static String STU_NUMBER = "number";
    private static String STU_SUBJECT = "subject";
    private static String STU_GRADE = "grade";
    private AlertDialog show;
    //数据库公共类
    CommonDatabase database;
    //学生列表
    ArrayList<Student> studentList;
    //学生类
    Student student;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        database = new CommonDatabase(this);
        input = new EditText(this);

        number = findViewById(R.id.number);
        identity = findViewById(R.id.identity);
        listView = findViewById(R.id.list_view);
        Button add = findViewById(R.id.add);
        Button back = findViewById(R.id.exit);
        Button delete = findViewById(R.id.delete);
        Button refresh = findViewById(R.id.refresh);
        Button search_number = findViewById(R.id.search_number);
        Button search_subject = findViewById(R.id.search_subject);
        Button modify = findViewById(R.id.modify);

        //显示成绩
        refreshData();

        //获取登录界面的值，并显示在头部
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        number.setText("用户名：" + bundle.getString("number"));
        identity.setText("身份：" + bundle.getString("identity"));

        /*
         * 查询功能：按学号查询、按课程名查询
         */

        //通过学号查找
        search_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                show = new AlertDialog.Builder(ScoreActivity.this)
                        .setTitle("请输入学号")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                searchByNumber(input.getText().toString().trim());

                                ((ViewGroup) input.getParent()).removeView(input);
                                show.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((ViewGroup) input.getParent()).removeView(input);
                                show.dismiss();
                            }
                        }).create();
                input.setText("");
                show.setView(input);
                show.show();
            }
        });

        //通过课程名查找
        search_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show = new AlertDialog.Builder(ScoreActivity.this)
                        .setTitle("请输入课程名")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                searchBySubject(input.getText().toString().trim());
                                ((ViewGroup) input.getParent()).removeView(input);
                                show.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((ViewGroup) input.getParent()).removeView(input);
                                show.dismiss();
                            }
                        })
                        .create();
                input.setText("");
                show.setView(input);
                show.show();
            }
        });

        /*
         * 添加功能
         */

        //添加数据
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                addData();
            }
        });

        /*
         * 修改功能
         */

        //修改数据库的值
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyData();
            }
        });

        /*
         * 刷新功能
         */

        //刷新列表并显示学生数据
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        /*
         * 删除功能
         */

        //删除数据
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        /*
         * 退出
         */

        //退出应用
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = database.getRead();
                db.close();
                finish();

            }
        });
    }


    /**
     * 因为跳转到其他页面后，再跳转回来时会出现空指针情况
     * 就是Bundle类接收不到值，从而中断程序的运行
     * 通过重写onActivityResult()方法来处理其他页面返回的值，并恢复成绩页面的值
     *
     * @param requestCode 请求标记
     * @param resultCode 结果标记
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            Bundle bundle = data.getExtras();
            assert bundle != null;
            number.setText(bundle.getString("number"));
            identity.setText(bundle.getString("subject"));
        }
    }

    //删除学生数据
    public void deleteData() {
        //使用覆盖添加方式，LayoutInflater为布局填充类
        LayoutInflater factory = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View layout = factory.inflate(R.layout.activity_delete, null);
        /* 从弹出框里获取输入框的值
         * 我们要获取的view的紧邻的父级的ViewGroup中调用findViewById方法
         * final EditText delete_number = layout.findViewById(R.id.delete_number);
         * 如果采用final EditText delete_number = findViewById(R.id.delete_number);
         * 这样获取的Editext的值就为空，因为这样我们获取的是另一个对象
         * 添加、查询、修改功能的实现都是依靠这种方法
         */
        final EditText delete_number = layout.findViewById(R.id.delete_number);
        final EditText delete_subject = layout.findViewById(R.id.delete_subject);
        new AlertDialog.Builder(ScoreActivity.this)
                .setTitle("请输入")
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            SQLiteDatabase db = database.getWrite();
                            db.delete(TABLE_NAME, STU_NUMBER + "=" + "'" + delete_number.getText().toString().trim() + "'" + " and " + STU_SUBJECT + "=" + "'" + delete_subject.getText().toString().trim() + "'",
                                    null);
                            //提示用户删除成功
                            Toast.makeText(ScoreActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            e.getMessage();
                            //提示用户删除失败
                            Toast.makeText(ScoreActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    //刷新显示列表
    public void refreshData() {
        SQLiteDatabase db = database.getRead();

        //读取数据库中student表中的数据
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from student", null);
        //初始化一个学生类列表
        studentList = new ArrayList<>();
        //遍历数据集合，存放到studentList中
        while (cursor.moveToNext()) {
            //实例化一个Student对象，用来存放数据的值
            student = new Student();
            student.stuName = cursor.getString(cursor.getColumnIndex(STU_NAME));
            student.stuNumber = cursor.getString(cursor.getColumnIndex(STU_NUMBER));
            student.stuSubject = cursor.getString(cursor.getColumnIndex(STU_SUBJECT));
            student.stuGrade = cursor.getString(cursor.getColumnIndex(STU_GRADE));
            studentList.add(student);  //添加到数组中
        }

        cursor.close();

        ScoreAdapter adapter = new ScoreAdapter(studentList,ScoreActivity.this);
        listView.setAdapter(adapter);
    }

    //按学号查询
    public void searchByNumber(String string) {
        LayoutInflater factory = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View layout = factory.inflate(R.layout.activity_search_number, null);
        ListView listView = layout.findViewById(R.id.list_search_number);
        database = new CommonDatabase(this);
        SQLiteDatabase db = database.getRead();
        studentList = new ArrayList<>();
        //读取数据库中student表中的数据
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from student where number " + "=" + "'" + string + "'", null);
        while (cursor.moveToNext()) {
            //实例化一个Student对象，用来存放数据的值
            student = new Student();
            student.stuName = cursor.getString(cursor.getColumnIndex(STU_NAME));
            student.stuNumber = cursor.getString(cursor.getColumnIndex(STU_NUMBER));
            student.stuSubject = cursor.getString(cursor.getColumnIndex(STU_SUBJECT));
            student.stuGrade = cursor.getString(cursor.getColumnIndex(STU_GRADE));
            studentList.add(student);  //添加到数组
        }

        cursor.close();
        //为ListView设置一个适配器
        SearchAdapter adapter = new SearchAdapter(studentList,ScoreActivity.this);
        listView.setAdapter(adapter);
        new AlertDialog.Builder(ScoreActivity.this)
                .setView(layout)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //恢复成绩页面的学生信息，因为用的是同一个布局文件，会修改ListView的适配器
                        refreshData();

                    }
                })
                .show();
    }

    //按课程名查询
    public void searchBySubject(String string) {
        LayoutInflater factory = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View layout = factory.inflate(R.layout.activity_search_subject, null);
        ListView listView = layout.findViewById(R.id.list_search);
        database = new CommonDatabase(this);
        SQLiteDatabase db = database.getRead();
        //读取数据库中student表中的数据
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from student where subject " + "=" + "'" + string + "'", null);
        //初始化一个学生类列表
        studentList = new ArrayList<>();
        //遍历数据集合，存放到studentList中
        while (cursor.moveToNext()) {
            //实例化一个Student对象，用来存放数据的值
            student = new Student();
            student.stuName = cursor.getString(cursor.getColumnIndex(STU_NAME));
            student.stuNumber = cursor.getString(cursor.getColumnIndex(STU_NUMBER));
            student.stuSubject = cursor.getString(cursor.getColumnIndex(STU_SUBJECT));
            student.stuGrade = cursor.getString(cursor.getColumnIndex(STU_GRADE));
            studentList.add(student);  //添加到数组中
        }

        //为ListView设置一个适配器
      SearchAdapter adapter = new SearchAdapter(studentList,ScoreActivity.this);
        listView.setAdapter(adapter);


       new AlertDialog.Builder(ScoreActivity.this).setTitle("学生成绩信息")
                .setView(layout)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //恢复成绩页面的学生信息，因为用的是同一个布局文件，会修改ListView的适配器
                        refreshData();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();


    }

    //修改数据
    public void modifyData() {
        LayoutInflater factory = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View layout = factory.inflate(R.layout.activity_modify, null);
        new AlertDialog.Builder(ScoreActivity.this)
                .setTitle("提示")
                .setView(layout)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = database.getWrite();
                        EditText old_number = layout.findViewById(R.id.md_old_number);
                        EditText old_subject = layout.findViewById(R.id.md_old_subject);
                        EditText new_number = layout.findViewById(R.id.md_new_number);
                        EditText new_subject = layout.findViewById(R.id.md_new_subject);
                        EditText new_name = layout.findViewById(R.id.md_new_name);
                        EditText new_grade = layout.findViewById(R.id.md_new_grade);
                        //获取用户输入的值
                        String number = old_number.getText().toString();
                        String subject = old_subject.getText().toString();
                        String number_new = new_number.getText().toString();
                        String subject_new = new_subject.getText().toString();
                        String name_new = new_name.getText().toString();
                        String grade_new = new_grade.getText().toString();
                        db.execSQL("update student set name = ?, number = ?, subject = ?, grade = ? where number = ? and subject = ? ",
                                new String[]{name_new, number_new, subject_new, grade_new, number, subject});
                        db.close();
                    }
                })
                .show();
    }

    //将用户输入的数据添加到数据库中
    public void addData() {
        LayoutInflater factory = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View layout = factory.inflate(R.layout.activity_add, null);
        try {
            //提示用户插入成功
            new AlertDialog.Builder(ScoreActivity.this).setTitle("添加数据")
                    .setView(layout)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //获取数据库操作权限
                            SQLiteDatabase db = database.getWrite();
                            //实例化Student类
                            student = new Student();
                            String TABLE_NAME = "student";
                            EditText stu_name = layout.findViewById(R.id.stu_name);
                            EditText stu_number = layout.findViewById(R.id.stu_number);
                            EditText stu_subject = layout.findViewById(R.id.stu_subject);
                            EditText stu_grade = layout.findViewById(R.id.stu_grade);
                            //接收用户输入的值转化为Student类实例
                            student.stuName = String.valueOf(stu_name.getText());
                            student.stuNumber = String.valueOf(stu_number.getText());
                            student.stuSubject = String.valueOf(stu_subject.getText());
                            student.stuGrade = String.valueOf(stu_grade.getText());
                            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from student where number " + "=" + "'" + student.stuNumber + "'", null);
                            String name = "test";
                            while (cursor.moveToNext()) {
                                name = cursor.getString(cursor.getColumnIndex("name"));
                            }
                            //将数组光标移至第一个数
                            if (student.stuNumber.isEmpty() || student.stuGrade.isEmpty() || student.stuSubject.isEmpty() || student.stuName.isEmpty()) {
                                Toast.makeText(ScoreActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                            } else if (student.stuGrade.isEmpty()) {
                                //判断成绩的范围
                                int grade = Integer.parseInt(student.stuGrade);
                                if (grade < 0 || grade > 100) {
                                    Toast.makeText(ScoreActivity.this, "成绩输入错误", Toast.LENGTH_SHORT).show();
                                }
                            } else if (!(name.equals(student.stuName)) && cursor.getCount() > 0) {  //判断学号是否存在
                                Toast.makeText(ScoreActivity.this, "学号输入错误", Toast.LENGTH_SHORT).show();
                            } else {
                                //使用ContentValues类对数据进行封装
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", student.stuName);
                                contentValues.put("number", student.stuNumber);
                                contentValues.put("subject", student.stuSubject);
                                contentValues.put("grade", student.stuGrade);
                                //插入student表中
                                db.insert(TABLE_NAME, null, contentValues);

                                Toast.makeText(ScoreActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();
        } catch (SQLException e) {
            e.getMessage();
            Toast.makeText(ScoreActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }
}
