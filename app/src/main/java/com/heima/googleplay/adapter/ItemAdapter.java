package com.heima.googleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.heima.googleplay.activity.DetailActivity;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.holder.ItemHolder;
import com.heima.googleplay.utils.UIUtils;

import java.util.List;

/**
 * HomeAdapter、AppAdapter、GameAdapter的基类
 */

public class ItemAdapter extends SuperBaseAdapter<ItemBean> {
    public ItemAdapter(List<ItemBean> datas, AbsListView absListView) {
        super(datas, absListView);
    }

    @Override
    public BaseHolder getSpecialBaseHolder(int i) {
        return new ItemHolder();
    }
    /**
     * 是否需要加载更多的数据
     */

    @Override
    public boolean hasLoadMore() {
        return true;
    }

    @Override
    public void onNormalItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ItemBean itemBean = (ItemBean) mDatas.get(i);
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName",itemBean.packageName);
        UIUtils.getContext().startActivity(intent);
    }
}
