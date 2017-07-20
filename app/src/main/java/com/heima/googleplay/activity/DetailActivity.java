package com.heima.googleplay.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.heima.googleplay.R;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.holder.DetailDesHolder;
import com.heima.googleplay.holder.DetailDownloadHolder;
import com.heima.googleplay.holder.DetailInfoHolder;
import com.heima.googleplay.holder.DetailPicHolder;
import com.heima.googleplay.holder.DetailSafeHolder;
import com.heima.googleplay.protocol.DetailProtocol;
import com.heima.googleplay.utils.UIUtils;

/**
 * Created by GuoYaHui on 2017/7/17.
 */

public class DetailActivity extends AppCompatActivity {

    private LoadingPagerController mLoadingPagerController;
    private String mPackageName;
    private ItemBean mItemBean;
    private FrameLayout mDetailfldes;
    private FrameLayout mDetailflpic;
    private FrameLayout mDetailflsafe;
    private FrameLayout mDetailflinfo;
    private FrameLayout mDetailfldownload;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPagerController = new LoadingPagerController(this) {

            @Override
            protected LoadedResult initData() {
                return DetailActivity.this.initData();
            }

            @Override
            public View initSuccessView() {
                return DetailActivity.this.initSuccessView();
            }
        };
        setContentView(mLoadingPagerController);
        init();
        //触发加载详情页面的数据
        triggerLoadData();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");
    }

    /**
     * 触发加载详情页面的数据
     */
    private void triggerLoadData() {
        mLoadingPagerController.triggerLoadData();
    }

    /**
     * @return 在子线程里面真正的加载详情页面的数据
     */
    private LoadingPagerController.LoadedResult initData() {
        SystemClock.sleep(1000);
        DetailProtocol detailProtocol = new DetailProtocol(mPackageName);
        try {
            mItemBean = detailProtocol.loadData(0);
            if (mItemBean != null) {
                return LoadingPagerController.LoadedResult.SUCCESS;
            } else {
                return LoadingPagerController.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    /**
     * @return 决定成功视图是什么，视图和数据的绑定
     */
    private View initSuccessView() {
        View successView = View.inflate(UIUtils.getContext(), R.layout.layout_detail, null);
        mDetailfldes = successView.findViewById(R.id.detail_fl_des);
        mDetailflpic = successView.findViewById(R.id.detail_fl_pic);
        mDetailflsafe = successView.findViewById(R.id.detail_fl_safe);
        mDetailflinfo = successView.findViewById(R.id.detail_fl_info);
        mDetailfldownload = successView.findViewById(R.id.detail_fl_download);
        //data在成员变量中
        //data+view
        //往应用的基本信息部分的容器里添加内容
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        mDetailflinfo.addView(detailInfoHolder.mHolderView);
        detailInfoHolder.setDataAndRefreshHolderView(mItemBean);

        //往应用的安全部分的容器里添加内容
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        mDetailflsafe.addView(detailSafeHolder.mHolderView);
        detailSafeHolder.setDataAndRefreshHolderView(mItemBean);

        //往应用的截图部分的容器里添加内容
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        mDetailflpic.addView(detailPicHolder.mHolderView);
        detailPicHolder.setDataAndRefreshHolderView(mItemBean);

        //往应用的描述部分的容器里添加内容
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        mDetailfldes.addView(detailDesHolder.mHolderView);
        detailDesHolder.setDataAndRefreshHolderView(mItemBean);

        //往应用的下载部分的容器里添加内容
        DetailDownloadHolder detailDownloadHolder = new DetailDownloadHolder();
        mDetailfldownload.addView(detailDownloadHolder.mHolderView);
        detailDownloadHolder.setDataAndRefreshHolderView(mItemBean);

        return successView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
