package com.heima.googleplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.astuetz.PagerSlidingTabStripExtends;
import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.base.LoadingPagerController;
import com.heima.googleplay.factory.FragmentFactory;
import com.heima.googleplay.utils.LogUtils;
import com.heima.googleplay.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ViewPager mViewPager;
    private PagerSlidingTabStripExtends mTab;
    private String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initActionBar();
        initActionBarDrawerToggle();
        initData();
        initListener();



    }

    private void initListener() {
        final MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();

        //设置viewpager的监听
        mTab.setOnPageChangeListener(myOnPageChangeListener);
        //监听ViewPager的ViewTreeObserver，当布局摆放完成时，
        // 再去调用onPageChangeListener.onPageSelected(0)来加载首页的数据。
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myOnPageChangeListener.onPageSelected(0);
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //根据position找到对应页面的fragment
            BaseFragment baseFragment = FragmentFactory.mCacheFragments.get(position);
            LoadingPagerController loadingPagerController = baseFragment.getLoadingPagerController();
            loadingPagerController.triggerLoadData();

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 控件的初始化
     */
    private void init() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl);
        mTab = (PagerSlidingTabStripExtends) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initData() {
        mTitles = UIUtils.getStrings(R.array.titles);
//        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTab.setViewPager(mViewPager);

    }
    class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

        public MainFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.s(mTitles[position]);
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            if (mTitles!=null){
                return mTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
    class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.s(mTitles[position]);
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            if (mTitles!=null){
                return mTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    private void initActionBarDrawerToggle() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        //同步状态。更改默认的回退部分的ui
        mToggle.syncState();
        //设置drawerLayout的监听，drawerLayout拖动的时候，toggle可以改变ui
        mDrawerLayout.setDrawerListener(mToggle);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //设置标题
        actionBar.setTitle("GooglePlay");
        //设置副标题
        actionBar.setSubtitle("gyh");
        //设置图标
        actionBar.setIcon(R.mipmap.ic_launcher);
        //显示图标
        actionBar.setDisplayShowHomeEnabled(true);
        //显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
