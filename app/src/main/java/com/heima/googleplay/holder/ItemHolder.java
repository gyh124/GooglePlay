package com.heima.googleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.heima.googleplay.R;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.bean.ItemBean;
import com.heima.googleplay.conf.Constants;
import com.heima.googleplay.utils.StringUtils;
import com.heima.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GuoYaHui on 2017/7/13.
 * 提供视图
 * 接收数据
 * 数据和视图的绑定
 */

public class ItemHolder extends BaseHolder<ItemBean> {
//    @BindView(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
//    @BindView(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
//    @BindView(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
//    @BindView(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
//    @BindView(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;


    /**
     * @return 初始化根视图，决定根视图长什么样
     */
    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        mItemAppinfoTvTitle = mHolderView.findViewById(R.id.item_appinfo_tv_title);
        mItemAppinfoIvIcon = mHolderView.findViewById(R.id.item_appinfo_iv_icon);
        mItemAppinfoRbStars = mHolderView.findViewById(R.id.item_appinfo_rb_stars);
        mItemAppinfoTvSize = mHolderView.findViewById(R.id.item_appinfo_tv_size);
        mItemAppinfoTvDes = mHolderView.findViewById(R.id.item_appinfo_tv_des);

//        ButterKnife.bind(mHolderView);

        return mHolderView;
    }

    /**
     * @param data 数据和视图的绑定
     */
    @Override
    public void refreshHolderView(ItemBean data) {
        //view:在成员变量中
        //data：成员变量有，局部变量有
        //data+view
        mItemAppinfoTvTitle.setText(data.name);
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvDes.setText(data.des);
        //ratingbar
        mItemAppinfoRbStars.setRating(data.stars);

        //图片加载
        String url = Constants.URLS.IMGBASEURL+data.iconUrl;
        Picasso.with(UIUtils.getContext()).load(url).into(mItemAppinfoIvIcon);

    }

}
