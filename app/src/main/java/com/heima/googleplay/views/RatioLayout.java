package com.heima.googleplay.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.heima.googleplay.R;

/**
 * Created by GuoYaHui on 2017/7/16.
 * 图片的宽高比=当前控件的宽高比
 * 已知宽度,动态计算高度
 * 已知高度,动态计算宽度
 */

public class RatioLayout extends FrameLayout {
    private float mPicRatio;
    private static final int RELATIVE_WIDTH = 0;//已知宽度,动态计算高度
    private static final int RELATIVE_HEIGHT = 1;//已知高度,动态计算宽度
    private int mRelative = RELATIVE_WIDTH;

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }


    public void setRelative(int relative) {
        mRelative = relative;
    }

    public RatioLayout(@NonNull Context context) {
        this(context, null);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //取出自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, 1);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得当前控件的宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //已知宽度，动态计算高度
        if (widthMode == MeasureSpec.EXACTLY) {
            //获得当前控件的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //计算控件的高度
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);
            //保存测量结果
            setMeasuredDimension(selfWidth, selfHeight);
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();
            int measureSpecWidth = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int measureSpecHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(measureSpecWidth, measureSpecHeight);
        } else if (heightMode == MeasureSpec.EXACTLY) {//已知高度，动态计算宽度
            //获得当前控件的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            //计算控件的宽度
            int selfWidth = (int) (selfHeight * mPicRatio + .5f);
            //保存测量结果
            setMeasuredDimension(selfWidth, selfHeight);
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();
            int measureSpecWidth = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int measureSpecHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(measureSpecWidth, measureSpecHeight);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


    }
}
