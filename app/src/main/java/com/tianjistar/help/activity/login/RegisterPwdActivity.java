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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.widget.ClearEditText;

import butterknife.Bind;

/**
 * 注册之输入密码
 * */
public class RegisterPwdActivity extends Base1Activity implements View.OnClickListener {
    @Bind(R.id.tv_regest_phone)
    TextView textViewPhone;
    @Bind(R.id.btn_regest)
    Button buttonRegest;
    @Bind(R.id.et_sure_pwd)
    ClearEditText clearEditTextSurePwd;//确认新密码
    @Bind(R.id.et_new_pwd)
    ClearEditText clearEditTextNewPwd;//新密码
    @Bind(R.id.iv_hide)
    ImageView imageViewHide;
    private boolean isHidden=false;
    String phone="";
    String code="";


    @Override
    public int getContentView() {
        return R.layout.activity_register_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("注册");
        phone=getIntent().getStringExtra("moble");
        code=getIntent().getStringExtra("code");
        if (!TextUtils.isEmpty(phone)){
            String substring = phone.substring(3, 8);
            String replace = phone.replace(substring, "****");
            textViewPhone.setText(replace);
        }
        setListener();
    }

    private void setListener() {
        imageViewHide.setOnClickListener(this);
        buttonRegest.setOnClickListener(this);
        clearEditTextSurePwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0&&!editable.toString().contains(" ")){
                    buttonRegest.setAlpha(1);
                }else{
                    buttonRegest.setAlpha((float) 0.5);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regest:
                String newpwd=clearEditTextNewPwd.getText().toString().trim();
                String surepwd=clearEditTextSurePwd.getText().toString().trim();
                if (TextUtils.isEmpty(newpwd)){
                    MyApplication.showToast("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(surepwd)){
                    MyApplication.showToast("请确认新密码");
                    return;
                }
                if (!newpwd.equals(surepwd)){
                    MyApplication.showToast("两次输入密码不一样");
                    return;
                }

                break;
            case R.id.iv_hide:
                if (isHidden) {
                    //设置EditText文本为可见的
                    clearEditTextSurePwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    clearEditTextNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewHide.setImageResource(R.drawable.ic_see);
                } else {
                    //设置EditText文本为隐藏的
                    imageViewHide.setImageResource(R.drawable.ic_hint);
                    clearEditTextNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    clearEditTextSurePwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                clearEditTextNewPwd.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = clearEditTextNewPwd.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
        }
    }
}
