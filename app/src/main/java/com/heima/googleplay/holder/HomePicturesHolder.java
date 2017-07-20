package com.heima.googleplay.holder;

import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.heima.googleplay.R;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.base.MyApplication;
import com.heima.googleplay.conf.Constants;
import com.heima.googleplay.utils.LogUtils;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.views.ChildViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/15.
 */

public class HomePicturesHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener {

    ChildViewPager mItemHomePicturePager;
    LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mPictures;
    private AutoScrollTask mAutoScrollTask;

    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_home_pictures, null);
        mItemHomePicturePager = mHolderView.findViewById(R.id.item_home_picture_pager);
        mItemHomePictureContainerIndicator = mHolderView.findViewById(R.id.item_home_picture_container_indicator);
        return mHolderView;
    }

    @Override
    public void refreshHolderView(List<String> pictures) {
        mPictures = pictures;
        //viewPager绑定
        mItemHomePicturePager.setAdapter(new HomePicturesPagerAdapter());
        //indicator绑定
        for (int i = 0; i < mPictures.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            if (i==0){
                ivIndicator.setImageResource(R.drawable.indicator_selected);

            }
            //第一种方式，把dp转px
            int px = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, UIUtils.getResources().getDisplayMetrics()) + .5f);
            LogUtils.s(px+"");
            //第二种方式
            float dimension = UIUtils.getResources().getDimension(R.dimen.dppx);
            LogUtils.s(dimension+"");
            //第三种方式
            int width = UIUtils.dp2px(6);//6dp
            int height = UIUtils.dp2px(6);//6dp
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = UIUtils.dp2px(6);
            params.bottomMargin = UIUtils.dp2px(6);
            mItemHomePictureContainerIndicator.addView(ivIndicator,params);
        }
        mItemHomePicturePager.setOnPageChangeListener(this);
        //设置viewPager页面的初始位置
        int curItem = Integer.MAX_VALUE / 2;
        //对curItem做偏差处理
        int diff = Integer.MAX_VALUE / 2 % mPictures.size();
        curItem = curItem - diff;
        mItemHomePicturePager.setCurrentItem(curItem);
        //实现自动轮播
        if (mAutoScrollTask==null){
            mAutoScrollTask = new AutoScrollTask();
            mAutoScrollTask.start();
        }
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoScrollTask.start();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mPictures.size();
        for (int i = 0; i < mPictures.size(); i++) {
            ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            if (i==position){
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class HomePicturesPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
//            return mPictures!=null?mPictures.size():0;
            return mPictures!=null?Integer.MAX_VALUE:0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mPictures.size();
            //view
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //data
            String url = mPictures.get(position);
            //view+data
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL+url).into(iv);
            //添加到容器中
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    class AutoScrollTask implements Runnable{
        /**
         * 开始滚动
         */
        public void start() {
            MyApplication.getMainThreadHandler().postDelayed(this, 3000);
        }

        /**
         * 停止滚动
         */
        public void stop(){
            MyApplication.getMainThreadHandler().removeCallbacks(this);
        }
        @Override
        public void run() {
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;
            mItemHomePicturePager.setCurrentItem(currentItem);
            start();
        }
    }
}
