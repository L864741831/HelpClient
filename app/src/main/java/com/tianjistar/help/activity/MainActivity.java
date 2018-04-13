package com.tianjistar.help.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.base.BaseActivity;
import com.tianjistar.help.fragment.HomeFragment;
import com.tianjistar.help.fragment.PersionFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

public class MainActivity extends Base1Activity implements View.OnClickListener {
    @Bind(R.id.menu02)
    LinearLayout menu02;
    @Bind(R.id.ll_main)
    LinearLayout linearLayout;
    @Bind(R.id.iv_main)
    ImageView imageView;
    private LinearLayout menu01, menu03;
    private ImageView menu_icon01, menu_icon02, menu_icon03;
    private TextView menu_name01, menu_name02, menu_name03;
    private int myNum = 0;
    private int mNum;
    private PersionFragment mineFragment; //首页
    private HomeFragment homeFragment; //个人

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        //版本更新
//        checkVersion();
        menu01 = (LinearLayout) findViewById(R.id.menu01);
        menu02 = (LinearLayout) findViewById(R.id.menu02);
        menu03 = (LinearLayout) findViewById(R.id.menu03);
        menu_icon01 = (ImageView) findViewById(R.id.menu_icon01);
        menu_icon02 = (ImageView) findViewById(R.id.menu_icon02);
        menu_icon03 = (ImageView) findViewById(R.id.menu_icon03);
        menu_name01 = (TextView) findViewById(R.id.menu_name01);
        menu_name02 = (TextView) findViewById(R.id.menu_name02);
        menu_name03 = (TextView) findViewById(R.id.menu_name03);
        menu01.setOnClickListener(this);
        menu02.setOnClickListener(this);
        menu03.setOnClickListener(this);
        menu_icon02.setOnClickListener(this);
        InitViewPage(mNum);
    }

    public void InitViewPage(int num) {
        setFragmengList(num);
        switch (num) {
            case 0://首页
                menu_icon01.setBackgroundResource(R.drawable.ic_first);
                menu_icon02.setBackgroundResource(R.drawable.ic_phone);
                menu_icon03.setBackgroundResource(R.drawable.ic_unpersion);


                menu_name01.setTextColor(Color.parseColor("#f02b2b"));
                menu_name02.setTextColor(Color.parseColor("#f02b2b"));
                menu_name03.setTextColor(Color.parseColor("#808080"));

                MainActivity.this.getFragmengList(0);
                break;
            case 1://一键呼救
                menu_icon01.setBackgroundResource(R.drawable.ic_unfirst);
                menu_icon02.setBackgroundResource(R.drawable.ic_phone);
                menu_icon03.setBackgroundResource(R.drawable.ic_unpersion);


                menu_name01.setTextColor(Color.parseColor("#808080"));
                menu_name02.setTextColor(Color.parseColor("#f02b2b"));
                menu_name03.setTextColor(Color.parseColor("#808080"));
                MainActivity.this.getFragmengList(1);

                break;
            case 2://个人
                menu_icon01.setBackgroundResource(R.drawable.ic_unfirst);
                menu_icon02.setBackgroundResource(R.drawable.ic_phone);
                menu_icon03.setBackgroundResource(R.drawable.ic_persion);


                menu_name01.setTextColor(Color.parseColor("#808080"));
                menu_name02.setTextColor(Color.parseColor("#f02b2b"));
                menu_name03.setTextColor(Color.parseColor("#f02b2b"));
                MainActivity.this.getFragmengList(2);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu01:
                menu_icon01.setBackgroundResource(R.drawable.ic_first);
                menu_icon02.setBackgroundResource(R.drawable.ic_phone);
                menu_icon03.setBackgroundResource(R.drawable.ic_unpersion);

                menu_name01.setTextColor(Color.parseColor("#f02b2b"));
                menu_name02.setTextColor(Color.parseColor("#f02b2b"));
                menu_name03.setTextColor(Color.parseColor("#808080"));

                MainActivity.this.getFragmengList(0);
                break;
            case R.id.menu02:
                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL,Uri.parse("tel:" + "15738776423"));//跳转到拨号界面，同时传递电话号码
                startActivity(Intent);
                break;

            case R.id.menu03:
                menu_icon01.setBackgroundResource(R.drawable.ic_unfirst);
                menu_icon02.setBackgroundResource(R.drawable.ic_phone);
                menu_icon03.setBackgroundResource(R.drawable.ic_persion);

                menu_name01.setTextColor(Color.parseColor("#808080"));
                menu_name02.setTextColor(Color.parseColor("#f02b2b"));
                menu_name03.setTextColor(Color.parseColor("#f02b2b"));

                MainActivity.this.getFragmengList(2);
                break;
            case R.id.menu_icon02:
                Intent Intent1 =  new Intent(android.content.Intent.ACTION_DIAL,Uri.parse("tel:" + "15738776423"));//跳转到拨号界面，同时传递电话号码
                startActivity(Intent1);
                break;

        }

    }

    public void getFragmengList(int num) {
        if (num == myNum) {
        } else {
            myNum = num;
            this.setFragmengList(num);
        }
        Log.e("num", "num: " + num);
    }

    public void setFragmengList(int num) {
        mNum = num;
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (num) {
            case 0://首页
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1://记录
                break;
            case 2://个人
                if ( mineFragment== null) {
                    mineFragment = new PersionFragment();

                    transaction.add(R.id.fl_main, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }


    }

//接收首页发送的广播隐藏与显示底部导航栏
    @Subscribe(priority = 1,threadMode = ThreadMode.MAIN)
    public void onReceiveAccount(String type){
        if (type.equals("1")){
            imageView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }else if (type.equals("2")){
            imageView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 返回键事件
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                mExitTime = System.currentTimeMillis();
//
//            } else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
        }
        return super.onKeyDown(keyCode, event);

    }

//    //版本更新弹框
//    protected void showUpdataDialog(String msg, final String loadurl,boolean isUpdate) {
//        AlertDialog.Builder builer = new AlertDialog.Builder(this,R.style.Dialog_Alert_Self) ;
//        builer.setTitle("版本升级");
//        builer.setMessage(msg);
//        builer.setCancelable(!isUpdate);
//        //当点确定按钮时从服务器上下载 新的apk 然后安装
//        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(loadurl);
//                intent.setData(content_url);
//                startActivity(intent);
//            }
//        });
//        if(!isUpdate){
//            //当点取消按钮时进行登录
//            builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//        }
//        AlertDialog dialog = builer.create();
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff8200"));
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ff8200"));
//    }
//    //检测是否需要自动更新
//    public void checkVersion(){
//        MyParams params=new MyParams();
//        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
//        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
//        VictorHttpUtil.doPost(false, mContext, Define.URL_about, params, true, null, new BaseHttpCallbackListener<Element>() {
//            @Override
//            public void callbackSuccess(String url, Element element) {
//                super.callbackSuccess(url, element);
//                AboutBean aboutBean= JSON.parseObject(element.getData(),AboutBean.class);
//                if ((AppUtil.getVersionName(mContext)+"").equals(aboutBean.getApk_edition())){//最新版本
////                    MyApplication.showToast("已是最新版本");
//                }else{//不是最新版本 弹框下载
//                    showUpdataDialog(aboutBean.getInfo(),aboutBean.getApk_url(),aboutBean.is_compel());
//                }
//            }
//        });
//    }

}
