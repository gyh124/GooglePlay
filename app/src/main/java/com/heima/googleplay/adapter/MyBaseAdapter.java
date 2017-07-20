package com.heima.googleplay.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/11.
 * 基类不清楚adapter要展示的数据类型，使用泛型代替
 * 由于每个adapter的getView()方法获得的视图不同，需要交给子类实现，此基类不重写
 * 如果一个类没有完全重写父类的所有抽象方法，那么这个类是抽象类
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> mDatas;

    public MyBaseAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas!=null?mDatas.size():0;
    }

    @Override
    public Object getItem(int i) {
        return mDatas!=null?mDatas.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
