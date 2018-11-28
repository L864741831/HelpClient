package com.tianjistar.help.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.tianjistar.help.R;
import com.tianjistar.help.activity.InsuranceProductsActivity;
import com.tianjistar.help.activity.login.LoginActivity;
import com.tianjistar.help.activity.persion.HelpCardActivity;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.BaseFragment;
import com.tianjistar.help.bean.FindAddressBean;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.StringUtils;
import com.tianjistar.help.view.baidu.MyOverLay;
import com.tianjistar.help.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener  {

    public LocationClient mLocationClient;

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    //纬度
    public double mCurrentLat = 0.0;
    //经度
    public double mCurrentLon = 0.0;
    //定位精度
    private float mCurrentAccracy;
    //是否首次定位
    private boolean isFirstLoc = true;

    @Bind(R.id.ll_down_layout)
    LinearLayout ll_down_layout;

    @Bind(R.id.ll_up_layout)
    LinearLayout ll_up_layout;

    //救援卡
    @Bind(R.id.ll_frist_helpcard)
    LinearLayout linearLayoutHelpcard;
    //保险理财
    @Bind(R.id.ll_frist_insurance)
    LinearLayout linearLayoutInsurance;

    //一键呼救布局
    @Bind(R.id.in_frist)
    LinearLayout view1;

    //点击显示一键呼救
    @Bind(R.id.iv_down)
    ImageView imageView;

    @Bind(R.id.rl_feist_on_help)
    RelativeLayout relativeLayoutOneHelp;//一键呼救

    @Bind(R.id.iv)
    ImageView imageViewCard;


    @Bind(R.id.ll_vp)
    LinearLayout linearLayoutVp;

    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.tv_frist_position)
    TextView textViewPosition;
    @Bind(R.id.et_search)
    ClearEditText clearEditText;

    PoiSearch mPoiSearch;

//    @Bind(R.id.indicator)
//    SmartTabLayout indicator;

    @Bind(R.id.viewpager)
    ViewPager pager;
//    DisallowParentTouchViewPager pager;


    @Bind(R.id.rl_down)
    RelativeLayout rl_down;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    private FragmentPagerAdapter adapter;
    private String[] CONTENT = new String[]{"急救小常识", "系统公告"};
    private String[] CONTENT_CODE = {"1", "2"};

    private LocationClient mLocClient;

    //网络获取的救援点数据
    private List<FindAddressBean> addressBeanList;
    //加载自定义的地图救援点图标
    private View v_temp;
    private TextView img_temp,tv_temp;

    // 2.1 定义用来与外部activity交互，获取到宿主activity
    private FragmentInteraction listerner;
    private UiSettings settings;

    // 1 定义了所有activity必须实现的接口方法
    public interface FragmentInteraction {
        void process(double str,double str2);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInteraction) {
            listerner = (FragmentInteraction)context; // 2.2 获取到宿主activity并赋值
        } else{
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_home4;
    }

    @Override
    public void initView() {
        mMapView = (MapView)findViewById(R.id.bmapView);
        /*获取百度map控件*/
        mBaiduMap = mMapView.getMap();

//        settings = mBaiduMap.getUiSettings();

        /*统一申请权限*/
        List<String> permissionList  = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions= permissionList.toArray(new String[permissionList.
                    size()]);
            /*使用ActivityCompat 统一申请权限 */
            ActivityCompat.requestPermissions(getActivity(),permissions,1);
        }else {
            /*开始定位*/
            requestLocation();
        }


        mTabLayout.addTab(mTabLayout.newTab().setText("1"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("2"));
        adapter = new GoogleMusicAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(pager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }

    @Override
    public void initData() {
        addressBeanList = new ArrayList<>();
        initCustomhelPoints();

//        mPoiSearch = PoiSearch.newInstance();
//        //创建POI检索实例
//        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
//        /*实例化 定位客户端*/
//        mLocationClient = new LocationClient(getActivity().getApplicationContext());
//        /*注册回调方法,显示定位结果信息*/
//        mLocationClient.registerLocationListener(new MyLocationListener());
//        /*初始化SDK 为了显示地图*/
////        SDKInitializer.initialize(getActivity().getApplicationContext());
//
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//
//        // 不显示缩放比例尺
//        mMapView.showZoomControls(false);
//        // 不显示百度地图Logo
//        mMapView.removeViewAt(1);

        findNearByHelpAddress("");
    }

    @Override
    public void initListener() {
        ll_down_layout.setOnClickListener(this);
        ll_up_layout.setOnClickListener(this);
        linearLayoutInsurance.setOnClickListener(this);
        linearLayoutHelpcard.setOnClickListener(this);
        relativeLayoutOneHelp.setOnClickListener(this);

        clearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    mBaiduMap.clear();
                    findNearByHelpAddress(clearEditText.getText().toString());
                }
                return false;
            }
        });

         /*覆盖物点击事件*/
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MyApplication.showToast("点击了"+marker.getTitle()+marker.getPosition());
                return true;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_down_layout://线隐藏底部，显示一键呼救
                linearLayoutVp.setVisibility(View.GONE);
                imageViewCard.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
                EventBus.getDefault().post("1");

                rl_down.setVisibility(View.GONE);
                mTabLayout.setVisibility(View.GONE);
                break;
            case R.id.ll_up_layout://线隐藏底部，显示初始页面
                linearLayoutVp.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                imageViewCard.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                EventBus.getDefault().post("2");

                rl_down.setVisibility(View.VISIBLE);
                mTabLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_frist_insurance://保险理财
                MyApplication.showToast("保险理财");
//                MyApplication.openActivity(mContext,InsuranceProductsActivity.class);
                break;
            case R.id.ll_frist_helpcard://救援卡
                MyApplication.openActivity(mContext, HelpCardActivity.class);
                break;
            case R.id.rl_feist_on_help://一键呼救
//                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + "15738776423"));//跳转到拨号界面，同时传递电话号码
//                startActivity(Intent);
                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                    String dim = String.valueOf(mCurrentLat);
                    String lon = String.valueOf(mCurrentLon);
                    onKeyHelp(dim,lon);
                }else {
                    MyApplication.showToast("请先登录");
                    MyApplication.openActivity(mContext, LoginActivity.class);
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Subscribe(priority = 1,threadMode = ThreadMode.MAIN)
    public void onReceiveAccount(String type){
        if (type.equals("3")){
            Log.i("info","---size---"+addressBeanList.size());
            if(addressBeanList.size()<0 || addressBeanList.size()==0){
                findNearByHelpAddress("");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mPoiSearch.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**开始定位*/
    private void requestLocation(){
        //地图状态改变相关监听
//        mBaiduMap.setOnMapStatusChangeListener(this);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位图层显示方式
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        /**
         * 设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效
         * customMarker用户自定义定位图标
         * enableDirection是否允许显示方向信息
         * locationMode定位图层显示方式
         */
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));

        //初始化定位
        mLocClient = new LocationClient(mActivity);
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


//        initLocation();
//        /*开始定位*/
//        mLocationClient.start();
    }

    /**设置5000ms更新一次坐标位置信息*/
    private void initLocation(){
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().zoom(18).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        LocationClientOption option = new  LocationClientOption();
        option.setScanSpan(5000);// 多长时间进行一次请求
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 高精度
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);// 使用设置
    }

    /**重写Activity 方法返回申请权限结果*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length > 0) {
                    for (int result:grantResults) {
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(mContext,"必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(mContext,"发生未知错误",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }

    /***获取当前位置显示当前地图**/
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {

//            String addr = location.getAddrStr();    //获取详细地址信息
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
//            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息

//            Log.i("info","---location---"+district);
            /*显示当前位置地图*/
            textViewPosition.setText(district);

            if (location == null || mBaiduMap == null) {
//            if (location == null || mMapView == null) {
                return;
            }
            //获取纬度信息
            mCurrentLat = location.getLatitude();
            //获取经度信息
            mCurrentLon = location.getLongitude();
            //获取定位精度，默认值为0.0f
            mCurrentAccracy = location.getRadius();

//            Log.i("info","---mCurrentAccracy---"+mCurrentAccracy);

            //定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    //定位精度location.getRadius()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection())
                    //经度
                    .latitude(location.getLatitude())
                    //纬度
                    .longitude(location.getLongitude())
                    //构建
                    .build();
            //设置定位数据
            mBaiduMap.setMyLocationData(locData);

            if (StringUtils.isNotBlank(district)){
                if (isFirstLoc){
                    isFirstLoc = false;
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(latLng,18);
                    mBaiduMap.animateMapStatus(mapStatus);
                }
            }

            //实例化UiSettings类对象
//            mUiSettings = mBaiduMap.getUiSettings();
//            mUiSettings.setCompassEnabled(false);
//            mUiSettings.setScrollGesturesEnabled(true);
//            mUiSettings.setZoomGesturesEnabled(true);

//            if (isFirstLoc) {
//                Log.i("info","---isFirstLoc---"+isFirstLoc);
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll)
//                        .zoom(18);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }


//            //POI搜索，周边检索,
//
//            //定义Maker坐标点,深圳大学经度和纬度113.943062,22.54069
//            //设置的时候经纬度是反的 纬度在前，经度在后
//            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
//            //获得Key
//            String key = clearEditText.getText().toString();
//
//            //周边检索
//            PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
//            nearbySearchOption.location(point);
//            nearbySearchOption.keyword(key);
////            nearbySearchOption.keyword("美食");
//            nearbySearchOption.radius(10000);// 检索半径，单位是米
//            mPoiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求

//            String dim = String.valueOf(mCurrentLat);
//            String lon = String.valueOf(mCurrentLon);
//            EventBus.getDefault().post(new MessageCoordEvent(dim,lon));

            listerner.process(mCurrentLat,mCurrentLon);
        }
    }

    /***获取POI检索结果**/
    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){

        public void onGetPoiResult(PoiResult poiResult){
            //获取POI检索结果
            //如果搜索到的结果不为空，并且没有错误
            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
                MyOverLay overlay = new MyOverLay(mBaiduMap, mPoiSearch);//这传入search对象，因为一般搜索到后，点击时方便发出详细搜索
                //设置数据,这里只需要一步，
                overlay.setData(poiResult);
                //添加到地图
                overlay.addToMap();
                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                overlay.zoomToSpan();//计算工具
                //设置标记物的点击监听事件
                mBaiduMap.setOnMarkerClickListener(overlay);
            } else {
                Toast.makeText(getActivity().getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
            }
        }

        public void onGetPoiDetailResult(PoiDetailResult result){
            //获取Place详情页检索结果
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private void findNearByHelpAddress(final String address){
        MyParams params = new MyParams();
        if (StringUtils.isNotBlank(address)){
            params.put("name", address);
        }

        VictorHttpUtil.doPost(false, mContext, Define.NEARBY_HELP_ADDRESS, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        //创建OverlayOptions的集合
                        List<OverlayOptions> options = new ArrayList<>();
                        //构建Marker图标
//                        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                .fromResource(R.drawable.pic);

                        addressBeanList = JSON.parseArray(element.rows,FindAddressBean.class);
                        for (int i = 0; i < addressBeanList.size(); i++) {
                            double dim = Double.parseDouble(addressBeanList.get(i).getDimensions());
                            double log = Double.parseDouble(addressBeanList.get(i).getLongitude());

                            //设置要显示的文本
                            tv_temp.setText("救援点");
//                            tv_temp.setText(addressBeanList.get(i).getName());
                            img_temp.setText(""+(i+1));
                            BitmapDescriptor  bitmap = BitmapDescriptorFactory.fromView(v_temp);//用到了这个实例化方法来把自定义布局实现到marker中。

                            //设置坐标点
                            LatLng point1 = new LatLng(dim, log);

                            //创建OverlayOptions属性
                            OverlayOptions option1 =  new MarkerOptions()
                                    .position(point1)
                                    .icon(bitmap)
                                    .title("救援点");
//                                    .title(addressBeanList.get(i).getName());
                            //将OverlayOptions添加到list
                            options.add(option1);
                        }
                        //在地图上批量添加
                        mBaiduMap.addOverlays(options);
                    }

                });
    }

    private void initCustomhelPoints(){
        v_temp = LayoutInflater.from(mActivity).inflate(R.layout.map_sign_layout, null);//加载自定义的布局
        img_temp = v_temp.findViewById(R.id.baidumap_custom_img);//获取自定义布局中的imageview
        tv_temp = v_temp.findViewById(R.id.baidumap_custom_text);//获取自定义布局中的textview
    }

    private void onKeyHelp(String dimensions,String longitude){
        MyParams params = new MyParams();
        //34.7402018931,113.6401748657
        params.put("dimensions", dimensions);
        params.put("longitude", longitude);

        params.put("app","yes");


        params.put("uuid", PreferencesUtils.getString(mContext, Constants.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mActivity));


        VictorHttpUtil.doPost(false, mContext, Define.CALL_FOR_HELP, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        MyApplication.showToast("收到急救，请稍后！");
                    }
                });
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {

        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    fragment = new CommonSenseFragment2();
                    bundle.putString("type",CONTENT_CODE[0]);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new CommonSenseFragment2();
                    bundle.putString("type",CONTENT_CODE[1]);
                    fragment.setArguments(bundle);
                    break;
                default:
                    fragment = new CommonSenseFragment2();
                    bundle.putString("type",CONTENT_CODE[0]);
                    fragment.setArguments(bundle);
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }


    }

}
