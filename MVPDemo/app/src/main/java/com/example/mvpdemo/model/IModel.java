package com.example.mvpdemo.model;

/**
 * Created by TZL
 * on 2020/11/12
 * Description: Model层接口---实现该接口的类负责实际的获取数据操作，如数据库读取、网络加载
 */
public interface IModel {

    void getData(Model.LoadDataCallback callback);

}
