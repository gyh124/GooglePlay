package com.heima.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.googleplay.base.BaseProtocol;
import com.heima.googleplay.bean.SubjectBean;

import java.util.List;

/**
 * Created by GuoYaHui on 2017/7/15.
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    public List<SubjectBean> parseJson(String resJsonString) {
        Gson gson = new Gson();

        return gson.fromJson(resJsonString,new TypeToken<List<SubjectBean>>(){}.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "subject";
    }
}
