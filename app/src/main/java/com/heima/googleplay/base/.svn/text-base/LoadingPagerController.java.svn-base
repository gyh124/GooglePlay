package com.heima.googleplay.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.heima.googleplay.R;
import com.heima.googleplay.utils.UIUtils;

/**
 * Created by GuoYaHui on 2017/7/9.
 * 提供视图（加载中，错误，加载成功，空视图）
 * 加载数据
 * 进行数据和视图的绑定
 */

public abstract class LoadingPagerController extends FrameLayout {

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public static final int STATE_LOADING = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int STATE_EMPTY = 3;
    public int mCurState = STATE_LOADING;
    private LoadDataTask mLoadDataTask;

    public LoadingPagerController(@NonNull Context context) {
        super(context);
        initCommonView();
    }

    /**
     * 初始化常规视图：加载中，错误，空视图三个静态视图
     */
    private void initCommonView() {
        //加载中视图
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);
        //错误视图
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerLoadData();
            }
        });

        //空视图
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        //根据当前页面的状态刷新ui
        refreshViewByState();
    }

    private void refreshViewByState() {

        if (mCurState==STATE_LOADING){
            mLoadingView.setVisibility(VISIBLE);
        }else {
            mLoadingView.setVisibility(GONE);
        }

        if (mCurState==STATE_ERROR){
            mErrorView.setVisibility(VISIBLE);
        }else {
            mErrorView.setVisibility(GONE);
        }

        if (mCurState==STATE_EMPTY){
            mEmptyView.setVisibility(VISIBLE);
        }else {
            mEmptyView.setVisibility(GONE);
        }
        if (mSuccessView==null && mCurState == STATE_SUCCESS){
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }
        if (mSuccessView!=null){
            if (mCurState==STATE_SUCCESS){
                mSuccessView.setVisibility(VISIBLE);
            }else {
                mSuccessView.setVisibility(GONE);
            }
        }
    }

    /**
     * 触发加载数据
     */
    public void triggerLoadData(){
        //若当前页面加载成功，则无需再次加载
        if (mCurState!=STATE_SUCCESS) {

            if (mLoadDataTask==null){//判断是否是正在加载数据中

                mCurState = STATE_LOADING;
                refreshViewByState();
                mLoadDataTask = new LoadDataTask();
                new Thread(mLoadDataTask).start();
            }
        }

    }
    class LoadDataTask implements Runnable{

        @Override
        public void run() {
            LoadedResult loadedResult = initData();
            mCurState = loadedResult.getState();
            //在主线程中更新ui
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    refreshViewByState();
                }
            });
            //加载完成后，置为null
            mLoadDataTask = null;

        }
    }

    protected abstract LoadedResult initData();

    public abstract View initSuccessView();

    public enum LoadedResult{
        SUCCESS(STATE_SUCCESS),ERROR(STATE_ERROR),EMPTY(STATE_EMPTY);
        private int state;

        LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
