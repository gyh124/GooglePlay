package com.heima.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.googleplay.R;
import com.heima.googleplay.base.BaseHolder;
import com.heima.googleplay.bean.CategoryBean;
import com.heima.googleplay.conf.Constants;
import com.heima.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by GuoYaHui on 2017/7/16.
 */

public class CategoryNormalHolder extends BaseHolder<CategoryBean> {

    ImageView mItemCategoryIcon1;

    TextView mItemCategoryName1;

    LinearLayout mItemCategoryItem1;

    ImageView mItemCategoryIcon2;

    TextView mItemCategoryName2;

    LinearLayout mItemCategoryItem2;

    ImageView mItemCategoryIcon3;

    TextView mItemCategoryName3;

    LinearLayout mItemCategoryItem3;

    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        mItemCategoryIcon1 = mHolderView.findViewById(R.id.item_category_icon_1);
        mItemCategoryName1 = mHolderView.findViewById(R.id.item_category_name_1);
        mItemCategoryItem1 = mHolderView.findViewById(R.id.item_category_item_1);
        mItemCategoryIcon2 = mHolderView.findViewById(R.id.item_category_icon_2);
        mItemCategoryName2 = mHolderView.findViewById(R.id.item_category_name_2);
        mItemCategoryItem2 = mHolderView.findViewById(R.id.item_category_item_2);
        mItemCategoryIcon3 = mHolderView.findViewById(R.id.item_category_icon_3);
        mItemCategoryName3 = mHolderView.findViewById(R.id.item_category_name_3);
        mItemCategoryItem3 = mHolderView.findViewById(R.id.item_category_item_3);
        return mHolderView;
    }

    @Override
    public void refreshHolderView(CategoryBean data) {
        refreshUI(data.name1, data.url1, mItemCategoryName1, mItemCategoryIcon1);
        refreshUI(data.name2, data.url2, mItemCategoryName2, mItemCategoryIcon2);
        refreshUI(data.name3, data.url3, mItemCategoryName3, mItemCategoryIcon3);

    }

    private void refreshUI(String name, String url, TextView itemCategoryName, ImageView itemCategoryIcon) {
        if (TextUtils.isEmpty(name)&&TextUtils.isEmpty(url)){
            ViewParent parent = itemCategoryName.getParent();
            ((ViewGroup)parent).setVisibility(View.INVISIBLE);
        }else {
            ViewParent parent = itemCategoryName.getParent();
            ((ViewGroup)parent).setVisibility(View.VISIBLE);
            itemCategoryName.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + url).into(itemCategoryIcon);
        }
    }
}
