package com.tianjistar.help.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.StringUtil;
import com.tianjistar.help.widget.ClearEditText;

import butterknife.Bind;

/**
 * 注册之输入手机号
 * */
public class RegisterPhoneActivity extends Base1Activity implements View.OnClickListener {
    @Bind(R.id.et_phone)
    ClearEditText ClearEditTextPhone;
    @Bind(R.id.btn_next)
    Button buttonNext;//下一步
    @Bind(R.id.tv_xieyi)
    TextView textViewXieYi;//协议
    @Override
    public int getContentView() {
        return R.layout.activity_register_phone;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("注册");
        setListener();
    }

    private void setListener() {
        textViewXieYi.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        ClearEditTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0&&!editable.toString().contains(" ")){
                    buttonNext.setAlpha(1);
                }else{
                    buttonNext.setAlpha((float) 0.5);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_xieyi:

                break;
            case R.id.btn_next:
                final String phone = ClearEditTextPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    MyApplication.showToast("手机号不能为空");
                    ClearEditTextPhone.requestFocus();
                    return;
                }
                if (!StringUtil.isPhoneNumber(phone)){
                    MyApplication.showToast("手机号格式不正确");
                    ClearEditTextPhone.requestFocus();
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("phone",phone);
                MyApplication.openActivity(mContext,RegisterYzmActivity.class,bundle);
                finish();
                break;
        }
    }
}
