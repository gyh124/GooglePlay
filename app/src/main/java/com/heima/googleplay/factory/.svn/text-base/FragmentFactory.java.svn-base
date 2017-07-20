package com.heima.googleplay.factory;

import android.support.v4.app.Fragment;

import com.heima.googleplay.base.BaseFragment;
import com.heima.googleplay.fragment.AppFragment;
import com.heima.googleplay.fragment.CategoryFragment;
import com.heima.googleplay.fragment.GameFragment;
import com.heima.googleplay.fragment.HomeFragment;
import com.heima.googleplay.fragment.HotFragment;
import com.heima.googleplay.fragment.RecommendFragment;
import com.heima.googleplay.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GuoYaHui on 2017/7/9.
 */

public class FragmentFactory {

    public static final int FRAGMENT_HOME = 0;//首页
    public static final int FRAGMENT_APP = 1;//应用
    public static final int FRAGMENT_GAME = 2;//游戏
    public static final int FRAGMENT_SUBJECT = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY = 5;//分类
    public static final int FRAGMENT_HOT = 6;//排行

    //用于缓存fragment的集合
    public static Map<Integer,BaseFragment> mCacheFragments = new HashMap<>();


    public static Fragment createFragment(int position){
        BaseFragment fragment = null;
        /**
         * 如果缓存集合里面有，先从集合中取出
         */
        if (mCacheFragments.containsKey(position)){
            fragment = mCacheFragments.get(position);
            return fragment;
        }
        switch (position){
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP:
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME:
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT:
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND:
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY:
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT:
                fragment = new HotFragment();
                break;
        }
        mCacheFragments.put(position,fragment);
        return fragment;

    }
}
