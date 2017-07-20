package com.heima.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.googleplay.base.BaseProtocol;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/16.
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> parseJson(String resJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonString,new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "recommend";
    }
}
