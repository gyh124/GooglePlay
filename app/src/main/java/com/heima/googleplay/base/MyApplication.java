package com.heima.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by GuoYaHui on 2017/7/8.
 */

public class MyApplication extends Application {


    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;
    private Map<String,String> mMemCacheMap = new HashMap<>();

    /**
     *
     * @return 获得内存缓存的集合
     */
    public Map<String, String> getMemCacheMap() {
        return mMemCacheMap;
    }

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
