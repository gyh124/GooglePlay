package com.heima.googleplay.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.protocol.RecommendProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.views.flyinout.ShakeListener;
import com.heima.googleplay.views.flyinout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class RecommendFragment extends BaseFragment {


    private List<String> mStringList;
    private ShakeListener mShakeListener;

    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        try {
            mStringList = recommendProtocol.loadData(0);
            return checkResult(mStringList);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    /**
     *
     * @return 成功视图
     * 子类具体实现成功视图的加载，以及数据和视图的绑定
     * @called triggerLoadData()方法被调用的时候，而且数据加载完成，而且数据加载成功
     */
    @Override
    public View initSuccessView() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        final RecommendAdapter adapter = new RecommendAdapter();
        stellarMap.setAdapter(adapter);
        //让首页展示
        stellarMap.setGroup(0,true);
        int padding = UIUtils.dp2px(10);
        stellarMap.setInnerPadding(padding,padding,padding,padding);

        //解决每页展示的数量不一样
        stellarMap.setRegularity(15,20);
        //摇一摇切换
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                int currentGroup = stellarMap.getCurrentGroup();
                if (currentGroup==adapter.getGroupCount()-1){
                    currentGroup=0;
                }else {
                    currentGroup++;
                }
                stellarMap.setGroup(currentGroup,true);
            }
        });

        return stellarMap;
    }

    @Override
    public void onResume() {
        if (mShakeListener!=null){
            mShakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener!=null){
            mShakeListener.pause();
        }
        super.onPause();
    }

    class RecommendAdapter implements StellarMap.Adapter{
        //每页多少个
        public static final int PAGERSIZE = 15;
        @Override
        public int getGroupCount() {//总共多少组
            if (mStringList.size()%PAGERSIZE==0){
                return mStringList.size()/PAGERSIZE;
            }else {
                return mStringList.size()/PAGERSIZE+1;
            }

        }

        @Override
        public int getCount(int group) {//每组多少个
            if (mStringList.size()%PAGERSIZE==0){
                return PAGERSIZE;
            }else {
                if (group==getGroupCount()-1){//最后一页
                    return mStringList.size()%PAGERSIZE;
                }else {
                    return PAGERSIZE;
                }

            }
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(UIUtils.getContext());
            Random random = new Random();
            tv.setTextSize(random.nextInt(5)+12);
            int alpha = 255;
            int red = random.nextInt(170)+30;
            int green = random.nextInt(170)+30;
            int blue = random.nextInt(170)+30;
            int color = Color.argb(alpha,red,green,blue);
            tv.setTextColor(color);
            int index = group*PAGERSIZE+position;

            String data = mStringList.get(index);
            tv.setText(data);



            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
