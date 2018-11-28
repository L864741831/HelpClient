package com.tianjistar.help.aaa;/*
package scut.carson_ho.socket_carson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

*/
/**
 * Created by Administrator on 2018/11/23.
 *//*


public class Map extends AppCompatActivity {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();


//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);


//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

*/
/*//*
/启动定位
        mLocationClient.startLocation();*//*





    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {


            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。

                    Log.i("address", ""
                            + aMapLocation.getLongitude() + "\n"
                            + aMapLocation.getLatitude());

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }


        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

*/
/*        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁*//*

        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    */
/*
    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
amapLocation.getLatitude();//获取纬度
amapLocation.getLongitude();//获取经度
amapLocation.getAccuracy();//获取精度信息
amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
amapLocation.getCountry();//国家信息
amapLocation.getProvince();//省信息
amapLocation.getCity();//城市信息
amapLocation.getDistrict();//城区信息
amapLocation.getStreet();//街道信息
amapLocation.getStreetNum();//街道门牌号信息
amapLocation.getCityCode();//城市编码
amapLocation.getAdCode();//地区编码
amapLocation.getAoiName();//获取当前定位点的AOI信息
amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
amapLocation.getFloor();//获取当前室内定位的楼层
amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
//获取定位时间
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = new Date(amapLocation.getTime());
df.format(date);
     *//*



    public void start(View v) {
//启动定位
        mLocationClient.startLocation();
    }


    public void stop(View v) {
//停止定位
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }


}
*/
