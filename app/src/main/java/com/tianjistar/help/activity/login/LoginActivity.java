package com.tianjistar.help.activity.login;

import android.Manifest;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tianjistar.help.R;
import com.tianjistar.help.activity.MainActivity;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.PhotoEvent;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.widget.ClearEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;

public class LoginActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.et_username)
    ClearEditText EditTextName;
    @Bind(R.id.et_pwd)
    ClearEditText EditTextPwd;
    @Bind(R.id.iv_hide)
    ImageView ImageViewHind;
    @Bind(R.id.btn_login)
    Button buttonSure;
    @Bind(R.id.tv_regest)
    TextView TextViewRegest;
    @Bind(R.id.tv_foget)
    TextView TextViewForget;
    private boolean isHidden = false;

    @Override
    public int getContentView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_login;
    }

    public void initView() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        ImageViewHind.setOnClickListener(this);
        buttonSure.setOnClickListener(this);
        TextViewForget.setOnClickListener(this);
        TextViewRegest.setOnClickListener(this);
        EditTextPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0&&!editable.toString().contains(" ")){
                    buttonSure.setAlpha(1);
                }else{
                    buttonSure.setAlpha((float) 0.5);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_hide://隐藏与可见
                if (isHidden) {
                    //设置EditText文本为可见的
                    EditTextPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ImageViewHind.setImageResource(R.drawable.ic_see);
                } else {
                    //设置EditText文本为隐藏的
                    ImageViewHind.setImageResource(R.drawable.ic_hint);
                    EditTextPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                EditTextPwd.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = EditTextPwd.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_login://登录
                String phone = EditTextName.getText().toString().trim();
                String pwd = EditTextPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    MyApplication.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    MyApplication.showToast("请输入密码");
                    return;
                }
                loginAuthority(phone,pwd);
                break;
            case R.id.tv_foget://忘记密码
                MyApplication.openActivity(mContext,ForgetPwdActivity.class);
                break;
            case R.id.tv_regest://新用户注册
                MyApplication.openActivity(mContext,RegisterPhoneActivity.class);
                break;
        }
    }

    @Subscribe(priority = 1,threadMode = ThreadMode.MAIN)
    public void onReceiveLogin(PhotoEvent event){
        Log.i("info","---onReceiveLogin---"+event.getName());
    }

    private void loginAuthority(final String phone,final String pwd){
        new TedPermission(MyApplication.CONTEXT)
                .setPermissions(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .setDeniedMessage("请在设置中允许本应用使用手机状态权限")
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        login(phone,pwd);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                        if (deniedPermissions.size()>0){
//                            for (int i = 0; i < deniedPermissions.size(); i++) {
//                                Log.i("info","---onPermissionDenied-2--"+deniedPermissions.get(i));
//                            }
//                        }
                    }
                }).check();
    }


//    username	string	是	账号
//    password	string	是	密码
//    mobileLogin	string	是	true
//    app	string	是	true
//    imei	string	是	手机唯一识别号
//    registrationID	string	是	极光推送id

    private void login(String phone,String pwd) {
        // 发送登录请求
        MyParams params = new MyParams();
        params.put("username", phone);
        params.put("password",pwd);
        params.put("mobileLogin","true");
        params.put("app","true");
        params.put("imei", AppUtil.getPhoneImei(mActivity));
//        params.put("registrationID", "");

        VictorHttpUtil.doPost(false, mContext, Define.LOGIN, params, true, "登录中...",
                new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);
                // 登录成功
                MyApplication.showToast("登录成功");
                // 保存用户信息
                preferencesHelper.putBoolean(Constants.SP_FIRST_LAUCH,true);
                preferencesHelper.putBoolean(Constants.LOGIN_SUCCESS,true);

                PreferencesUtils.saveUserInfo(mContext,element.rows);
                // 关闭本页
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
