package com.example.mvpdemo.model;

/**
 * Created by TZL
 * on 2020/11/12
 * Description: 实现IModel接口，负责实际的数据获取操作（数据库读取，网络加载等），然后通过自己的接口（LoadDataCallback）反馈出去
 */
public class Model implements IModel {

    @Override
    public void getData(LoadDataCallback callback) {
        new Thread(()->{
            try{
                Thread.sleep(3000);
                String data = "Hello World";
                callback.success(data);
            }catch (InterruptedException e){
                callback.failure();
            }
        }).start();
    }

    public interface LoadDataCallback{
        void success(String taskId);
        void failure();
    }
}
