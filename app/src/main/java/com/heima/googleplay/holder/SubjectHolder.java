package com.heima.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.googleplay.R;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.bean.SubjectBean;
import com.heima.googleplay.conf.Constants;
import com.heima.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by GuoYaHui on 2017/7/15.
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {

    ImageView mItemSubjectIvIcon;
    TextView mItemSubjectTvTitle;

    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        mItemSubjectIvIcon = mHolderView.findViewById(R.id.item_subject_iv_icon);
        mItemSubjectTvTitle = mHolderView.findViewById(R.id.item_subject_tv_title);
        return mHolderView;
    }

    @Override
    public void refreshHolderView(SubjectBean data) {
        mItemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL+data.url).into(mItemSubjectIvIcon);
    }
}
