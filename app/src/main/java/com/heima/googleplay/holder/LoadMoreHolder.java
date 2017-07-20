package com.heima.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.googleplay.R;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GuoYaHui on 2017/7/14.
 */

public class LoadMoreHolder extends BaseHolder<Integer> {
//    @BindView(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
//    @BindView(R.id.item_loadmore_tv_retry)
    TextView mItemLoadmoreTvRetry;
//    @BindView(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;
    public static final int LOADINGMORE = 0;//正在加载更多
    public static final int ERROR = 1;//加载失败，点击重试
    public static final int NONE = 2;//没有加载更多
    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        mItemLoadmoreContainerLoading = mHolderView.findViewById(R.id.item_loadmore_container_loading);
        mItemLoadmoreTvRetry = mHolderView.findViewById(R.id.item_loadmore_tv_retry);
        mItemLoadmoreContainerRetry = mHolderView.findViewById(R.id.item_loadmore_container_retry);
        return mHolderView;
    }

    /**
     *
     * @param state 根据state刷新ui
     */
    @Override
    public void refreshHolderView(Integer state) {
        //首先隐藏所有的视图
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (state){
            case LOADINGMORE:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case NONE:
                break;
        }

    }
}
