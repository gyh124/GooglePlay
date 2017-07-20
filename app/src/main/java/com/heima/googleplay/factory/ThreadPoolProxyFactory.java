package com.heima.googleplay.factory;

import com.heima.googleplay.proxy.ThreadPoolProxy;

/**
 * Created by GuoYaHui on 2017/7/11.
 * ThreadPoolProxy的工厂类，来创建ThreadPoolProxy
 */

public class ThreadPoolProxyFactory {
    //普通类型的线程池代理
    static ThreadPoolProxy mNormalThreadPoolProxy;
    //下载类型的线程池代理
    static ThreadPoolProxy mDownThreadPoolProxy;

    /**
     *
     * @return 获得普通类的线程池代理
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mNormalThreadPoolProxy==null){
                    mNormalThreadPoolProxy=new ThreadPoolProxy(5,5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     *
     * @return 获得下载类的线程池代理
     */
    public static ThreadPoolProxy getDownThreadPoolProxy() {
        if (mDownThreadPoolProxy==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mDownThreadPoolProxy==null){
                    mDownThreadPoolProxy = new ThreadPoolProxy(3,3);
                }
            }
        }
        return mDownThreadPoolProxy;
    }
}
