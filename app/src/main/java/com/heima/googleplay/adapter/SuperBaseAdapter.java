package com.heima.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.base.MyApplication;
import com.heima.googleplay.factory.ThreadPoolProxyFactory;
import com.heima.googleplay.holder.LoadMoreHolder;
import com.heima.googleplay.utils.LogUtils;

import java.util.List;



/**
 * Created by GuoYaHui on 2017/7/13.
 */

public abstract class SuperBaseAdapter<T> extends MyBaseAdapter implements AdapterView.OnItemClickListener {
    //加载更多的item
    public static final int VIEWTYPE_LOADMORE = 0;
    //普通的item
    public static final int VIEWTYPE_NORMAL = 1;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private AbsListView mAbsListView;
    private int mState;

    public SuperBaseAdapter(List<T> datas,AbsListView absListView) {

        super(datas);
        mAbsListView = absListView;
        mAbsListView.setOnItemClickListener(this);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseHolder holder = null;
        int curViewType = getItemViewType(i);
        if (view==null){
            if (curViewType==VIEWTYPE_LOADMORE){
                holder = getLoadMoreHolder();
            }else {
                //获得holder
                holder = getSpecialBaseHolder(i);
            }

        }else {
            holder = (BaseHolder) view.getTag();
        }
        //获得数据
        if (curViewType == VIEWTYPE_LOADMORE){
            if (hasLoadMore()){
                //显示正在加载更多的视图
                holder.setDataAndRefreshHolderView(LoadMoreHolder.LOADINGMORE);
                //触发加载更多的数据
                triggerLoadMoreData();
            }else {
                //隐藏加载更多和加载失败，点击重试的视图
                holder.setDataAndRefreshHolderView(LoadMoreHolder.NONE);
            }
        }else {
            Object data = mDatas.get(i);
            holder.setDataAndRefreshHolderView(data);
        }

        return holder.mHolderView;
    }

    /**
     * 触发加载更多的数据
     */
    private void triggerLoadMoreData() {
        if (mLoadMoreTask==null) {
            mLoadMoreTask = new LoadMoreTask();

            LogUtils.s("triggerLoadMoreData");
            //异步加载
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadMoreTask);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //处理position
        if (mAbsListView instanceof ListView) {
            i = i - ((ListView) mAbsListView).getHeaderViewsCount();
        }
        int curViewType = getItemViewType(i);
        if (curViewType==LoadMoreHolder.LOADINGMORE){
            if (mState==LoadMoreHolder.ERROR){
                //加载失败时，点击更新为加载更多的ui
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADINGMORE);
                //再次触发加载更多
                triggerLoadMoreData();
            }
        }else {//点击了普通条目
            onNormalItemClick(adapterView,view,i,l);

        }
    }

    /**
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     *  普通条目的点击事件，不知道如何具体实现点击事件，交给子类实现，子类是选择性实现
     */
    public void onNormalItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    class LoadMoreTask implements Runnable{
        private static final int PAGERSIZE = 20;//每页请求的总数

        @Override
        public void run() {
            //定义刷新ui用到的两个值
            List loadMoreList = null;

            try {
                loadMoreList = onLoadMore();
                //处理数据
                if (loadMoreList==null){
                    mState = LoadMoreHolder.NONE;//没有加载更多的数据
                }else {
                    if (loadMoreList.size()==PAGERSIZE){
                        //可能有加载更多
                        mState = LoadMoreHolder.LOADINGMORE;//正在加载更多
                    }else {
                        mState = LoadMoreHolder.NONE;//没有加载更多
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.ERROR;//加载更多失败，点击重试
            }
            //刷新ui
            final List finalLoadMoreList = loadMoreList;
            final int finalState = mState;
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (finalLoadMoreList !=null){
                        mDatas.addAll(finalLoadMoreList);
                        notifyDataSetChanged();
                    }
                    mLoadMoreHolder.setDataAndRefreshHolderView(finalState);
                }
            });
            mLoadMoreTask =null;
        }
    }

    /**
     *
     * @return
     * 不知道如何具体的加载更多的数据，交给子类实现，子类是选择性实现
     * 只有子类有加载更多的时候才复写该方法
     */
    public  List onLoadMore() throws Exception{
        return null;//默认返回null
    }

    /**
     *
     * @return 判断是否有加载更多，默认是没有加载更多
     *  子类可以复写该方法，可以决定是否有加载更多
     */
    public boolean hasLoadMore() {
        return false;
    }

    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder==null){
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;

    }

    /**
     *
     * @param position
     * @return 默认是0
     * 取值范围：0——getViewTypeCount()
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount()-1){
            return VIEWTYPE_LOADMORE;
        }else {
//            return VIEWTYPE_NORMAL;
            return getNormalItemViewType(position);
        }


    }

    public int getNormalItemViewType(int position) {
        return VIEWTYPE_NORMAL;
    }

    /**
     *
     * @return 默认是一种类型，返回值是1；
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    /**
     *
     * @return 获得baseHolder 的具体子类对象
     * 不知道如何创建具体子类的对象，只能交给子类实现，子类是必须实现的，所以定为抽象方法
     *
     *
     */
    public abstract BaseHolder getSpecialBaseHolder(int i);
}
