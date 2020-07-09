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

//建立公共类，方便对数据库操作

public class CommonDatabase{

    private DBHelper dbHelper;
    //实例化数据库对象
    public CommonDatabase(Context context){
        dbHelper = new DBHelper(context);
    }

    public SQLiteDatabase getWrite(){
        return dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getRead(){
        return dbHelper.getReadableDatabase();
    }
}
