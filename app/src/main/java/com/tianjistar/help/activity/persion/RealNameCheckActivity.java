package com.tianjistar.help.activity.persion;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.tianjistar.help.R;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.AppSpContact;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.SharedPreferencesHelper;

import butterknife.Bind;


/**
 * 实名认证**/
public class RealNameCheckActivity extends Base1Activity {

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.et_realname_num)
    EditText editTextNum;
    @Bind(R.id.et_realname)
    EditText editTextName;
    @Bind(R.id.btn_addcard_phone_next)
    Button buttonSure;

    @Override
    public int getContentView() {
        return R.layout.activity_real_name_check;
    }

    @Override
    protected void initView() {
        super.initView();
        textViewTitle.setText("实名认证");
        getData();
        editTextNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    editTextNum.setText(str1);
                    editTextNum.setSelection(start);

                }
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
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    editTextName.setText(str1);
                    editTextName.setSelection(start);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextName.getText().toString().trim())){
                    MyApplication.showToast("请输入您的真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(editTextNum.getText().toString().trim())){
                    MyApplication.showToast("请输入您的身份证号");
                    return;
                }
                MyParams params = new MyParams();
                params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
                params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
                params.put("realname", editTextName.getText().toString().trim());
                params.put("idcard", editTextNum.getText().toString().trim());
                VictorHttpUtil.doPost(false, mContext, Define.URL_real_name, params, true, null,
                        new BaseHttpCallbackListener<Element>() {
                            @Override
                            public void callbackSuccess(String url, Element element) {
                                super.callbackSuccess(url, element);
                                MyApplication.showToast(element.getMsg());
//                                bundle.putString("type","namecheck");//实名认证成功，去绑定银行卡
                                MyApplication.openActivity(mContext, RealNameSucceseActivity.class);
                                finish();
                            }
                        });
            }
        });

    }
    //获取姓名
    private void getData() {
//        MyParams params = new MyParams();
//        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
//        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
//        VictorHttpUtil.doPost(false, mContext, Define.URL_myinfo, params, true, null,
//                new BaseHttpCallbackListener<Element>() {
//                    @Override
//                    public void callbackSuccess(String url, Element element) {
//                        super.callbackSuccess(url, element);
//                        try {
//                            JSONObject json=new JSONObject(element.getData());
//                            String name=json.optString("username");
//                            editTextName.setText(FlockUtil.jiemi(name));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
    }
}
