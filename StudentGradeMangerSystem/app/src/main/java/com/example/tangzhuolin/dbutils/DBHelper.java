/**
 * 版权所属TZL@2016080332072
 *
 * 基于android的学生成绩管理系统
 *
 * Date:2019\6\6
 */

package com.example.tangzhuolin.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "user.db";
    private static int DATABASE_VERSION = 1;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建管理员数据表
        String TABLE_NAME_1 = "admin";
        String COLUMN_NAME_1 = "userNumber";
        String COLUMN_NAME_2 = "passWord";
        String sql = "CREATE TABLE " + TABLE_NAME_1 + " (" + COLUMN_NAME_1
                + " String, " + COLUMN_NAME_2 + " String " + ");";

        //创建学生数据表
        String TABLE_NAME_2 = "student";
        String STU_NAME = "name";
        String STU_NUMBER = "number";
        String STU_SUBJECT = "subject";
        String STU_GRADE = "grade";
        String sq2 = "CREATE TABLE " + TABLE_NAME_2 + " (" + STU_NAME
                + " String, " + STU_NUMBER + " String, " + STU_SUBJECT + " String, " + STU_GRADE + " String " + ");";
        db.execSQL(sql);
        db.execSQL(sq2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
