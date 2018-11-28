package com.tianjistar.help.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
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
import com.tianjistar.help.view.VerifyCodeView;

import butterknife.Bind;

/**
 * 注册之输入验证码
 * */
public class RegisterYzmActivity extends Base1Activity {

    @Bind(R.id.tv_regest_phone)
    TextView textViewPhone;
    @Bind(R.id.tv_get_yzm)
    TextView textViewGetYzm;
    @Bind(R.id.vc_code)
    VerifyCodeView code;
    String phone="";

    @Override
    public int getContentView() {
        return R.layout.activity_register_yzm;
    }

    @Override
    public void initView() {
        setTitle("注册");
    }

    @Override
    public void initData() {
        phone = getIntent().getStringExtra("phone");

        if (!TextUtils.isEmpty(phone) && phone.length()>=11){
            textViewPhone.setText("+86"+"  "+phone.substring(0,3)+"  "+phone.substring(3,7)+"  "+phone.substring(7,11));
        }
        /**获取验证码*/
        getYzm();
    }

    @Override
    public void initListener() {
        textViewGetYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewGetYzm.getText().toString().equals("重发验证码")){//重新发送验证码
                    getYzm();
                }
            }
        });

        code.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                // 发送验证验证码功能
                MyParams params = new MyParams();
                params.put("validateCode", code.getEditContent());//验证码
//                params.put("app","yes");
//                params.put("imei", AppUtil.getPhoneImei(mActivity));
                VictorHttpUtil.doPost(false, mContext, Define.CHECK_CAPTCHA, params, true, "验证码校验中...", new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", phone);
                        bundle.putString("code", code.getEditContent());
                        MyApplication.openActivity(mActivity, RegisterPwdActivity.class, bundle);
                        finish();
                    }
                });
            }

            @Override
            public void invalidContent() {
            }

        });

    }

    private void getYzm() {
        // 获取短信验证码
        MyParams params = new MyParams();
        params.put("phone", phone);//用户名或手机号
//        params.put("app","yes");
//        params.put("imei", AppUtil.getPhoneImei(mActivity));
        VictorHttpUtil.doPost(false, mContext, Define.GET_CAPTCHA, params, true, "获取中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        // 成功
                        new CaptchaTimer1(60000L, 1000L).start();
                        MyApplication.showToastLong("验证码已发送到" + phone + "的手机中");
                    }
                });
    }

    class CaptchaTimer1 extends CountDownTimer {

        public CaptchaTimer1(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            textViewGetYzm.setTextColor(Color.parseColor("#000000"));
            textViewGetYzm.setText("重发验证码");
            textViewGetYzm.setBackgroundResource(R.drawable.tv_bg_yzm1);
            textViewGetYzm.setEnabled(true);
        }

        public void onTick(long millisUntilFinished) {
            //strategy1是一个TextView
            String texts = "00:"+millisUntilFinished / 1000L + "秒后重发验证码";//60s后再次获取
            textViewGetYzm.setText(texts);
            textViewGetYzm.setTextColor(Color.parseColor("#000000"));
//            textViewGetYzm.setBackgroundResource(R.drawable.tv_bg_yzm1);
            SpannableStringBuilder builder1 = new SpannableStringBuilder(textViewGetYzm.getText().toString());
            //设置前景色为蓝色
//        ForegroundColorSpan blue=new ForegroundColorSpan(Color.GRAY);
//        //改变第3-最后个字体颜色为蓝色
//        builder1.setSpan(blue,3,builder1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textViewGetYzm.setText(builder1);
            textViewGetYzm.setEnabled(false);
        }

    }

}
