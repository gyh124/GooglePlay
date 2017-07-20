package com.heima.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.heima.googleplay.adapter.SuperBaseAdapter;
import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.bean.SubjectBean;
import com.heima.googleplay.factory.ListViewFactory;
import com.heima.googleplay.holder.SubjectHolder;
import com.heima.googleplay.protocol.SubjectProtocol;
import com.heima.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class SubjectFragment extends BaseFragment {


    private SubjectProtocol mSubjectProtocol;
    private List<SubjectBean> mSubjectBeen;

    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        mSubjectProtocol = new SubjectProtocol();
        try {
            mSubjectBeen = mSubjectProtocol.loadData(0);
            return checkResult(mSubjectBeen);
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
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new SubjectAdapter(mSubjectBeen,listView));
        return listView;
    }
    class SubjectAdapter extends SuperBaseAdapter<SubjectBean>{
        public SubjectAdapter(List<SubjectBean> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int i) {
            return new SubjectHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            List<SubjectBean> subjectBeen = mSubjectProtocol.loadData(mSubjectBeen.size());
            return subjectBeen;
        }
        /**
         * 处理条目点击事件
         */
        @Override
        public void onNormalItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(UIUtils.getContext(), "点击了"+i+"条目", Toast.LENGTH_SHORT).show();
        }
    }
}
