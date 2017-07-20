package com.heima.googleplay.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by GuoYaHui on 2017/7/15.
 * 用与轮播图左右滑动时,请求父亲不要拦截事件,解决在2.3的模拟器上左右滑动轮播图时,是外层的viewpager在响应事件,产生滑动效果的问题
 */

public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *
     * @param ev
     * @return
     * 什么时候事件会来到onTouchEvent
     *  1.当前控件拦截了事件
     *  2.事件传递给下一级之后，下一级拦截了事件，但是没处理事件（事件没被消费）
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录手指按下的坐标
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();
                int dx = (int) (moveX-mDownX+.5f);
                int dy = (int) (moveY-mDownY+.5f);
                if (Math.abs(dx)>Math.abs(dy)){//水平移动
                    //孩子处理事件，请求父控件不要拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(ev);
    }
}
