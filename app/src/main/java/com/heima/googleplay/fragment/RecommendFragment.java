package com.heima.googleplay.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.utils.UIUtils;

import java.util.Random;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class RecommendFragment extends BaseFragment {


    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        SystemClock.sleep(2000);//模拟网络加载数据
        Random random = new Random();
        int i = random.nextInt(3);
        LoadingPagerController.LoadedResult[] arr = new LoadingPagerController.LoadedResult[]{LoadingPagerController.LoadedResult.SUCCESS,
                LoadingPagerController.LoadedResult.ERROR,LoadingPagerController.LoadedResult.EMPTY};
        return arr[i];//数据加载成功
    }

    /**
     *
     * @return 成功视图
     * 子类具体实现成功视图的加载，以及数据和视图的绑定
     * @called triggerLoadData()方法被调用的时候，而且数据加载完成，而且数据加载成功
     */
    @Override
    public View initSuccessView() {
        TextView successView = new TextView(UIUtils.getContext());
        successView.setGravity(Gravity.CENTER);
        successView.setText(getClass().getSimpleName());
        successView.setTextColor(Color.BLACK);
        return successView;
    }
}
