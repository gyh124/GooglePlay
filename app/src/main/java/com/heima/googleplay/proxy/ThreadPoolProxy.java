package com.heima.googleplay.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by GuoYaHui on 2017/7/11.
 * 线程池代理类：
 * 1.代理模式就是多一个代理类出来，替原对象进行一些操作
 * 2.只需提供使用原对象时候真正关心的方法：提交任务，执行任务，移除任务
 * 3.可以对原对象方法进行增强
 *
 */

public class ThreadPoolProxy {
    ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;//核心线程池的大小
    private int mMaximumPoolSize;//最大线程数量

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    public void initThreadPoolExecutor(){
        //mExecutor.isShutdown():判断线程池是否关闭，mExecutor.isTerminated()：判断线程池中的任务是否执行完成
        if(mExecutor==null||mExecutor.isShutdown()||mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {//双重检查加锁，只有第一次实例化的时候才启用同步机制，提高了性能
                if (mExecutor==null||mExecutor.isShutdown()||mExecutor.isTerminated()) {
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();//无界
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();//也是丢弃任务，但是不抛出异常。
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
                }
            }
        }
    }
    /**
     * 1.提交任务和执行任务的区别
     *      是否有返回值,提交任务有返回值
     * 2.Future<?>是什么?
     *      1.得到任务执行之后的结果
     *      2.包含了一个get方法和cancle
     *      3.其中get方法,是一个阻塞的方法,会阻塞等待任务执行完成之后的结果,还可以try catch到任务执行过程中抛出的异常
     */

    /**
     * 提交任务
     */
    public void submit(Runnable task){
        initThreadPoolExecutor();
        mExecutor.submit(task);
    }
    /**
     * 执行任务
     */
    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }
    /**
     * 移除任务
     */
    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}
