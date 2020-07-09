/**
 * 版权所属TZL@2016080332072
 *
 * 基于android的学生成绩管理系统
 *
 * Date:2019\6\6
 */

package com.example.tangzhuolin.bean;

/**
 * 建立学生类，存放从数据库中读出的学生信息
 */
public class Student {
    //学号
    public String stuNumber;
    //姓名
    public String stuName;
    //课程
    public String stuSubject;
    //成绩
    public String stuGrade;

    public String getStuNumber() {
        return stuNumber;
    }

    public String getStuName() {
        return stuName;
    }

    public String getStuSubject() {
        return stuSubject;
    }

    public String getStuGrade() {
        return stuGrade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuNumber='" + stuNumber + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuSubject='" + stuSubject + '\'' +
                ", stuGrade='" + stuGrade + '\'' +
                '}';
    }
}
