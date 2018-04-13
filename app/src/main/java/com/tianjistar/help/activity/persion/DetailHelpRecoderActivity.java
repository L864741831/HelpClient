package com.tianjistar.help.activity.persion;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
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
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.utils.SharedPreferencesHelper;

import butterknife.Bind;

/**
 * 救援详情
 * */
public class DetailHelpRecoderActivity extends Base1Activity implements View.OnClickListener {
    @Bind(R.id.tv_detail_time)
    TextView textViewTime;//呼救时间
    @Bind(R.id.tv_detail_no)
    TextView textViewNo;//订单编号 顶部
    @Bind(R.id.tv_detail_type)
    TextView textViewType;//救援类型
    @Bind(R.id.tv_jinzhan)
    TextView textViewJinZhan;//救援进展
    @Bind(R.id.tv_cancle)
    TextView textViewCancle;//取消订单
    @Bind(R.id.tv_type)
    TextView textViewDetailType;//救援状态 例如正在救援
    @Bind(R.id.tv_day)
    TextView textViewReciviTime;//接单时间
    @Bind(R.id.tv_detail_wu)
    TextView textViewWuPin;//所需物品
    @Bind(R.id.tv_detail_detail)
    TextView textViewDetail;//详情
    @Bind(R.id.tv_detail)
    TextView textViewBeiZhu;//备注
    @Bind(R.id.tv_position)
    TextView textViewPosition;//地理位置
    @Override
    public int getContentView() {
        return R.layout.activity_detail_help_recoder;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("救援详情");
        getData();
        setListener();
    }

    private void setListener() {
        textViewJinZhan.setOnClickListener(this);
        textViewCancle.setOnClickListener(this);
    }

    /*获取网络数据*/
    private void getData() {
        textViewNo.setText("救援单号：  ");
        textViewTime.setText("呼救时间：  ");
        textViewType.setText("救援类型：  ");
        textViewReciviTime.setText("接单时间：  ");
        textViewDetailType.setText("正在施救");
        textViewWuPin.setText("所需物品");
        textViewDetail.setText("详情");
        textViewBeiZhu.setText("备注");
        textViewPosition.setText("地理位置");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jinzhan://救援进展
                MyApplication.openActivity(mContext,HelpProgressActivity.class);

                break;

            case R.id.tv_cancle://取消订单
                View view1 = LayoutInflater.from(DetailHelpRecoderActivity.this).inflate(R.layout.dialog_delet, null);
                final PopupWindow popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                DisplayUtil.setAlpha(mActivity, 0.5f);
                popupWindow.showAtLocation(view1, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        DisplayUtil.setAlpha(mActivity, 1f);
                    }
                });
                TextView textViewCancle = (TextView) view1.findViewById(R.id.tv_cancel);
                TextView textViewSure = (TextView) view1.findViewById(R.id.ok);
                textViewCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                textViewSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        finish();
//                        MyParams params = new MyParams();
//                        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
//                        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
//                        params.put("id", item.getId());
//                        VictorHttpUtil.doPost(false, mContext, Define.URL_detect_wishes, params, true, "正在删除寄语...", new BaseHttpCallbackListener<Element>() {
//                            @Override
//                            public void callbackSuccess(String url, Element element) {
//                                super.callbackSuccess(url, element);
//                                MyApplication.showToast(element.msg);
//
//
//                            }
//                        });
                    }
                });
                break;
        }

    }
}
