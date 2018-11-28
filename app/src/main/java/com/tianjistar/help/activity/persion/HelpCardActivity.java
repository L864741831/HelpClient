package com.tianjistar.help.activity.persion;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
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
import com.tianjistar.help.bean.ContactsBean;
import com.tianjistar.help.bean.FindAddressBean;
import com.tianjistar.help.bean.HelpCardInfoBean;
import com.tianjistar.help.listener.OnCancelClickListener;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.StringUtils;
import com.tianjistar.help.view.dialog.RechargeDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 救援卡
 */
public class HelpCardActivity extends Base1Activity  {

    @Bind(R.id.tv_buy)
    TextView textViewBuy;
    @Bind(R.id.ll_foot)
    LinearLayout linearLayout;

    @Bind(R.id.tv_point_one)
    TextView tv_point_one;
    @Bind(R.id.tv_point_two)
    TextView tv_point_two;
    @Bind(R.id.tv_point_three)
    TextView tv_point_three;
    @Bind(R.id.tv_point_four)
    TextView tv_point_four;
    @Bind(R.id.tv_point_five)
    TextView tv_point_five;
    @Bind(R.id.tv_point_six)
    TextView tv_point_six;

    private RechargeDialog rechargeDialog;

    private EditText ce_pwd;
    private String pa_password = ""; //支付密码

    String card_id = "";//救援卡id

    @Override
    public int getContentView() {
        return R.layout.activity_help_card;
    }

    @Override
    public void initView() {
        setTitle("救援卡");
    }

    @Override
    public void initData() {
        rechargeDialog = new RechargeDialog(mContext);
        checkHelpCard();
    }

    @Override
    public void initListener() {
        textViewBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                buyNow();

            }
        });

        rechargeDialog.setmOnCancelClickListener(new OnCancelClickListener() {
            @Override
            public void onSureClick() {
                MyApplication.openActivity(mContext,RechargeActivity.class);
            }
        });
    }

    private void buyNow(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.popwin_pwd, null);
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

        ce_pwd = view.findViewById(R.id.ce_pwd);

        TextView tv_foget_pwd = view.findViewById(R.id.tv_foget_pwd);
        ImageView ivCancel = view.findViewById(R.id.iv_close);
        Button sure = view.findViewById(R.id.btn_pwd_sure);

        final EditText clearEditText = view.findViewById(R.id.ce_pwd);
        clearEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        clearEditText.setFocusable(true);

        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//InputMethodManager.SHOW_FORCED


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 验证支付密码通知后台 正确即购买成功
                if (TextUtils.isEmpty(clearEditText.getText().toString())){
                    MyApplication.showToast("请输入您的支付密码");
                    return;
                }

                //实名认证
//                MyApplication.openActivity(mContext,RealNameCheckActivity.class);

                pa_password = ce_pwd.getText().toString().trim();

                //Log.i("goumai","购买救援卡");
                confirmPassword(pa_password);


                //rechargeDialog.show();



                popupWindow.dismiss();
//                linearLayout.setVisibility(View.GONE);
            }
        });

        tv_foget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.openActivity(mContext, ForgetPayPwdActivity.class);
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void checkHelpCard(){
        MyParams params = new MyParams();
        VictorHttpUtil.doPost(false, mContext, Define.CHECK_HELP_CARD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);



                        List<HelpCardInfoBean> infoBeanList = JSON.parseArray(element.rows, HelpCardInfoBean.class);
                        if (infoBeanList!=null && infoBeanList.size()>0){
                            HelpCardInfoBean infoBean = infoBeanList.get(0);

                            card_id = infoBean.getId();

                            List<HelpCardInfoBean.ContentBean> contentBean = infoBean.getContent();

                            tv_point_one.setText(contentBean.get(0).getTitle());
                            tv_point_two.setText(contentBean.get(0).getContent());
                            tv_point_three.setText(contentBean.get(1).getTitle());
                            tv_point_four.setText(contentBean.get(1).getContent());
                            tv_point_five.setText(contentBean.get(2).getTitle());
                            tv_point_six.setText(contentBean.get(2).getContent());
                        }

                    }

                });
    }


    /*
    确认支付密码
     */
    private void confirmPassword(String password){
        MyParams params = new MyParams();
        params.put("newpass", password);

        params.put("app","yes");
        params.put("uuid", PreferencesUtils.getString(mContext, Constants.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mActivity));

        Log.i("zhifumima","支付密码"+password+"\n"+PreferencesUtils.getString(mContext, Constants.USER_UUID)+"\n"+AppUtil.getPhoneImei(mActivity));


        VictorHttpUtil.doPost(false, mContext, Define.CHECK_PAY_IS_PASS, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        Log.i("zhifumima",element.rows);

                        JSONObject obj = null;

                        try {
                            obj = new JSONObject(element.rows);
                            String token = obj.getString("token");

                            buyCard(token,card_id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //MyApplication.showToast("收到急救，请稍后！");

                    }
                });
    }




    /*
购买救援卡
 */
    private void buyCard(String token,String card_id){
        MyParams params = new MyParams();
        params.put("token", token);
        params.put("id", card_id);

        params.put("app","yes");
        params.put("uuid", PreferencesUtils.getString(mContext, Constants.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mActivity));

        Log.i("card","token"+token+"\n"+"id"+card_id+"\n"+PreferencesUtils.getString(mContext, Constants.USER_UUID)+"\n"+AppUtil.getPhoneImei(mActivity));


        VictorHttpUtil.doPost(false, mContext, Define.BUY, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        Log.i("buy",element.toString());

                    /*    JSONObject obj = null;

                        try {
                            obj = new JSONObject(element.rows);
                            String token = obj.getString("token");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        //MyApplication.showToast("收到急救，请稍后！");

                    }
                });
    }



}
