package com.heima.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.bean.CategoryBean;
import com.heima.googleplay.utils.UIUtils;

/**
 * Created by GuoYaHui on 2017/7/16.
 */

public class CategorytitleHolder extends BaseHolder<CategoryBean> {

    private TextView mTitle;

    @Override
    public View initHolderView() {
        mTitle = new TextView(UIUtils.getContext());
        int padding = UIUtils.dp2px(5);
        mTitle.setPadding(padding,padding,padding,padding);
        return mTitle;
    }

    @Override
    public void refreshHolderView(CategoryBean data) {
        mTitle.setText(data.title);
        mTitle.setTextColor(Color.BLACK);
    }
}
