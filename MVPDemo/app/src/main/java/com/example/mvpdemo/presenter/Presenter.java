package com.example.mvpdemo.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.mvpdemo.model.Model;
import com.example.mvpdemo.view.IView;

/**
 * Created by TZL
 * on 2020/11/12
 * Description:
 */
public class Presenter implements IPresenter, Model.LoadDataCallback {

    private final IView mView;
    private final Model mModel;
    private Context mContext;

    public Presenter(IView view, Context context){
        mView = view;
        mModel = new Model();
        mContext = context;
    }

    @Override
    public void success(String data) {
        mView.showData(data);
    }

    @Override
    public void failure() {
        Toast.makeText(mContext,"获取数据失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadData() {
        mView.showLoadingProgress("加载数据中.....");
        mModel.getData(Presenter.this);
    }
}
