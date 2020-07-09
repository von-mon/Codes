/*
 * 版权所属TZL@2016080332072
 * <p>
 * 基于android的学生成绩管理系统
 * <p>
 * Date:2019\6\6
 */

package com.example.tangzhuolin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangzhuolin.dbutils.CommonDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText userNumber;
    private EditText userPassWord;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String msg = "";
    private String DB_TABLE = "admin";
    private String KEY_NUMBER = "userNumber";
    private String KEY_PWD = "passWord";
    CommonDatabase database;
    private EditText input; //点击忘记密码，接收用户输入的值
    public String pwd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input = new EditText(this);
        Button login = findViewById(R.id.login);
        TextView register = findViewById(R.id.register);
        TextView forget = findViewById(R.id.forget_pwd);
        userNumber = findViewById(R.id.userNumber);
        userPassWord = findViewById(R.id.userPassword);
        radioGroup = findViewById(R.id.radiogroup);
        database = new CommonDatabase(this);
        //获取单选按钮的值
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //获取被选中按钮的Id值
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                msg = (String) radioButton.getText();
            }
        });

        /*
        跳转到成绩界面
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 获取数据库的值与用户输入的值进行比较
                 * 相同则跳转到成绩界面
                 * 不同则提示用户进行注册
                 */
                //用户已注册
                if (isUser()) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, ScoreActivity.class);
                    //向成绩页面传递学号和身份信息
                    Bundle bundle = new Bundle();
                    bundle.putString("number", userNumber.getText().toString().trim());
                    bundle.putString("identity", msg);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //销毁登录界面
                    finish();
                } else if (userNumber.getText().toString().isEmpty() || userPassWord.getText().toString().isEmpty()) {
                    //判断是否输入为空
                    Toast.makeText(LoginActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
                } else {
                    //用户未注册或密码错误
                    Toast.makeText(LoginActivity.this, "未注册或用户名密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //跳转到注册界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                //销毁登录界面
                finish();
            }
        });

        /*
        点击忘记密码事件
         */
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("请输入用户名")
                        .setView(input)
                        //监听"确认"按钮，返回结果
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                findPwd();
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("提示")
                                        .setMessage("您的密码是：" + pwd)
                                        .setPositiveButton("确认", null)
                                        .show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }


    /*
    判断用户是否注册或输入密码是否正确
     */
    boolean isUser() {
        try {
            SQLiteDatabase db = database.getRead();
            //查询是否有相同的用户名和密码
            @SuppressLint("Recycle") Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NUMBER, KEY_PWD},
                    KEY_NUMBER + "=" + "'" + userNumber.getText().toString().trim() + "'" + " AND " + KEY_PWD + "=" + "'" + userPassWord.getText().toString().trim() + "'",
                    null, null, null, null);
            //获取集合的数据数量
            int Counts = cursor.getCount();
            if (userNumber.getText().toString().isEmpty() || userPassWord.getText().toString().isEmpty()) {
                return false;
            }
            cursor.close();

            //如果集合为空，则说明没有相同的用户名和密码，返回false
            return Counts > 0;

        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
    }

    //找回密码
    public void findPwd() {

        //获取有相同用户名的数据
        try {

            SQLiteDatabase db = database.getRead();
            @SuppressLint("Recycle") Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NUMBER, KEY_PWD},
                    KEY_NUMBER + "=" + "'" + input.getText().toString().trim() + "'",
                    null, null, null, null);
            if (cursor.getCount() == 0 || !cursor.moveToFirst()) {
                return;
            }
            pwd = cursor.getString(cursor.getColumnIndex(KEY_PWD));
            //关闭数据库
            db.close();
        } catch (SQLException e) {
            e.getMessage();
            Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
