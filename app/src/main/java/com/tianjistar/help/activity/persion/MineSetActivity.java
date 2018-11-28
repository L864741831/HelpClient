package com.tianjistar.help.activity.persion;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.activity.login.LoginActivity;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.DataCleanManager;
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.utils.PreferencesUtils;

import butterknife.Bind;

/**
 * 设置
 * */
public class MineSetActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.rl_updata_pwd)
    RelativeLayout relativeLayoutPwd;
    @Bind(R.id.rl_clear)
    RelativeLayout relativeLayoutClear;
    @Bind(R.id.rl_about)
    RelativeLayout relativeLayoutAbout;
    @Bind(R.id.rl_exzt)
    RelativeLayout relativeLayoutExzt;
    @Bind(R.id.tv_set_clear)
    TextView textViewClear;

    private PopupWindow popupWindow;
    private String uuid;

    @Override
    public int getContentView() {
        return R.layout.activity_mine_set;
    }

    @Override
    public void initView() {
        setTitle("设置");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        relativeLayoutPwd.setOnClickListener(this);
        relativeLayoutClear.setOnClickListener(this);
        relativeLayoutAbout.setOnClickListener(this);
        relativeLayoutExzt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_updata_pwd:
                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                    MyApplication.openActivity(mActivity, UpdataPwdActivity.class);
                }else {
                    MyApplication.showToast("请先登录");
                    MyApplication.openActivity(mActivity, LoginActivity.class);
                }
//                Bundle bundle=new Bundle();
//                bundle.putString("type","1");
//                MyApplication.openActivity(mContext,.class,bundle);
                break;
            case R.id.rl_clear:
                clearCash();
                break;
            case R.id.rl_about:

                break;
            case R.id.rl_exzt:
                exit();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
            relativeLayoutExzt.setVisibility(View.VISIBLE);
        }else {
            relativeLayoutExzt.setVisibility(View.GONE);
        }
        uuid =  PreferencesUtils.getString(mContext,Constants.USER_UUID);
    }

    //清楚缓存
    private void clearCash() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_clearcash, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        DisplayUtil.setAlpha(MineSetActivity.this, 0.5f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtil.setAlpha(MineSetActivity.this, 1f);
            }
        });
        TextView tvCancel = (TextView) view.findViewById(R.id.pop_tv_cancle);
        TextView sure = (TextView) view.findViewById(R.id.pop_tv_sure);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
                    MyApplication.showToast("清理成功！");
                    DataCleanManager.clearAllCache(mContext);
                    textViewClear.setText("0.00M");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                popupWindow.dismiss();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    //退出登录
    private void exit() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.pop_tv_cancle);
        TextView sure = (TextView) view.findViewById(R.id.pop_tv_sure);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        DisplayUtil.setAlpha(MineSetActivity.this, 0.5f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtil.setAlpha(MineSetActivity.this, 1f);
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/3/21 退出登录的逻辑
                withdrawLogin();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void withdrawLogin() {
        MyParams params = new MyParams();
        params.put("app","yes");
        params.put("uuid",uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        VictorHttpUtil.doPost(false, mContext, Define.WITHDRAW, params, true, "退出中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);

                popupWindow.dismiss();
                // 退出成功
                MyApplication.showToast("退出成功");

                preferencesHelper.putBoolean(Constants.SP_FIRST_LAUCH,false);
                preferencesHelper.putBoolean(Constants.LOGIN_SUCCESS,false);

                PreferencesUtils.removeAllData(mContext);

                relativeLayoutExzt.setVisibility(View.GONE);
            }

            @Override
            public void callbackError(String url, Element obj) {
                super.callbackError(url, obj);

                Log.i("info","---callbackError-11--");
                popupWindow.dismiss();
            }
        });
    }


}
