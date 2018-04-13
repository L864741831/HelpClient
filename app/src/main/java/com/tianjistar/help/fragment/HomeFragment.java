package com.tianjistar.help.fragment;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.tianjistar.help.R;
import com.tianjistar.help.activity.persion.HelpCardActivity;
import com.tianjistar.help.adapter.FristPagerAdapter;
import com.tianjistar.help.adapter.HelpRecoderViewPagerAdapter;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.BaseFragment;
import com.tianjistar.help.view.DisallowParentTouchViewPager;
import com.tianjistar.help.view.baidu.MyOverLay;
import com.tianjistar.help.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.tianjistar.help.utils.Tools.dip2px;


/**
 * 首页
 *
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public LocationClient mLocationClient;
    @Bind(R.id.bmapView)
     MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLocate  = true;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private int mCurrentDirection = 0;
    boolean isFirstLoc = true; // 是否首次定位
    private TabLayout tabLayout;
    private ViewPager vp;
    private String[] title;
    private ArrayList<CommonSenseFragment> list;
    @Bind(R.id.tv_frist_xian)
    TextView textViewXian;
    @Bind(R.id.tv_frist_xian1)
    TextView textViewXian1;
    @Bind(R.id.ll_frist_insurance)
    LinearLayout linearLayoutInsurance;//保险理财
    @Bind(R.id.ll_frist_helpcard)
    LinearLayout linearLayoutHelpcard;//救援看
    @Bind(R.id.coo_frist)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.in_frist)
    LinearLayout view1;//一键呼救布局
    @Bind(R.id.iv_1)
    ImageView imageView;
    @Bind(R.id.rl_feist_on_help)
    RelativeLayout relativeLayoutOneHelp;//一键呼救
    @Bind(R.id.ll_1)
    LinearLayout linearLayout;
    @Bind(R.id.iv)
    ImageView imageViewCard;
    @Bind(R.id.tv_xin1)
    TextView textView1;
    @Bind(R.id.tv_xin)
    TextView textView;
    @Bind(R.id.ll_vp)
    LinearLayout linearLayoutVp;
    @Bind(R.id.rl_frist)
    RelativeLayout relativeLayout;
    @Bind(R.id.appl)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_frist_position)
    TextView textViewPosition;
    @Bind(R.id.et_search)
    ClearEditText clearEditText;
    PoiSearch mPoiSearch;
    @Bind(R.id.indicator)
    SmartTabLayout indicator;
    @Bind(R.id.viewpager)
    DisallowParentTouchViewPager pager;
    private FragmentPagerAdapter adapter;
    private static final String[] CONTENT = new String[]{"急救小常识", "系统公告"};
    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        /*实例化 定位客户端*/
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        /*注册回调方法,显示定位结果信息*/
        mLocationClient.registerLocationListener(new MyLocationListener());
        /*初始化SDK 为了显示地图*/
        SDKInitializer.initialize(getActivity().getApplicationContext());
        mMapView = (MapView) findViewById(R.id.bmapView);
        /*获取百度map控件*/
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mMapView.removeViewAt(1);
        /*覆盖物点击事件*/
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MyApplication.showToast("点击了"+marker.getTitle()+marker.getPosition());
                return true;
            }
        });


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
        /**
         * viewpage
         */
        adapter = new GoogleMusicAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        indicator.setViewPager(pager);
        setListener();

    }
    /*监听事件*/
    private void setListener() {
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
        textViewXian.setOnClickListener(this);
        textViewXian1.setOnClickListener(this);
        linearLayoutInsurance.setOnClickListener(this);
        linearLayoutHelpcard.setOnClickListener(this);
        relativeLayoutOneHelp.setOnClickListener(this);
        /*触摸事件显示与隐藏*/
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        AppBarLayout.LayoutParams lp= (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
                        lp.setScrollFlags(0);
                        linearLayoutVp.setVisibility(View.GONE);
                        imageViewCard.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        textViewXian.setVisibility(View.GONE);
                        indicator.setVisibility(View.GONE);
                        view1.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);
                        textView1.setVisibility(View.GONE);
                        EventBus.getDefault().post("1");
                        break;
                }
                return true;
            }
        });

        view1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        AppBarLayout.LayoutParams lp= (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
                        lp.setScrollFlags(5);
                        linearLayoutVp.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.GONE);
                        imageViewCard.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        textViewXian.setVisibility(View.VISIBLE);
                        indicator.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        textView1.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post("2");
                        break;
                }
                return true;
            }
        });
    }
    /**开始定位*/
    private void requestLocation(){
        initLocation();
        /*开始定位*/
        mLocationClient.start();
    }
    /**设置5000ms更新一次坐标位置信息*/
    private void initLocation(){
        LocationClientOption option = new  LocationClientOption();
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);

    }
    /**重写Activity 方法返回申请权限结果*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
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
            /*显示当前位置地图*/
            textViewPosition.setText(location.getDistrict());
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            //POI搜索，周边检索,

            //定义Maker坐标点,深圳大学经度和纬度113.943062,22.54069
            //设置的时候经纬度是反的 纬度在前，经度在后
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            //获得Key
            String key = clearEditText.getText().toString();
            //周边检索
            PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
            nearbySearchOption.location(point);
            nearbySearchOption.keyword("美食");
            nearbySearchOption.radius(700);// 检索半径，单位是米
            mPoiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求

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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_frist_xian://线隐藏底部，显示一键呼救
                AppBarLayout.LayoutParams lp= (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
                lp.setScrollFlags(0);
                linearLayoutVp.setVisibility(View.GONE);
                imageViewCard.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                textViewXian.setVisibility(View.GONE);
                indicator.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                EventBus.getDefault().post("1");
                break;
            case R.id.tv_frist_xian1://线隐藏底部，显示初始页面
                AppBarLayout.LayoutParams lp1= (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
                lp1.setScrollFlags(5);
                linearLayoutVp.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                imageViewCard.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                textViewXian.setVisibility(View.VISIBLE);
                indicator.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                EventBus.getDefault().post("2");
                break;
            case R.id.ll_frist_insurance://保险理财
                MyApplication.showToast("保险理财");
                break;
            case R.id.ll_frist_helpcard://救援卡
                MyApplication.openActivity(mContext, HelpCardActivity.class);
                break;
            case R.id.rl_feist_on_help://一键呼救
                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + "15738776423"));//跳转到拨号界面，同时传递电话号码
                startActivity(Intent);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
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
                    fragment = new CommonSenseFragment();
                    bundle.putString("type",CONTENT[0]);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new CommonSenseFragment();
                    bundle.putString("type",CONTENT[1]);
                    fragment.setArguments(bundle);
                    break;
                default:
                    fragment = new CommonSenseFragment();
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
