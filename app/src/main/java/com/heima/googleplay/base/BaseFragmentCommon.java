package com.heima.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public abstract class BaseFragmentCommon extends Fragment {
    /**
     *
     * @param savedInstanceState
     * fragment被创建
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
    }



    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 返回fragment所需的布局
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }



    /**
     *
     * @param savedInstanceState
     * 宿主activity被创建
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initData();
        initListener();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化fragment里面相关的监听，不知道如何具体添加事件的相关监听，交给子类实现，子类选择性实现
     */
    public void initListener(){

    }

    /**
     * 初始化fragment数据的加载
     * 不知道如何具体进行数据的加载，交给子类实现，子类选择性实现
     */
    public void initData(){

    }

    /**
     * 进行一些初始化的操作，在BaseFragmentCommon不知道进行什么样的初始化操作
     * 交给子类实现，子类选择性实现
     */
    public void init() {
    }
    
    /**
     *
     * @return
     * 初始化视图。返回给fragment进行展示
     * 在BaseFragmentCommon不知道如何具体初始化对应的视图，交给子类实现
     * 由于子类是必须实现，定义为抽象方法
     */
    public abstract View initView() ;
}
