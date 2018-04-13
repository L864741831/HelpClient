package com.tianjistar.help.activity.persion;

import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.activity.login.ForgetPwdActivity;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.widget.ClearEditText;

import butterknife.Bind;
/**
 * 我的钱包
 * **/
public class MineWalletctivity extends Base1Activity {
    @Bind(R.id.topbar_right)
    TextView textViewDetailo;
    @Bind(R.id.tv_look)
    TextView textViewLook;
    @Bind(R.id.tv_pay_money)
    TextView textViewMoney;


    @Override
    public int getContentView() {
        return R.layout.activity_mine_walletctivity;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的钱包");
        textViewDetailo.setText("明细");
        textViewLook.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textViewLook.getPaint().setAntiAlias(true);//抗锯齿
        textViewDetailo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.openActivity(mContext,MineWalletDetailActivity.class);
            }
        });
        //提现
        textViewMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

            }
        });

    }
}
