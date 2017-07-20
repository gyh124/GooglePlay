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
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.factory.ListViewFactory;
import com.heima.googleplay.holder.ItemHolder;
import com.heima.googleplay.protocol.AppProtocol;
import com.heima.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class AppFragment extends BaseFragment {


    private AppProtocol mAppProtocol;
    private List<ItemBean> mDatas;

    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        mAppProtocol = new AppProtocol();
        try {
            mDatas = mAppProtocol.loadData(0);
            //检查网络请求返回的数据，返回相应的状态
            return checkResult(mDatas);

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
        //data--mDatas
        //view+data
        listView.setAdapter(new AppAdapter(mDatas,listView));
        return listView;
    }
    class AppAdapter extends ItemAdapter{

        public AppAdapter(List<ItemBean> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        /**
         *
         * @return
         * @throws Exception
         * 具体加载更多的数据
         */
        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            List<ItemBean> itemBeans = mAppProtocol.loadData(mDatas.size());
            return itemBeans;
        }
    }
}
