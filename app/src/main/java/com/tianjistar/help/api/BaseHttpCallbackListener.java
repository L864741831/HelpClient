package com.tianjistar.help.api;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.utils.StringUtil;


/**
 * 回调的简单空实现
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016-3-13 下午9:47:52
 */
public class BaseHttpCallbackListener<T> implements HttpCallbackListener<T> {

    private Context context;

    @Override
    public void callbackNoNetwork(String url) {
    }

    @Override
    public void callbackErrorJSONFormat(String url) {
//        Logger.e("verrr", url);// 打印错误
        Log.i("info","---callbackErrorJSONFormat---"+url);
    }

    @Override
    public void callbackError(String url, T obj) {
        Log.i("info","---callbackError---");
        if (obj instanceof Element) {
            Element element2 = (Element) obj;
            if (!StringUtil.isEmpty(element2.msg)) {
                MyApplication.showToast(element2.msg);
            }
        }
    }

    @Override
    public void callbackSuccess(String url, T element) {
    }

    @Override
    public void onFaliure(String url, int statusCode, String content, Throwable error) {
//        Logger.e(error, url, statusCode);

        Log.i("info",error+"---onFaliure---"+url);
        Log.i("info","---onFaliure2---"+statusCode);
        MyApplication.showToast("连接超时，请稍后重试");
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
