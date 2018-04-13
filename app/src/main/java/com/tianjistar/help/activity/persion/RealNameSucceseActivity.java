package com.tianjistar.help.activity.persion;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 各种成功界面
 */
public class RealNameSucceseActivity extends Base1Activity {
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.lin_band_bank)
    LinearLayout linBandBank;
    @Bind(R.id.tv_contacts)
    TextView tvContacts;
    @Bind(R.id.tv_bankcard)
    TextView tvBankcard;
    private String type;
    int isHaveCard;

    @Override
    public int getContentView() {
        return R.layout.activity_succese;
    }

    @Override
    protected void initView() {
        super.initView();

        /**
         * 去绑定银行卡
         */
        tvBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                bundle.putString("type", "2");
//                MyApplication.openActivity(mContext, AddCardInputPwdActivity.class, bundle);
                finish();
            }
        });
    }



}
