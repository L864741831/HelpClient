package com.tianjistar.help.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.widget.ClearEditText;

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
    private boolean isHidden=false;

    @Override
    public int getContentView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setListener();
    }

    private void setListener() {
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
               String phone=EditTextName.getText().toString().trim();
               String pwd=EditTextPwd.getText().toString().trim();
               if (TextUtils.isEmpty(phone)){
                   MyApplication.showToast("请输入手机号");
                   return;
               }
               if (TextUtils.isEmpty(pwd)){
                   MyApplication.showToast("请输入密码");
                   return;
               }


               break;
           case R.id.tv_foget://忘记密码
               MyApplication.openActivity(mContext,ForgetPwdActivity.class);

               break;
           case R.id.tv_regest://新用户注册
               MyApplication.openActivity(mContext,RegisterPhoneActivity.class);

               break;
       }
    }
}
