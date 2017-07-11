package com.heima.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by GuoYaHui on 2017/7/8.
 */

public class MyApplication extends Application {


    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //获得主线程的handler
        mMainThreadHandler = new Handler();
        //获得主线程的线程id
        mMainThreadId = Process.myTid();
    }
    public static Context getContext(){
        return mContext;
    }
    public static Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }
    public static int getMainThreadId(){
        return mMainThreadId;
    }
}
