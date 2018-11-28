package com.tianjistar.help.activity.persion;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.tianjistar.help.R;
import com.tianjistar.help.activity.common.RechargeActivity;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.WalletBalanceBean;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.StringUtils;

import butterknife.Bind;
/**
 * 我的钱包
 */
public class MineWalletctivity extends Base1Activity {

    @Bind(R.id.topbar_right)
    TextView textViewDetailo;
    @Bind(R.id.tv_look)
    TextView textViewLook;
    @Bind(R.id.tv_pay_money)
    TextView textViewMoney;

    @Bind(R.id.tv_balance)
    TextView tv_balance;

    @Override
    public int getContentView() {
        return R.layout.activity_mine_walletctivity;
    }

    @Override
    public void initView() {
        setTitle("我的钱包");
        textViewDetailo.setText("明细");
        textViewLook.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textViewLook.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void initData() {
        getBalanceData();
    }

    @Override
    public void initListener() {
        //明细
        textViewDetailo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.openActivity(mContext,MineWalletDetailActivity.class);
            }
        });
        //充值
        textViewMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                MyApplication.openActivity(mContext, RechargeActivity.class);
            }
        });
    }

    private void getBalanceData() {
        String uuid =  PreferencesUtils.getString(mContext, Constants.USER_UUID);

        MyParams params = new MyParams();
        params.put("uuid",uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));

        params.put("app", "yes");
        VictorHttpUtil.doPost(false, mContext, Define.CHECK_BALANCE, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        WalletBalanceBean balanceBean = JSON.parseObject(element.rows,WalletBalanceBean.class);
                        if (StringUtils.isNotBlank(balanceBean.getMoney())){
                            tv_balance.setText(balanceBean.getMoney());
                        }

                    }
                });
    }

}
