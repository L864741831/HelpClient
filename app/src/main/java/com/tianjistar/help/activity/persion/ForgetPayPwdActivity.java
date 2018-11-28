package com.tianjistar.help.activity.persion;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;

import butterknife.Bind;

/**
 * 忘记支付密码
 */
public class ForgetPayPwdActivity extends Base1Activity {

    @Bind(R.id.et_forgetpaypwd_phone)
    EditText editTextPhone;
    @Bind(R.id.et_forgetpaypwd_name)
    EditText editTextName;
    @Bind(R.id.et_forgetpaypwd_num)
    EditText editTextNo;
    @Bind(R.id.btn_next)
    Button buttonNext;

    @Override
    public int getContentView() {
        return R.layout.activity_forget_pay_pwd;
    }

    @Override
    public void initView() {
        setTitle("忘记支付密码");
        setListener();
        checkInfo();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
    }

    private void checkInfo() {
        String name = editTextName.getText().toString().trim();
        String no = editTextNo.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            MyApplication.showToast("请输入完整的姓名");
            return;
        }
        if (TextUtils.isEmpty(no)){
            MyApplication.showToast("请输入证件号");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            MyApplication.showToast("请输入银行预留手机号");
            return;
        }

    }

    private void setListener() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editTextPhone.addTextChangedListener(new TextWatcher() {
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

}
