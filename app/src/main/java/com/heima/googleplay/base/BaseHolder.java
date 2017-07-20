package com.heima.googleplay.base;

import android.view.View;

/**
 * Created by GuoYaHui on 2017/7/13.
 * 提供视图
 * 接收数据
 * 数据和视图的绑定
 */

public abstract class BaseHolder<T> {
    public View mHolderView;//视图
    public T mData;//数据

    public BaseHolder(){
        //初始化根视图
        mHolderView = initHolderView();
        //找一个符合条件的holder绑定在mHolderView上
        mHolderView.setTag(this);
    }


    /**
     * 接收数据
     * 数据和视图的绑定
     */
    public void setDataAndRefreshHolderView(T data){
        //保存数据到成员变量
        mData = data;
        //进行数据和视图的绑定
        refreshHolderView(data);

    }
    /**
     *
     * @return 返回当前的根视图
     * 在baseholder中不知道如何具体的初始化根视图，交给子类实现，子类是必须实现，必须定为抽象的方法
     */
    public abstract View initHolderView();

    /**
     *
     * @param data
     * 数据和视图的绑定
     * 在BaseHolder不知道如何具体实现数据和视图的绑定，必须交给子类实现，子类是必须实现，必须定为抽象的方法
     */
    public abstract void refreshHolderView(T data);
}
