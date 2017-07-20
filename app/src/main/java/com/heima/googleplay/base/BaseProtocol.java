package com.heima.googleplay.base;

import android.support.annotation.NonNull;

import com.heima.googleplay.conf.Constants;
import com.heima.googleplay.utils.FileUtils;
import com.heima.googleplay.utils.HttpUtils;
import com.heima.googleplay.utils.IOUtils;
import com.heima.googleplay.utils.LogUtils;
import com.heima.googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GuoYaHui on 2017/7/13.
 * 对homeFragment的网络请求进行封装
 */

public abstract class BaseProtocol<T> {
    public T loadData(int index)throws Exception{
        T result = null;
        result = loadDataFromMem(index);
        if (result!=null){
            LogUtils.s("从内存获得数据"+getGenerateKey(index));
            return result;
        }
        result = loadDataFromLocal(index);
        if (result!=null){
            LogUtils.s("从本地获得数据"+getCacheFile(index).getAbsolutePath());
            return result;
        }

        return loadDataFromNet(index);

    }

    /**
     *
     * @param index
     * @return 从内存获得数据
     */
    private T loadDataFromMem(int index) {
        T result = null;
        MyApplication myApplication = (MyApplication) UIUtils.getContext();
        Map<String, String> memCacheMap = myApplication.getMemCacheMap();
        //判断是否有缓存
        String key = getInterfaceKey();
        if (memCacheMap.containsKey(key)){
            String memCacheResJsonString = memCacheMap.get(key);
            result = parseJson(memCacheResJsonString);
            if (result!=null){
                return result;
            }

        }
        return null;
    }

    /**
     *
     * @param index
     * @return 从本地获得数据
     */
    private T loadDataFromLocal(int index) {
        BufferedReader bufferedReader = null;
        try {
            //获得缓存文件
            File cacheFile = getCacheFile(index);
            if (cacheFile.exists()&&cacheFile.length()>0){
                bufferedReader = new BufferedReader(new FileReader(cacheFile));
                //读取时间
                String firstLine = bufferedReader.readLine();
                Long cacheTime = Long.parseLong(firstLine);
                Long currentTime = System.currentTimeMillis();
                if ((currentTime-cacheTime)<Constants.PROTOCOLTIMEOUT){
                    String diskCacheResJsonString = bufferedReader.readLine();
                    //保存数据到内存
                    MyApplication myApplication = (MyApplication) UIUtils.getContext();
                    Map<String, String> memCacheMap = myApplication.getMemCacheMap();
                    memCacheMap.put(getGenerateKey(index),diskCacheResJsonString);
                    LogUtils.s("保存本地数据到内存"+getGenerateKey(index));
                    return parseJson(diskCacheResJsonString);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedReader);
        }
        return null;

    }

    /**
     *
     * @param index
     * @return
     *
     * 从网络加载数据
     */
    private T loadDataFromNet(int index) throws Exception {
        //创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 拼接URL：http://localhost:8080/GooglePlayServer/home
        String url = Constants.URLS.BASEURL + getInterfaceKey();
        //定义参数对应的map
        Map<String, Object> params = getParamsMap(index);
        //拼接参数信息?index=0
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(params);
        url = url+"?"+urlParamsByMap;
        //创建请求对象
        Request request = new Request.Builder().get().url(url).build();
        //发起请求：同步请求
        Response response = okHttpClient.newCall(request).execute();
        //解析数据
        if (response.isSuccessful()){
            //取出响应的内容
            String resJsonString  = response.body().string();
            LogUtils.s("resJsonString：" + resJsonString);
            //保存网络数据到内存
            MyApplication myApplication = (MyApplication) UIUtils.getContext();
            Map<String, String> memCacheMap = myApplication.getMemCacheMap();
            memCacheMap.put(getGenerateKey(index),resJsonString);
            LogUtils.s("保存网络数据到内存"+getGenerateKey(index));


            //保存数据到本地
            BufferedWriter bufferedWriter=null;
            try {
                File cacheFile = getCacheFile(index);
                bufferedWriter = new BufferedWriter(new FileWriter(cacheFile));
                //先把保存文件的当前时间写到第一行
                bufferedWriter.write(System.currentTimeMillis()+"");
                //换行
                bufferedWriter.newLine();
                //写数据
                bufferedWriter.write(resJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(bufferedWriter);
            }
            LogUtils.s("保存网络数据到本地"+getCacheFile(index).getAbsolutePath());
            //进行json解析
            return parseJson(resJsonString);
        }else {
            return null;
        }
    }

    /**
     *
     * @param index
     * @return 获得请求参数所对应的hashMap
     * 子类可以复写该方法，返回不同的参数
     */
    @NonNull
    public Map<String, Object> getParamsMap(int index) {
        Map<String,Object> params = new HashMap<>();
        params.put("index",index);//不考虑分页
        return params;//默认参数是index
    }

    /**
     *
     * @param index
     * @return 获得缓存文件
     */
    private File getCacheFile(int index) {
        //优先保存到外置sd卡（sdcard/Android/data/包目录/cache/json）
        String dir = FileUtils.getDir("json");
        String fileName = getGenerateKey(index);
        return new File(dir,fileName);
    }

    /**
     *
     * @param index
     * @return 生成缓存唯一的key
     */
    public String getGenerateKey(int index) {
        return getInterfaceKey()+"."+index;
    }

    /**
     *
     * @param resJsonString
     * @return json解析有三种情况：结点解析，bean解析，泛型解析，交给子类实现，子类必须实现，定为抽象的方法
     */
    public abstract T parseJson(String resJsonString);

    /**
     *
     * @return 获得协议的关键字
     * 在BaseProtocol不知道协议关键字具体是啥，交给子类实现，子类必须实现，定为抽象的方法；
     */
    public abstract String getInterfaceKey();
}
