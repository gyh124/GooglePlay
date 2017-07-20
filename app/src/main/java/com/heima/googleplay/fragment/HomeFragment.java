package com.heima.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.heima.googleplay.adapter.ItemAdapter;
import com.heima.googleplay.adapter.SuperBaseAdapter;
import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.bean.HomeBean;
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.factory.ListViewFactory;
import com.heima.googleplay.holder.HomePicturesHolder;
import com.heima.googleplay.holder.ItemHolder;
import com.heima.googleplay.protocol.HomeProtocol;
import com.heima.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class HomeFragment extends BaseFragment {
    private List<ItemBean> mItemBeans;
    private List<String> mPictures;
    private HomeProtocol mHomeProtocol;


    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        mHomeProtocol = new HomeProtocol();

        try {
            HomeBean homeBean = mHomeProtocol.loadData(0);
            LoadingPagerController.LoadedResult state = checkResult(homeBean);
            if (state != LoadingPagerController.LoadedResult.SUCCESS) {//说明homeBean有问题,homeBean==null
                return state;
            }
            state = checkResult(homeBean.list);
            if (state != LoadingPagerController.LoadedResult.SUCCESS) {//说明list有问题,list.size==0
                return state;
            }
            //保存数据到成员变量
            mItemBeans = homeBean.list;
            mPictures = homeBean.picture;
            //返回状态（成功）
            return state;//数据加载成功
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
        //view
        ListView listView = ListViewFactory.createListView();
        //构建轮播图的holder
        HomePicturesHolder homePicturesHolder = new HomePicturesHolder();
        //数据和视图的绑定
        homePicturesHolder.setDataAndRefreshHolderView(mPictures);
        //取出homePicturesHolder的holderView
        View headerView = homePicturesHolder.mHolderView;
        //将headerView添加到listview
        listView.addHeaderView(headerView);
        //data->在成员变量中mDatas
        //data+view的绑定
        listView.setAdapter(new HomeAdapter(mItemBeans,listView));
        return listView;
    }
    class HomeAdapter extends ItemAdapter {
        public HomeAdapter(List<ItemBean> datas,AbsListView absListView) {
            super(datas,absListView);
        }


        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            HomeBean homeBean = mHomeProtocol.loadData(mItemBeans.size());
            if (homeBean!=null){
                return homeBean.list;
            }
            return super.onLoadMore();
        }
    }
}
