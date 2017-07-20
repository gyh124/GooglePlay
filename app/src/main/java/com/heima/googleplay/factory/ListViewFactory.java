package com.heima.googleplay.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.heima.googleplay.utils.UIUtils;

/**
 * Created by GuoYaHui on 2017/7/14.
 * listview的工厂类
 */

public class ListViewFactory {
    public static ListView createListView(){
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDividerHeight(0);//去调分割线
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFastScrollEnabled(true);//快速滑动滚动条
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));//去掉默认的点击效果
        return listView;
    }
}
