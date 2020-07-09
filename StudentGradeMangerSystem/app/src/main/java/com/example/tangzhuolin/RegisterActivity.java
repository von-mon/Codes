/*
 * 版权所属TZL@2016080332072
 * <p>
 * 基于android的学生成绩管理系统
 * <p>
 * Date:2019\6\6
 */
package com.example.tangzhuolin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tangzhuolin.bean.User;
import com.example.tangzhuolin.dbutils.CommonDatabase;

public class RegisterActivity extends Activity {
    CommonDatabase database;
    private EditText userNumber;
    private EditText setPwd;
    private EditText getPwd;
    //数据表名称
    private String TABLE_NAME = "admin";
    //用户类
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //实例化数据库
        database = new CommonDatabase(this);
        userNumber = findViewById(R.id.r_userNumber);
        setPwd = findViewById(R.id.r_userPassword);
        getPwd = findViewById(R.id.r_checkPassword);
        Button ok = findViewById(R.id.ok);
        Button cancel = findViewById(R.id.cancel);
        //点击确定按钮事件
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userNumber.getText().toString().isEmpty() || setPwd.getText().toString().isEmpty() || getPwd.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"输入为空",Toast.LENGTH_SHORT).show();
                } else if (isSame()) {
                    Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();

                } else if (setPwd.getText().toString().trim().equals(getPwd.getText().toString().trim())) { //如果两次输入的密码相等，则插入到数据库中
                    //插入数据
                    insertItems(v);
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    //销毁注册界面
                    finish();
                } else {    //不相等则提示用户输入错误
                    Toast.makeText(RegisterActivity.this,"两次输入密码不同",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //回到登录界面
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //向表中插入数据
    public void insertItems(View v) {
        try {
            //获取数据库操作权限
            SQLiteDatabase db = database.getWrite();
            //实例化用户类
            user = new User();
            //把用户输入的值转化为用户类实例
            user.adminNumber = String.valueOf(userNumber.getText());
            user.pwd = String.valueOf(setPwd.getText());
            //使用ContentValues类对数据进行封装
            ContentValues contentValues = new ContentValues();
            contentValues.put("userNumber", user.adminNumber);
            contentValues.put("passWord", user.pwd);
            //使用insert语句进行插入操作
            db.insert(TABLE_NAME, null, contentValues);
            //创建对话框提示用户注册成功
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            //创建对话框提示用户注册失败
            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            e.getMessage();
        }
    }

    //查找是否有相同的用户名
    boolean isSame() {
        SQLiteDatabase db = database.getRead();
        String KEY_NUMBER = "userNumber";
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_NUMBER},
                KEY_NUMBER + "=" + "'" + userNumber.getText().toString().trim() + "'",
                null, null, null, null);
        //获取集合的数据数量
        int Counts = cursor.getCount();
        cursor.close();
        return Counts > 0;
    }

}
