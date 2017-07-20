package com.heima.googleplay.protocol;

import com.google.gson.Gson;
import com.heima.googleplay.base.BaseProtocol;
import com.heima.googleplay.bean.HomeBean;

/**
 * Created by GuoYaHui on 2017/7/13.
 * 对homeFragment的网络请求进行封装
 */

public class HomeProtocol extends BaseProtocol<HomeBean>{

    @Override
    public HomeBean parseJson(String resJsonString) {
        Gson gson = new Gson();
        HomeBean homeBean = gson.fromJson(resJsonString, HomeBean.class);
        return homeBean;
    }

    @Override
    public String getInterfaceKey() {
        return "home";
    }
}
