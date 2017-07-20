package com.heima.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.utils.UIUtils;

/**
 * Created by GuoYaHui on 2017/7/17.
 * 应用详情页面：安全部分
 */

public class DetailSafeHolder extends BaseHolder<ItemBean> {

    private TextView mTv;

    @Override
    public View initHolderView() {
        mTv = new TextView(UIUtils.getContext());
        mTv.setTextColor(Color.BLUE);

        return mTv;
    }

    @Override
    public void refreshHolderView(ItemBean data) {

        mTv.setText(data.des);

    }
}
