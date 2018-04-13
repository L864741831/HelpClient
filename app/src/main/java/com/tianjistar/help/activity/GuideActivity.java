package com.tianjistar.help.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.tianjistar.help.R;
import com.tianjistar.help.activity.login.LoginActivity;
import com.tianjistar.help.activity.login.RegisterYzmActivity;
import com.tianjistar.help.app.AppSpContact;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.SharedPreferencesHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 导航页
 * **/
public class GuideActivity extends Base1Activity {



    @Override
    public int getContentView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        super.initView();
        //检查权限
        int permissionCheck = ContextCompat.checkSelfPermission(GuideActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        //如果应用具有此权限，方法将返回 PackageManager.PERMISSION_GRANTED，
        // 并且应用可以继续操作。如果应用不具有此权限，
        // 方法将返回 PERMISSION_DENIED，且应用必须明确向用户要求权限。
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions
                    (GuideActivity.this, new String[]{Manifest.permission.
                                    ACCESS_FINE_LOCATION},
                            0);
        }
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setViewData();
    }
    /**
     * 三秒启动
     * */
    protected void setViewData() {
        final Intent intent;
        //判断是否是登陆过
        if (SharedPreferencesHelper.getInstance().getBoolean(AppSpContact.SP_FIRST_LAUCH)==true){
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else{
            intent=new Intent(this, MainActivity.class);
            intent.putExtra(AppSpContact.JUMP_LOGIN,"register");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,3*1000);//此处的Delay可以是3*1000，代表三秒
    }
}
