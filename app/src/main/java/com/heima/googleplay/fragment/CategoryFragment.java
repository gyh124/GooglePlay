package com.heima.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.heima.googleplay.adapter.SuperBaseAdapter;
import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.holder.CategoryNormalHolder;
import com.heima.googleplay.holder.CategorytitleHolder;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.bean.CategoryBean;
import com.heima.googleplay.factory.ListViewFactory;
import com.heima.googleplay.protocol.CategoryProtocol;
import com.heima.googleplay.utils.LogUtils;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class CategoryFragment extends BaseFragment {


    private List<CategoryBean> mCategoryBeanList;

    /**
     *
     * @return
     * 子类实现数据的加载
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPagerController.LoadedResult initData() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        try {
            mCategoryBeanList = categoryProtocol.loadData(0);
            LogUtils.printList(mCategoryBeanList);
            return checkResult(mCategoryBeanList);
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
        listView.setAdapter(new CategoryAdapter(mCategoryBeanList,listView));
        return listView;
    }
    class CategoryAdapter extends SuperBaseAdapter<CategoryBean>{
        public CategoryAdapter(List<CategoryBean> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int i) {
            CategoryBean categoryBean = mCategoryBeanList.get(i);
            if (categoryBean.isTitle){
                return new CategorytitleHolder();
            }else {
                return new CategoryNormalHolder();
            }

        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        public int getNormalItemViewType(int position) {
            CategoryBean categoryBean = mCategoryBeanList.get(position);
            if (categoryBean.isTitle){
                return 1;
            }else {
                return 2;
            }

        }
    }
}
