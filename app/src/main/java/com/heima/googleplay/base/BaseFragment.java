package com.heima.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heima.googleplay.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by GuoYaHui on 2017/7/10.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPagerController mLoadingPagerController;

    public LoadingPagerController getLoadingPagerController() {
        return mLoadingPagerController;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPagerController = new LoadingPagerController(UIUtils.getContext()){

            @Override
            protected LoadedResult initData() {
                return BaseFragment.this.initData();
            }

            @Override
            public View initSuccessView() {
                return BaseFragment.this.initSuccessView();
            }
        };
        //触发加载数据
//        loadingPagerController.triggerLoadData();
        return mLoadingPagerController;
    }
    /**
     * @des 校验请求回来的数据
     */
    public LoadingPagerController.LoadedResult checkResult(Object resObj) {
        if (resObj == null) {
            return LoadingPagerController.LoadedResult.EMPTY;
        }
        //resObj -->List
        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPagerController.LoadedResult.EMPTY;
            }
        }
        //resObj -->Map
        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPagerController.LoadedResult.EMPTY;
            }
        }
        return LoadingPagerController.LoadedResult.SUCCESS;
    }

    /**
     *@des 数据和视图的绑定过程
     *@des 在BaseFragment中不是具体视图和具体数据，不知道如何进行数据和视图的绑定，交给子类实现，子类是必须实现的
     *@called triggerLoadData()方法被调用的时候，并且数据加载完成了，而且数据加载成功
     *
     */
    public abstract View initSuccessView();

    /**
     *
     * @return
     * @des  在Basefragment不知道如何具体的进行数据的加载，交给子类实现，子类是必须实现的
     * @called 在触发加载的时候调用，triggerLoadData()方法被调用的时候
     *
     */
    public abstract LoadingPagerController.LoadedResult initData();
}
