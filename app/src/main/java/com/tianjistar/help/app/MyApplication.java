package com.tianjistar.help.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.SDKInitializer;
import com.tianjistar.help.base.BaseApplication;
import com.tianjistar.help.bean.User;
import com.tianjistar.help.utils.GsonUtil;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.SharedPreferencesHelper;
import com.tianjistar.help.utils.SharedPreferencesUtil;


import java.util.ArrayList;




/**
 * Created by Administrator on 2017/5/22.
 */

public class MyApplication extends BaseApplication {
    private static Context sContext;
    private static String registrationID;
    public static SharedPreferencesUtil spUtil;
    public static  User CURRENT_USER;// 当前用户
    public static final boolean DEBUG = true;// 是否debug， 开发和测试阶段使用
//    private static DisplayImageOptions options;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        setUpSharedPreferencesHelper(getApplicationContext());//初始化SharedPreferences
        SDKInitializer.initialize(getApplicationContext());
    }

    /**
     * 获取Application 上下文
     *
     * @return
     */
    public static Context getContext() {

        return sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    private void setUpSharedPreferencesHelper(Context context) {
        SharedPreferencesHelper.getInstance().Builder(context);
    }
    /**
     *   动态添加权限
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public static void saveUser(User user) {
        MyApplication.CURRENT_USER = user;
        // spUtil.setObject("CURRENT_USER", CURRENT_USER);
        PreferencesUtils.putString(getContext(), AppSpContact.USERINFO, GsonUtil.GsonString(user));
    }
    /**
     * 读取用户
     *
     * @return
     */
    public static User getUser() {
        User current_user = GsonUtil.GsonToBean(PreferencesUtils.getString(getContext(), AppSpContact.USERINFO), User.class);
        return current_user ;
    }


    /**
     * 跳到查看大图
     *
     * @param activity
     * @param isDelete
     * @param imageUrlList
     */
    public static void goGalleryUrlActivity(Activity activity, boolean isDelete, int point, ArrayList<String> imageUrlList) {
//        Intent intent = new Intent(activity, ViewPagerActivity.class);
//        intent.putExtra("isDelete", isDelete);
//        intent.putExtra("point", point);
//        intent.putStringArrayListExtra("imageUrlList", imageUrlList);
//        activity.startActivity(intent);
    }

    /**
     * @param activity
     * @param forward_msg_id
     * @param imagePath
     */
    public static void goForwardMessageActivity(Activity activity, String forward_msg_id, String imagePath) {
//        Intent intent = new Intent(activity, ForwardMessageActivity.class);
//        intent.putExtra("forward_msg_id", forward_msg_id);
//        intent.putExtra("imagePath", imagePath);
//        activity.startActivity(intent);
    }

//    public static DisplayImageOptions getOptions() {
//        return options;
//    }
//    private void openSealDBIfHasCachedToken() {
//        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
//        //  String cachedToken = sp.getString("loginToken", "");
//        String cachedToken = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_TOKEN);
//        if (!TextUtils.isEmpty(cachedToken)) {
//            String current = getCurProcessName(this);
//            String mainProcessName = getPackageName();
//            if (mainProcessName.equals(current)) {
//                SealUserInfoManager.getInstance().openDB();
//            }
//        }
//    }
}
