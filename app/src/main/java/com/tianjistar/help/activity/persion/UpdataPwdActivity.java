package com.tianjistar.help.activity.persion;

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
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.PreferencesUtils;

import butterknife.Bind;

/**
 * 修改登录密码
 * */
public class UpdataPwdActivity extends Base1Activity {

    @Bind(R.id.bt_update_password)
    Button bt_update_password;
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

    @Override
    public int getContentView() {
        return R.layout.activity_updata_pwd;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        setTitle("修改密码");

//        if (getIntent().getStringExtra("type").equals("1")){//修改登录密码
//            setTitle("修改密码");
////            url= Define.URL_updata_login_pwd;
//        }else if (getIntent().getStringExtra("type").equals("2")){//修改支付密码
////            int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL|InputType.TYPE_NUMBER_VARIATION_PASSWORD;
////            et_tel_num.setInputType(inputType);
////            et_password.setInputType(inputType);
////            et_newpassword.setInputType(inputType);
//            setTitle("修改支付密码");
//        }
//
//        //完成修改密码
//        bt_updatepassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                upData();
//            }
//        });
//        //支付密码6位数字监听
////        setListener();
    }

    @Override
    public void initListener() {
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
                    bt_update_password.setAlpha(1);
                }else{
                    bt_update_password.setAlpha((float) 0.5);
                }
            }
        });


        bt_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeModifyPassword();
            }
        });

    }

    private void completeModifyPassword() {
        if (TextUtils.isEmpty(et_tel_num.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString())
                || TextUtils.isEmpty(et_newpassword.getText().toString())){
            MyApplication.showToast("修改信息不能为空");
            return;
        }
        if (!et_password.getText().toString().equals(et_newpassword.getText().toString())){
            MyApplication.showToast("两次输入密码不一致");
            return;
        }

        MyParams params = new MyParams();
        params.put("app","yes");
        params.put("uuid", PreferencesUtils.getString(mContext,Constants.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        params.put("mobile",PreferencesUtils.getString(mContext,Constants.USER_PHONE));
        params.put("password",et_tel_num.getText().toString());
        params.put("newPassword",et_password.getText().toString());

        VictorHttpUtil.doPost(false,mContext,Define.UPDATE_PASSWORD,params,true,null,new BaseHttpCallbackListener<Element>(){
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);
                MyApplication.showToast("修改成功,请重新登录");

                preferencesHelper.putBoolean(Constants.SP_FIRST_LAUCH,false);
                preferencesHelper.putBoolean(Constants.LOGIN_SUCCESS,false);

                PreferencesUtils.removeAllData(mContext);
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
