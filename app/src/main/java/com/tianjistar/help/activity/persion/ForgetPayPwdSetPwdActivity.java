package com.tianjistar.help.activity.persion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**忘记支付密码之设置支付密码*/
public class ForgetPayPwdSetPwdActivity extends Base1Activity {
    @Bind(R.id.et_set_pwd)
    EditText editTextPwd;
    @Bind(R.id.bt_set_next)
    Button buttonNect;

    @Override
    public int getContentView() {
        return R.layout.activity_forget_pay_pwd_set_pwd;
    }

    @Override
    public void initView() {
        setTitle("设置支付密码");
        buttonNect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd=editTextPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)){
                    MyApplication.showToast("请输入天佑支付密码");
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("pwd",pwd);
                MyApplication.openActivity(mContext,ForgetPayPwdSurePwdActivity.class,bundle);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    //接收设置页面发送的广播关闭当前页面
    @Subscribe(priority = 1,threadMode = ThreadMode.MAIN)
    public void onReceiveAccount(String type){
        if (type.equals("close")){
           finish();
        }
    }
}
