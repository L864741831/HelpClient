package com.tianjistar.help.activity.persion;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.tianjistar.help.R;
import com.tianjistar.help.activity.login.ForgetPwdActivity;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.DisplayUtil;


import butterknife.Bind;

/**
 * 救援卡
 * **/
public class HelpCardActivity extends Base1Activity  {

    @Bind(R.id.tv_buy)
    TextView textViewBuy;
    @Bind(R.id.ll_foot)
    LinearLayout linearLayout;

    @Override
    public int getContentView() {
        return R.layout.activity_help_card;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("救援卡");
        textViewBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                View view = LayoutInflater.from(HelpCardActivity.this).inflate(R.layout.popwin_pwd, null);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                DisplayUtil.setAlpha(HelpCardActivity.this, 0.5f);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        DisplayUtil.setAlpha(HelpCardActivity.this, 1f);
                    }
                });
                TextView textView=view.findViewById(R.id.tv_foget_pwd);
                ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_close);
                Button sure = (Button) view.findViewById(R.id.btn_pwd_sure);
                final EditText clearEditText=view.findViewById(R.id.ce_pwd);
                clearEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                clearEditText.setFocusable(true);
                InputMethodManager imm = (InputMethodManager)HelpCardActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);    //InputMethodManager.SHOW_FORCED
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2018/3/21 验证支付密码通知后台 正确即购买成功
                        if (TextUtils.isEmpty(clearEditText.getText().toString())){
                            MyApplication.showToast("请输入您的支付密码");
                            return;
                        }

                        popupWindow.dismiss();
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyApplication.openActivity(mContext, ForgetPwdActivity.class);
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }



}
