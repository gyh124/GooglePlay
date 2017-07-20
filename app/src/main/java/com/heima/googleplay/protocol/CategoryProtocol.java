package com.heima.googleplay.protocol;


import com.heima.googleplay.base.BaseProtocol;
import com.heima.googleplay.bean.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/16.
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    public List<CategoryBean> parseJson(String resJsonString) {
        //用节点解析，jsonString的结构和bean结构不统一
        List<CategoryBean> categoryBeanList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(resJsonString);
            //遍历
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //取出title
                String title = jsonObject.getString("title");
                CategoryBean titleCategoryBean = new CategoryBean();
                titleCategoryBean.title = title;
                titleCategoryBean.isTitle = true;
                categoryBeanList.add(titleCategoryBean);
                JSONArray infosJSONArray = jsonObject.getJSONArray("infos");
                //遍历
                for (int j = 0; j <infosJSONArray.length() ; j++) {
                    JSONObject infosJsonObject = infosJSONArray.getJSONObject(j);
                    String name1 = infosJsonObject.getString("name1");
                    String name2 = infosJsonObject.getString("name2");
                    String name3 = infosJsonObject.getString("name3");
                    String url1 = infosJsonObject.getString("url1");
                    String url2 = infosJsonObject.getString("url2");
                    String url3 = infosJsonObject.getString("url3");
                    CategoryBean infoCategoryBean = new CategoryBean();
                    infoCategoryBean.name1 = name1;
                    infoCategoryBean.name2 = name2;
                    infoCategoryBean.name3 = name3;
                    infoCategoryBean.url1 = url1;
                    infoCategoryBean.url2 = url2;
                    infoCategoryBean.url3 = url3;
                    categoryBeanList.add(infoCategoryBean);

                }
            }
            return categoryBeanList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInterfaceKey() {
        return "category";
    }
}
