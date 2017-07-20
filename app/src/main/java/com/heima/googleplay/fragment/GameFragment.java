package com.heima.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.heima.googleplay.adapter.ItemAdapter;
import com.heima.googleplay.adapter.SuperBaseAdapter;
import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.factory.ListViewFactory;
import com.heima.googleplay.holder.ItemHolder;
import com.heima.googleplay.protocol.GameProtocol;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class GameFragment extends BaseFragment {


    private GameProtocol mGameProtocol;
    private List<ItemBean> mDatas;

    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        mGameProtocol = new GameProtocol();
        try {
            mDatas = mGameProtocol.loadData(0);
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
        listView.setAdapter(new GameAdapter(mDatas,listView));
        return listView;
    }
    class GameAdapter extends ItemAdapter{

        public GameAdapter(List<ItemBean> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        /**
         *
         * @return
         * @throws Exception
         * 子类具体实现加载更多数据
         */
        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            List<ItemBean> itemBeans = mGameProtocol.loadData(mDatas.size());
            return itemBeans;
        }

    }
}
