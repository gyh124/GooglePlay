package com.heima.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;

import com.heima.googleplay.base.MyApplication;

/**
 * Created by GuoYaHui on 2017/7/8.
 */

public class UIUtils {
    /**
     * 获得上下文
     */
    public static Context getContext(){

        return MyApplication.getContext();
    }
    /**
     * 获得resource对象
     */
    public static Resources getResources(){

        return getContext().getResources();
    }
    /**
     * 获得String.xml的字符串信息
     */
    public static String getString(int resId){

        return getResources().getString(resId);
    }
    /**
     * 获得String.xml的字符串数组信息
     */
    public static String[] getStrings(int resId){
        return getResources().getStringArray(resId);
    }
    /**
     * 获得Color.xml的字符串信息
     */
    public static int getColor(int resId){
        return getResources().getColor(resId);
    }
    /**
     * 获得应用程序的包名
     */
    public static String getPackageName(){
        return getContext().getPackageName();
    }
    /**
     * dp转px
     */
    public static int dp2px(int dp){
        //px/dp=density
        //获得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dp*density+.5f);
        return px;
    }
    /**
     * px转dp
     */
    public static int px2dp(int px){
        float density = getResources().getDisplayMetrics().density;
        int dp = (int) (px/density+.5f);
        return dp;
    }
}
