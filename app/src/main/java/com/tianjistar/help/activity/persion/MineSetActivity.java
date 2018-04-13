package com.tianjistar.help.activity.persion;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.DataCleanManager;
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.utils.ToastHelper;

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

    @Override
    public int getContentView() {
        return R.layout.activity_mine_set;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("设置");
        setListener();
    }

    private void setListener() {
        relativeLayoutPwd.setOnClickListener(this);
        relativeLayoutClear.setOnClickListener(this);
        relativeLayoutAbout.setOnClickListener(this);
        relativeLayoutExzt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_updata_pwd:
                Bundle bundle=new Bundle();
                bundle.putString("type","1");
                MyApplication.openActivity(mContext,UpdataPwdActivity.class,bundle);
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
                try {
                    ToastHelper.showAlert(MineSetActivity.this, "清理成功！");
                    DataCleanManager.clearAllCache(mContext);
                    textViewClear.setText("0.00M");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                // TODO: 2018/3/21 退出登录的逻辑
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
}
