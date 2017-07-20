package com.heima.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.SystemClock;
import android.util.AndroidException;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.protocol.HotProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.views.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class HotFragment extends BaseFragment {


    private List<String> mStringList;

    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        HotProtocol hotProtocol = new HotProtocol();
        try {
            mStringList = hotProtocol.loadData(0);
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
        ScrollView scrollView = new ScrollView(UIUtils.getContext());


        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (int i = 0; i <mStringList.size() ; i++) {
            String data = mStringList.get(i);
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(data);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            int padding = UIUtils.dp2px(5);
            textView.setPadding(padding, padding, padding, padding);
            //创建一个默认的背景
            GradientDrawable normalBg = new GradientDrawable();
            normalBg.setCornerRadius(10);
            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(170) + 30;//30-200
            int green = random.nextInt(170) + 30;//30-200
            int blue = random.nextInt(170) + 30;//30-200
            int argb = Color.argb(alpha, red, green, blue);
            normalBg.setColor(argb);

            //创建一个按压下去的背景
            GradientDrawable pressedBg = new GradientDrawable();
            pressedBg.setCornerRadius(10);
            pressedBg.setColor(Color.DKGRAY);

            //创建一个选择器
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed},pressedBg);//按压状态下
            stateListDrawable.addState(new int[]{-android.R.attr.state_pressed},normalBg);//默认状态下

            textView.setClickable(true);
            textView.setBackgroundDrawable(stateListDrawable);

            flowLayout.addView(textView);
        }
        scrollView.setPadding(10,10,10,10);
        scrollView.addView(flowLayout);
        return scrollView;
    }
}
