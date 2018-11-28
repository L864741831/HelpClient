package com.tianjistar.help.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.StringUtil;
import com.tianjistar.help.widget.ClearEditText;

import butterknife.Bind;

public class ForgetPwdActivity extends Base1Activity implements View.OnClickListener {
    @Bind(R.id.tv_forget_get_yzm)
    TextView textViewGetYzm;//获取验证码
    @Bind(R.id.et_forget_yzm)
    ClearEditText clearEditTextYzm;
    @Bind(R.id.et_forget_phone)
    ClearEditText clearEditTextPhone;
    @Bind(R.id.btn_forget_sure)
    Button buttonSure;

    @Override
    public int getContentView() {
        return R.layout.activity_forget_pwd;
    }

    public void initView() {
        setTitle("忘记密码");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        buttonSure.setOnClickListener(this);
        textViewGetYzm.setOnClickListener(this);
        clearEditTextYzm.addTextChangedListener(new TextWatcher() {
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
        switch (view.getId()) {
            case R.id.tv_forget_get_yzm:
                getYzm();
                break;
            case R.id.btn_forget_sure:
                String phone = clearEditTextPhone.getText().toString().trim();
                String yzm = clearEditTextYzm.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    MyApplication.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(yzm)){
                    MyApplication.showToast("请输入验证码");
                    return;
                }

                checkCaptcha(phone,yzm);
                break;
        }
    }

    /**
     * 获取验证码
     */

    public void getYzm() {
        final String phone = clearEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            MyApplication.showToast("手机号不能为空");
            clearEditTextPhone.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNumber(phone)){
            MyApplication.showToast("手机号格式不正确");
            clearEditTextPhone.requestFocus();
            return;
        }

        MyParams params = new MyParams();
        params.put("phone",phone);
        params.put("app","yes");
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        VictorHttpUtil.doPost(false,this, Define.GET_CAPTCHA,params,true,"获取中...",new BaseHttpCallbackListener<Element>(){
            @Override
            public void callbackSuccess(String url, Element element) {

                new CaptchaTimer(60000L, 1000L).start();

                MyApplication.showToastLong("验证码已发送到" + phone + "的手机中");
                // 验证码框获得焦点
                clearEditTextYzm.requestFocus();
            }
        });
    }

    private void checkCaptcha(final String phone,final String yzm) {
        MyParams params = new MyParams();
        params.put("validateCode",yzm);
        params.put("app","yes");
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        VictorHttpUtil.doPost(false,this, Define.GET_CAPTCHA,params,true,"加载中...",new BaseHttpCallbackListener<Element>(){
            @Override
            public void callbackSuccess(String url, Element element) {
                MyApplication.showToast("验证码验证成功");
                Bundle bundle = new Bundle();
                bundle.putString("mobile", phone);
                bundle.putString("code", yzm);
                bundle.putString("forget", "forget");
                MyApplication.openActivity(mActivity, RegisterPwdActivity.class, bundle);
            }
        });
    }

    class CaptchaTimer extends CountDownTimer {

        public CaptchaTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            textViewGetYzm.setTextColor(Color.parseColor("#000000"));
            textViewGetYzm.setText("重发验证码");
            textViewGetYzm.setEnabled(true);
        }

        public void onTick(long millisUntilFinished) {
            String texts = millisUntilFinished / 1000L + "秒后重发";//60s后再次获取
            textViewGetYzm.setText(texts);
            textViewGetYzm.setTextColor(Color.parseColor("#000000"));
            SpannableStringBuilder builder1 = new SpannableStringBuilder(textViewGetYzm.getText().toString());
            textViewGetYzm.setText(builder1);
            textViewGetYzm.setEnabled(false);
        }

    }


}
