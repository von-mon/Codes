/**
 * 版权所属TZL@2016080332072
 *
 * 基于android的学生成绩管理系统
 *
 * Date:2019\6\6
 */
package com.example.tangzhuolin.bean;


//定义用户类，用于接收用户数据
public class User {
    public String adminNumber;
    public String pwd;

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "adminNumber='" + adminNumber + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
