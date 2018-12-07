package com.tianjistar.help.aaa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

import com.tianjistar.help.R;
import com.tianjistar.help.utils.ToastUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;

/**
 * Created by zitan on 2018/5/2.
 */

public class MyBridge {
    private Context context;

    Handler handler;

    String yCurrentLon = "0";
    String yCurrentLat = "0";

    public MyBridge(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

/*    @JavascriptInterface
    public void showMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void toastMessage(String message) {
        Log.i("toastMessage", "传递过来的值是： " + message);
    }

    @JavascriptInterface
    public String getMessage(String s) {
        return s + "world !";
    }


    @JavascriptInterface
    public void payPrice(double p) {
        //Toast.makeText(context, "p, Toast.LENGTH_SHORT).show();

        Message msg = new Message();
        msg.what = 2;
        msg.obj = p;
        handler.sendMessage(msg);
    }


    @JavascriptInterface
    public void sharApp(String s) {
*//*        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); //分享的是文本类型
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);//分享出去的内容
        context.startActivity(shareIntent);    //注意这里的变化*//*

    }*/





/*    @JavascriptInterface
    public double payPrice(double p) {
        return p;
    }*/


    private void showShare(String title, final String content, String icon_url, String img_url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);

/*        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");*/

        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

        //oks.setImageUrl("https://mmbiz.qlogo.cn/mmbiz_png/v4xOHcWSBxgbXKnibttdDzcrpEHfBZW1JKLyz2fDJFUdeHMgtbNjogEp7uibe0wtIibS9XsiaTTa0R8wvVibP0ibFibmQ/0?wx_fmt=png");

        oks.setImageUrl(icon_url);


        // url仅在微信（包括好友和朋友圈）中使用
/*
        oks.setUrl("http://sharesdk.cn");
*/
        //oks.setUrl("http://ty.tianjistar.com/mobile/#/user/home");
        oks.setUrl(img_url);

        //http://ty.tianjistar.com/mobile/#/user/home

/*        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");*/



/*        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(context, "分享完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(context, "分享错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
            }
        });*/


        oks.setCallback(new OnekeyShareThemeImpl() {
            @Override
            protected void showPlatformPage(Context context) {
/*                Toast.makeText(context, "showPlatformPage", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            protected void showEditPage(Context context, Platform platform, Platform.ShareParams shareParams) {
/*                Toast.makeText(context, "showEditPage", Toast.LENGTH_SHORT).show();*/
            }
        });



// 启动分享GUI
        oks.show(context);

        /*
        showShare(title,content,icon_url,img_url);
*/





    }


    public void sharApp(String s) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); //分享的是文本类型
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);//分享出去的内容
        context.startActivity(shareIntent);    //注意这里的变化
    }


    @JavascriptInterface
    public void shareInAndroid(String title, String content, String icon_url, String img_url) {

        //Toast.makeText(context, title + "\n" + content + "\n" + icon_url + "\n" + img_url + "\n", Toast.LENGTH_SHORT).show();

        //sharApp(img_url);

        showShare(title,content,icon_url,img_url);
    }


    @JavascriptInterface
    public void startMapInAndroid(String address_name, String mCurrentLon, String mCurrentLat, String yaddress_name, String yCurrentLon, String yCurrentLat) {
        //Toast.makeText(context, address_name + "\n" + mCurrentLon + "\n" + mCurrentLat + "\n" + yaddress_name + "\n" + yCurrentLon + "\n" + yCurrentLat, Toast.LENGTH_SHORT).show();

        this.yCurrentLon = yCurrentLon;
        this.yCurrentLat = yCurrentLat;

        //openTencent(34.7768160000,113.7338290000,"救援地点");

        startGaode();

        //show1();


/*        if (isAvilible(context,"com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+yCurrentLon+","+yCurrentLat +", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        }else {
            Toast.makeText(context, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();

            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);  }*/


    }


    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    private void show1() {
        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();


        TextView baidu = contentView.findViewById(R.id.baidu);
        TextView gaode = contentView.findViewById(R.id.gaode);



        if (isAvilible(context, "com.baidu.BaiduMap")) {
            baidu.setVisibility(View.VISIBLE);
        }

        if (isAvilible(context, "com.autonavi.minimap")) {
            gaode.setVisibility(View.VISIBLE);
        }

        if(!isAvilible(context, "com.baidu.BaiduMap")&&!isAvilible(context, "com.autonavi.minimap")){
            Toast.makeText(context,"未安装地图软件，请前往应用商店下载",Toast.LENGTH_SHORT).show();
        }

        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "baidu", Toast.LENGTH_SHORT).show();
                startBaidu();
            }
        });

        gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "gaode", Toast.LENGTH_SHORT).show();
                startGaode();
            }
        });


    }


    public void startBaidu() {
        if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
            try {
//                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:" + yCurrentLon + "," + yCurrentLat + "|name:救援地点" +        //终点
                        "&mode=driving&" +          //导航路线方式
                        "region=" +           //
                        "&src=天佑#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
/*            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);*/
        }
    }


    public void startGaode() {
        if (isAvilible(context, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=天佑&poiname=救援地点&lat=" + yCurrentLon + "&lon=" + yCurrentLat + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
/*            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);*/
        }
    }




    /**
     * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
     *
     * 公交：type=bus，policy有以下取值
     * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
     * 驾车：type=drive，policy有以下取值
     * 0：较快捷 、 1：无高速 、 2：距离短
     * policy的取值缺省为0
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    private void openTencent(double dlat, double dlon, String dname) {
        if (isAvilible(context, "com.tencent.map")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("qqmap://map/routeplan?type=bus&from=我的位置&fromcoord=0,0"
                    + "&to=" + dname
                    + "&tocoord=" + dlat + "," + dlon
                    + "&policy=1&referer=myapp"));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "您尚未安装腾讯地图", Toast.LENGTH_LONG).show();
        }
    }






}
