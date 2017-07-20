package com.heima.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.googleplay.base.BaseProtocol;
import com.heima.googleplay.bean.ItemBean;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/15.
 */

public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> parseJson(String resJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonString,new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "hot";
    }
}
