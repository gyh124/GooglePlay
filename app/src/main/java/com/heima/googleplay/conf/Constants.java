package com.heima.googleplay.conf;

import com.heima.googleplay.utils.LogUtils;

/**
 * Created by GuoYaHui on 2017/7/8.
 */

public class Constants {
    //LogUtils.LEVEL_ALL:显示所有的日志输出
    //LogUtils.LEVEL_OFF：关闭所有的日志输出
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = 5*60*1000;

    public static final class URLS {
        public static final String BASEURL = "http://10.0.2.2:8080/GooglePlayServer/";
        public static final String IMGBASEURL = BASEURL + "image?name=";
    }

    public static final class REQ {

    }

    public static final class RES {

    }

    public static final class PAY {
        public static final int PAYTYPE_ZHIFUBAO = 0;
        public static final int PAYTYPE_WEIXIN = 1;
    }
}
