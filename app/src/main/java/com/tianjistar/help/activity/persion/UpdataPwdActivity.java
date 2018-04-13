package com.tianjistar.help.activity.persion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.AppSpContact;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.app.SpContact;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.SharedPreferencesHelper;

import butterknife.Bind;

/**
 * 修改密码
 * */
public class UpdataPwdActivity extends Base1Activity {
    @Bind(R.id.bt_updatepassword)
    Button bt_updatepassword;
    @Bind(R.id.et_tel_num)
    EditText et_tel_num;//原密码
    @Bind(R.id.et_password)
    EditText et_password;//新密码
    @Bind(R.id.et_newpassword)
    EditText et_newpassword;//确定新密码
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    String url;


    @Override
    public int getContentView() {
        return R.layout.activity_updata_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getIntent().getStringExtra("type").equals("1")){//修改登录密码
            setTitle("修改密码");
            url= Define.URL_updata_login_pwd;
        }else if (getIntent().getStringExtra("type").equals("2")){//修改支付密码
//            int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL|InputType.TYPE_NUMBER_VARIATION_PASSWORD;
//            et_tel_num.setInputType(inputType);
//            et_password.setInputType(inputType);
//            et_newpassword.setInputType(inputType);
            setTitle("修改支付密码");
            url= Define.URL_updata_pay_pwd;
        }
        bt_updatepassword.setOnClickListener(new View.OnClickListener() {//完成修改密码
            @Override
            public void onClick(View view) {

                upData(url);

            }
        });
        //支付密码6位数字监听
        setListener();
    }
    private void setListener() {
//        if (getIntent().getStringExtra("type").equals("2")){
//            et_tel_num.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.length() > 6) {
//                        MyApplication.showToast( "支付密码由6位数字组成");
//                        editable.delete(6, editable.length());
//                    }
//                }
//            });
//            et_password.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.length() > 6) {
//                        MyApplication.showToast( "支付密码由6位数字组成");
//                        editable.delete(6, editable.length());
//                    }
//                }
//            });
            et_newpassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length()>0&&!editable.toString().contains(" ")){
                        bt_updatepassword.setAlpha(1);
                    }else{
                        bt_updatepassword.setAlpha((float) 0.5);
                    }
                }
            });

        }



    private void upData(String url) {
        if (TextUtils.isEmpty(et_tel_num.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString()) || TextUtils.isEmpty(et_newpassword.getText().toString())){
            MyApplication.showToast("修改信息不能为空");
            return;
        }
        if (!et_password.getText().toString().equals(et_newpassword.getText().toString())){
            MyApplication.showToast("两次输入密码不一致");
            return;
        }
        MyParams params=new MyParams();
        if (getIntent().getStringExtra("type").equals("2")){
            params.put("uniqid", SharedPreferencesHelper.getInstance().getString(SpContact.UNIQID));
        }
//        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
//        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
//        params.put("formerpwd", FlockUtil.jiami(et_tel_num.getText().toString().trim()));
//        params.put("newpwd",FlockUtil.jiami(et_password.getText().toString().trim()));
//        params.put("newpwds",FlockUtil.jiami(et_newpassword.getText().toString().trim()));
//        VictorHttpUtil.doPost(false,mContext,url,params,true,null,new BaseHttpCallbackListener<Element>(){
//            @Override
//            public void callbackSuccess(String url, Element element) {
//                super.callbackSuccess(url, element);
//                MyApplication.showToast(element.getMsg());
//                finish();
//            }
//        });

    }
}
