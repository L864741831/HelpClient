package com.tianjistar.help.aaa;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.tianjistar.help.R;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.JavaScriptObject;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.AboutBean;
import com.tianjistar.help.fragment.HomeFragment;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.LogUtil;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.StringUtils;
import com.tianjistar.help.view.alipayView.PayResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2018/11/10.
 */

public class VueActivity extends Base1Activity implements View.OnClickListener {

    ImageView iv_back;
    ImageView iv_reload;
    WebView mWebview;
    ProgressBar mProgressBar;
    LinearLayout common_network_error;

    //http://172.18.1.79:8080/
/*    String mUrl = "http://47.94.90.205/mobile/#/user/home";*/

    //String mUrl = "http://172.18.1.79:8080";   //本地

    //String mUrl = "http://47.94.90.205/mobile/"; //测试

    //http://ty.tianjistar.com  //正式

    String mUrl = "http://ty.tianjistar.com/mobile/#/user/home";

/*    String mUrl = "http://172.18.1.79:8080";*/

/*    String mUrl = "http://172.18.1.79:8080/#/user/home";*/

/*    String mUrl = "https://www.baidu.com/";*/

    private LocationClient mLocClient;
    //纬度
    public double mCurrentLat = 0.0;
    //经度
    public double mCurrentLon = 0.0;
    //定位精度
    private float mCurrentAccracy;


    boolean show = false;   //第一次加载url

    public final int SDK_PAY_FLAG = 1;
    private String orderInfo = "";

    /*
    H5调用相机拍照图库选择图片
     */
    private android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL;
    private android.webkit.ValueCallback<Uri> mUploadCallbackBelow;
    private Uri imageUri;
    private int REQUEST_CODE = 1234;


    int progress = 0;


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    JSONObject obj = null;//最外层的JSONObject对象
                    String out_trade_no_info = "";
                    try {
                        obj = new JSONObject(resultInfo);
                        JSONObject object = obj.getJSONObject("alipay_trade_app_pay_response");
                        out_trade_no_info = object.getString("out_trade_no");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        getAliPayState(out_trade_no_info);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;


                case 2:
                    double price = (double) msg.obj;
                    Toast.makeText(VueActivity.this, "金额223" + price, Toast.LENGTH_SHORT).show();
                    break;


/*                case 555:

                    Log.i("666", "666");

*//*                    // 只需要将第一种方法的loadUrl()换成下面该方法即可
                    mWebview.evaluateJavascript("javascript:upDetaGeography("+mCurrentLon+","+mCurrentLat+")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {                            //此处为 js 返回的结果
                        }
                    });*//*

                    break;*/

            }
        }

        ;
    };


    @Override
    public int getContentView() {
        return R.layout.a_vue;
    }

    @Override
    public void initView() {

        Log.i("sha1",sHA1(this));



        //版本更新
        checkVersion();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_reload = (ImageView) findViewById(R.id.iv_reload);
        mWebview = (WebView) findViewById(R.id.mWebview);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        common_network_error = (LinearLayout) findViewById(R.id.common_network_error);

        WebSettings webSettings = mWebview.getSettings();


        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSettings.setSavePassword(true);

        webSettings.setSaveFormData(true);

        //地理位置
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // webSettings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage
        // 设置编码
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置可以支持缩放
        webSettings.setSupportZoom(false);
        // 设置支持js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 关闭缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //  设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //  设置UserAgent（安全起见）
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " cbw");
        // 自适应屏幕
        webSettings.setLoadWithOverviewMode(true);


//可以有缓存
        webSettings.setAppCacheEnabled(true);
        //设置允许访问文件数据
        webSettings.setAllowFileAccess(true);
        //可以有数据库
        webSettings.setDatabaseEnabled(true);
        //设置定位的数据库路径，若不设置定位数据库路径则无法使用定位功能
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);





        //webSettings.setAllowUniversalAccessFromFileURLs(true);


        // 设置本地调用对象及其接口
        //mWebview.addJavascriptInterface(new JavaScriptObject(mActivity), "android");
        mWebview.addJavascriptInterface(new MyBridge(VueActivity.this, mHandler), "bridge");


        mWebview.setWebChromeClient(new WebChromeClient() {

            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
                Log.i("log", "onGeolocationPermissionsHidePrompt");
            }

            //配置权限（同样在WebChromeClient中实现）
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                //定位服务
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);

                Log.i("log", "onGeolocationPermissionsShowPrompt");

            }


/*
//配置权限（同样在WebChromeClient中实现）
//定位服务 带弹窗
 */

/*            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
                Log.i("log", "onGeolocationPermissionsHidePrompt");
            }


            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin,
                                                           final GeolocationPermissions.Callback callback) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VueActivity.this);
                builder.setMessage("需要获取您的地理位置");
                DialogInterface.OnClickListener dialogButtonOnClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int clickedButton) {
                        if (DialogInterface.BUTTON_POSITIVE == clickedButton) {
                            callback.invoke(origin, true, true);
                        } else if (DialogInterface.BUTTON_NEGATIVE == clickedButton) {
                            callback.invoke(origin, false, false);
                        }
                    }
                };
                builder.setPositiveButton("同意", dialogButtonOnClickListener);
                builder.setNegativeButton("不同意", dialogButtonOnClickListener);
                builder.show();
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                Log.i("log", "onGeolocationPermissionsShowPrompt");

                callback.invoke(origin, true, true);
            }*/



            /*
            打开相机相册
             */

            /**
             * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
             */
            private void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg) {
                Log.e("WangJ", "运行方法 openFileChooser-1");
                // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
                mUploadCallbackBelow = uploadMsg;
                takePhoto();
            }

            /**
             * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
             */
            public void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.e("WangJ", "运行方法 openFileChooser-2 (acceptType: " + acceptType + ")");
                // 这里我们就不区分input的参数了，直接用拍照
                openFileChooser(uploadMsg);
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
             */
            public void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("WangJ", "运行方法 openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
                // 这里我们就不区分input的参数了，直接用拍照
                openFileChooser(uploadMsg);
            }

            /**
             * API >= 21(Android 5.0.1)回调此方法
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                Log.e("WangJ", "运行方法 onShowFileChooser");
                // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = valueCallback;
                takePhoto();
                return true;
            }


            // 获取到html的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                progress = newProgress;

/*                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }*/

                super.onProgressChanged(view, newProgress);
            }


        });


        mWebview.setWebViewClient(new WebViewClient() {

            //拨打电话
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("用户单击超连接", url);
                //判断用户单击的是那个超连接
                String tag = "tel";
                if (url.contains(tag)) {
                    String mobile = url.substring(url.lastIndexOf("/") + 1);
                    Log.e("mobile----------->", mobile);
                    Intent mIntent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse(mobile);
                    mIntent.setData(data);
                    //Android6.0以后的动态获取打电话权限
                    if (ActivityCompat.checkSelfPermission(VueActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(mIntent);
                        //这个超连接,java已经处理了，webview不要处理
                        return true;
                    } else {
                        //申请权限
                        ActivityCompat.requestPermissions(VueActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        return true;
                    }
                }


                //支付宝支付uri拦截
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
                return true;
            }


/*            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(mUrl + "?Latitude=" + mCurrentLat + "&Longitude=" + mCurrentLon);
                return true;
            }*/


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        });


        // 改写物理按键——返回的逻辑
        mWebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
                        mWebview.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });

        if (AppUtil.isNetworkAvailable(mContext)) {
            findViewById(R.id.common_network_error).setVisibility(View.GONE);
            findViewById(R.id.page_content).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.common_network_error).setVisibility(View.VISIBLE);
            findViewById(R.id.page_content).setVisibility(View.GONE);
            return;
        }


         /*统一申请权限*/
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(VueActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(VueActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(VueActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (ContextCompat.checkSelfPermission(VueActivity.this, Manifest.
                permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(VueActivity.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }


        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            /*使用ActivityCompat 统一申请权限 */
            ActivityCompat.requestPermissions(VueActivity.this, permissions, 1);
        } else {
            requestLocation();
            //requestLocation();
        }


        //mWebview.loadUrl(mUrl);


/*        mWebview.addJavascriptInterface(new MyBridge(VueActivity.this, mHandler), "bridge");

        mWebview.loadUrl("file:///android_asset/javascript.html");*/

        //mWebview.loadUrl("file:///android_asset/javascript.html");



/*        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

/*        Log.i("777", "777");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Thread.sleep(2000);

                        Message msg = new Message();
                        msg.what = 555;
                        mHandler.sendMessage(msg);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/








/*        mWebview.loadUrl("javascript:upDetaGeography("+mCurrentLon+")");*/

/*        mWebview.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    // 注意调用的JS方法名要对应上
                    // 调用javascript的callJS()方法
                    mWebview.loadUrl("javascript:upDetaGeography("+mCurrentLon+")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });*/


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
        iv_reload.setOnClickListener(this);
        common_network_error.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mWebview.goBack();// 返回前一个页面
                break;
            case R.id.iv_reload:
                mWebview.loadUrl(mUrl + "?Latitude=" + mCurrentLat + "&Longitude=" + mCurrentLon + "&type=3");

/*                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain"); //分享的是文本类型
                shareIntent.putExtra(Intent.EXTRA_TEXT, "文本内容");//分享出去的内容
                startActivity(shareIntent);    //注意这里的变化*/

                break;
            case R.id.btn_network_error_reload:
                initView();
                mWebview.loadUrl(mUrl + "?Latitude=" + mCurrentLat + "&Longitude=" + mCurrentLon + "&type=3");
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mWebview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebview.onPause();
    }

    @Override
    public void onDestroy() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.onDestroy();
    }

    /**
     * 系统自动的返回按钮处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();// 返回前一个页面
            return true;
        } else {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }


    //    //版本更新弹框
    protected void showUpdataDialog(String msg, final String loadurl, boolean isUpdate) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this, R.style.Dialog_Alert_Self);
        builer.setTitle("版本升级");
        builer.setMessage(msg);
        builer.setCancelable(!isUpdate);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                //http://static.oneplus.cn/data/attachment/forum/201811/10/172919i5haakhk13aa3pfc.apk
                Uri content_url = Uri.parse(loadurl);
                //Uri content_url = Uri.parse("http://static.oneplus.cn/data/attachment/forum/201811/10/172919i5haakhk13aa3pfc.apk");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        if (!isUpdate) {
            //当点取消按钮时进行登录
            builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        AlertDialog dialog = builer.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff8200"));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ff8200"));
    }


    //检测是否需要自动更新
    public void checkVersion() {
        MyParams params = new MyParams();
/*        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));*/

//安卓 Android救援端 =0。Android用户端=1
        params.put("type", "1");


        VictorHttpUtil.doPost(false, mContext, Define.CHECK, params, true, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);
                AboutBean aboutBean = JSON.parseObject(element.getRows(), AboutBean.class);

                Log.i("banben", element.getRows());

                if ((AppUtil.getVersionName(mContext) + "").equals(aboutBean.getApkEdition())) {//最新版本
/*                if ((AppUtil.getVersionName(mContext)+"").equals("1.0")){//最新版本*/
                    //MyApplication.showToast("已是最新版本");
                } else {//不是最新版本 弹框下载
                    showUpdataDialog(aboutBean.getInfo(), aboutBean.getApkUrl(), aboutBean.is_compel());
                }
            }
        });
    }


    /***获取当前位置显示当前地图**/
    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            //Log.i("233","233");

            //获取纬度信息
            mCurrentLat = location.getLatitude();
            //获取经度信息
            mCurrentLon = location.getLongitude();
            //获取定位精度，默认值为0.0f
            mCurrentAccracy = location.getRadius();

            //Log.i("weizhi", "123??" + mCurrentLat + "\n" + mCurrentLon);



            //Toast.makeText(VueActivity.this,""+mCurrentLat + "\n" + mCurrentLon,Toast.LENGTH_SHORT).show();

            if (!show) {
                mWebview.loadUrl(mUrl + "?Latitude=" + mCurrentLat + "&Longitude=" + mCurrentLon + "&type=3");

                Log.i("mUrl", mUrl + "?Latitude=" + mCurrentLat + "&Longitude=" + mCurrentLon + "&type=3");

                show = true;
            }

            // 只需要将第一种方法的loadUrl()换成下面该方法即可
            mWebview.evaluateJavascript("javascript:upDetaGeography("+mCurrentLon+","+mCurrentLat+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {                            //此处为 js 返回的结果
                }
            });


        }
    }


    /**
     * 重写Activity 方法返回申请权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mContext, "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(mContext, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }


    public void requestLocation() {
 /*开始定位*/
        //初始化定位
        mLocClient = new LocationClient(VueActivity.this);
        //注册定位监听
        mLocClient.registerLocationListener(new MyLocationListener());


        //定位选项
        LocationClientOption option = new LocationClientOption();
        /**
         * coorType - 取值有3个：
         * 返回国测局经纬度坐标系：gcj02
         * 返回百度墨卡托坐标系 ：bd09
         * 返回百度经纬度坐标系 ：bd09ll
         */
        option.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        //设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"， 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        //设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
        /**
         * 设置定位模式
         * Battery_Saving
         * 低功耗模式
         * Device_Sensors
         * 仅设备(Gps)模式
         * Hight_Accuracy
         * 高精度模式
         */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);//设置是否打开gps进行定位
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(1000);

        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

//        option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先

        //设置 LocationClientOption
        mLocClient.setLocOption(option);

        //开始定位
        mLocClient.start();
    }


    //    price	float	是	充值金额
    private void getAliPay(String price) {
        String uuid = PreferencesUtils.getString(mContext, Constants.USER_UUID);

        MyParams params = new MyParams();
        params.put("userid", uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));

        params.put("price", price);

        VictorHttpUtil.doPost(false, mContext, Define.ALIPAY, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        //JSONObject obj = null;//最外层的JSONObject对象
/*                        try {*/
                        //obj = new JSONObject(element.rows);

                        //Log.i("zhifu",obj.toString());

                        //orderInfo = obj.getString("response");

                        orderInfo = element.rows;

/*                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(mActivity);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                LogUtil.i(result.toString());

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }


    private void getAliPayState(String no) {
        String uuid = PreferencesUtils.getString(mContext, Constants.USER_UUID);

        MyParams params = new MyParams();
        params.put("userid", uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));

        params.put("out_trade_no", no);

//        VictorHttpUtil.doPost(false, mContext, Define.ALIPAY_STATE_URL, params, true, "加载中...",
//                new BaseHttpCallbackListener<Element>() {
//                    @Override
//                    public void callbackSuccess(String url, Element element) {
//                        super.callbackSuccess(url, element);
//
//                        JSONObject obj = null;//最外层的JSONObject对象
//                        try {
//                            obj = new JSONObject(element.data);
//                            String tjh_status = obj.getString("tjh_status");
//                            if ("1".equals(tjh_status)){
//                                finish();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }


    /**
     * 调用相机
     */
    private void takePhoto() {
        // 指定拍照存储位置的方式调起相机
        String filePath = Environment.getExternalStorageDirectory() + File.separator
                + Environment.DIRECTORY_PICTURES + File.separator;
        String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        imageUri = Uri.fromFile(new File(filePath + fileName));

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        Intent Photo = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(Photo, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

        startActivityForResult(chooserIntent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        Log.e("WangJ", "返回调用方法--chooseBelow");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e("WangJ", "系统返回URI：" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                Log.e("WangJ", "自定义结果：" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        Log.e("WangJ", "返回调用方法--chooseAbove");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    for (Uri uri : results) {
                        Log.e("WangJ", "系统返回URI：" + uri.toString());
                    }
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                Log.e("WangJ", "自定义结果：" + imageUri.toString());
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }



    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }




}
