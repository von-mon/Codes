package com.example.mvpdemo.view;

/**
 * Created by TZL
 * on 2020/11/12
 * Description:View层接口---执行各种UI操作，定义的方法主要是给Presenter中来调用的
 */
public interface IView {

    void showLoadingProgress(String message);

    void showData(String text);
}
