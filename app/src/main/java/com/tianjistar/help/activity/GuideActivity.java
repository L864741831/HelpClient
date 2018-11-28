package com.tianjistar.help.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tianjistar.help.R;
import com.tianjistar.help.aaa.VueActivity;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 导航页
 **/
public class GuideActivity extends Base1Activity {

    @Override
    public int getContentView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        //检查权限
//        int permissionCheck = ContextCompat.checkSelfPermission(GuideActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        //如果应用具有此权限，方法将返回 PackageManager.PERMISSION_GRANTED，
//        // 并且应用可以继续操作。如果应用不具有此权限，
//        // 方法将返回 PERMISSION_DENIED，且应用必须明确向用户要求权限。
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions
//                    (GuideActivity.this, new String[]{Manifest.permission.
//                                    ACCESS_FINE_LOCATION},
//                            0);
//        }
    }

    @Override
    public void initData() {
        setViewData();

    }

    @Override
    public void initListener() {
    }

    /**
     * 三秒启动
     */
    protected void setViewData() {
        new TedPermission(MyApplication.CONTEXT)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                        )
                .setDeniedMessage("请在设置中允许本应用使用手机状态权限")
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        entryLogin();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    }
                }).check();
    }



    private void entryLogin() {
        final Intent intent;
        //判断是否是登陆过
        if (SharedPreferencesHelper.getInstance().getBoolean(Constants.SP_FIRST_LAUCH) == true) {
            intent = new Intent(this, MainActivity.class);
/*            intent = new Intent(this, VueActivity.class);*/
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent = new Intent(this, MainActivity.class);
/*            intent = new Intent(this, VueActivity.class);*/
            intent.putExtra(Constants.JUMP_LOGIN, "register");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 3 * 1000);//此处的Delay可以是3*1000，代表三秒
    }

}
