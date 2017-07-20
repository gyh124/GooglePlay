package com.heima.googleplay.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.heima.googleplay.base.BaseProtocol;
import com.heima.googleplay.bean.ItemBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GuoYaHui on 2017/7/17.
 * 应用详情界面对应的协议处理类
 */

public class DetailProtocol extends BaseProtocol<ItemBean> {

   private String packageName;

    public DetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public ItemBean parseJson(String resJsonString) {
        Gson gson = new Gson();
        ItemBean itemBean = gson.fromJson(resJsonString, ItemBean.class);
        return itemBean;
    }

    @Override
    public String getInterfaceKey() {
        return "detail";
    }
    /**
     * 复写该方法，返回不同的参数
     */
    @NonNull
    @Override
    public Map<String, Object> getParamsMap(int index) {
        HashMap<String, Object> parasmMap  = new HashMap<>();
        parasmMap.put("packageName",packageName);
        return parasmMap;
    }
    /**
     * 复写该方法，返回不同的缓存的key
     */
    @Override
    public String getGenerateKey(int index) {
        return getInterfaceKey()+"."+packageName;
    }
}
