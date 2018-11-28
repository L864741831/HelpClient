package com.tianjistar.help.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.baidu.mapapi.SDKInitializer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.mob.MobSDK;
import com.tianjistar.help.base.BaseApplication;
import com.tianjistar.help.bean.User;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.SharedPreferencesHelper;
import com.tianjistar.help.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

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



        //shareSDK分享
        MobSDK.init(this);



        //极光推送
/*        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);*/


//        HttpHeaders headers = new HttpHeaders();
//        HttpParams params = new HttpParams();
        //header不支持中文，不允许有特殊字符
//        headers.put("Connection", "close");
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey1", "commonParamsValue1");
//        params.put("commonParamsKey2", "这里支持中文参数");
        //必须调用初始化
        OkGo.init(this);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
//                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
//                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
//                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
//                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
//                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
//                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore());      //cookie持久化存储，如果cookie不过期，则一直有效

            //可以设置https的证书,以下几种方案根据需要自己设置
//                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

            //这两行同上，不需要就不要加入
//                    .addCommonHeaders(headers)  //设置全局公共头
//                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }


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
