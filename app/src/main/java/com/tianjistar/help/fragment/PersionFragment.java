package com.tianjistar.help.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.aaa.aActivity;
import com.tianjistar.help.activity.ApplyRefundActivity;
import com.tianjistar.help.activity.InsuranceProductsActivity;
import com.tianjistar.help.activity.login.LoginActivity;
import com.tianjistar.help.activity.persion.HelpCardActivity;
import com.tianjistar.help.activity.persion.HelpRecoderActivity;
import com.tianjistar.help.activity.persion.InsuranceRecordDetailActivity;
import com.tianjistar.help.activity.persion.MineMessageActivity;
import com.tianjistar.help.activity.persion.MineSetActivity;
import com.tianjistar.help.activity.persion.MineWalletctivity;
import com.tianjistar.help.activity.persion.PersionInfoActivity;
import com.tianjistar.help.activity.persion.SafeRecorderActivity;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.BaseFragment;
import com.tianjistar.help.utils.PicassoUtils;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.SharedPreferencesHelper;
import com.tianjistar.help.utils.StringUtils;
import com.tianjistar.help.view.dialog.InsuranceDialog;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 *
 */
public class PersionFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.rl_persion_helpcard)
    RelativeLayout relativeLayoutHelpCard;
    @Bind(R.id.rl_persion_helprecoder)
    RelativeLayout relativeLayoutHelpRecoder;
    @Bind(R.id.rl_persion_message)
    RelativeLayout relativeLayoutMessage;
    @Bind(R.id.rl_persion_money)
    RelativeLayout relativeLayoutMoney;
    @Bind(R.id.rl_persion_safecard)
    RelativeLayout relativeLayoutSafeCard;
    @Bind(R.id.rl_persion_set)
    RelativeLayout relativeLayoutSet;
    @Bind(R.id.iv_persion_share)
    ImageView imgeViewShare;
    @Bind(R.id.tv_persion_name)
    TextView textViewName;
    @Bind(R.id.tv_persion_info)
    TextView textViewInfo;
    @Bind(R.id.iv_persion_head)
    CircleImageView imgeViewHead;
    @Bind(R.id.rl_head)
    RelativeLayout relativeLayoutHead;

    private SharedPreferencesHelper preferencesHelper;
    //用户头像，昵称
    private String userAvatar,userName;

    @Override
    public int getContentView() {
        return R.layout.fragment_persion;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        preferencesHelper = SharedPreferencesHelper.getInstance();
    }

    @Override
    public void initListener() {
        relativeLayoutHelpCard.setOnClickListener(this);
        relativeLayoutHelpRecoder.setOnClickListener(this);
        relativeLayoutMessage.setOnClickListener(this);
        relativeLayoutMoney.setOnClickListener(this);
        relativeLayoutSafeCard.setOnClickListener(this);
        relativeLayoutSet.setOnClickListener(this);
        imgeViewShare.setOnClickListener(this);
//        textViewInfo.setOnClickListener(this);
        relativeLayoutHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head://查看个人资料
                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                    MyApplication.openActivity(mContext, PersionInfoActivity.class);
                }else {
                    MyApplication.showToast("请先登录");
                    MyApplication.openActivity(mContext, LoginActivity.class);
                }
                break;
            case R.id.rl_persion_money://我的钱包
//                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                    MyApplication.openActivity(mActivity, MineWalletctivity.class);
//                }else {
//                    MyApplication.showToast("请先登录");
//                    MyApplication.openActivity(mActivity, LoginActivity.class);
//                }
                break;
            case R.id.rl_persion_helpcard://救援卡
//                MyApplication.openActivity(mContext, HelpCardActivity.class);



//                MyApplication.openActivity(mContext, InsuranceRecordDetailActivity.class);
//                MyApplication.openActivity(mContext, ApplyRefundActivity.class);

                MyApplication.openActivity(mContext,aActivity.class);

                break;
            case R.id.rl_persion_helprecoder://救援记录
//                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                    MyApplication.openActivity(mActivity, HelpRecoderActivity.class);
//                }else {
//                    MyApplication.showToast("请先登录");
//                    MyApplication.openActivity(mActivity, LoginActivity.class);
//                }

//                InsuranceDialog dialog = new InsuranceDialog(mContext);
//                dialog.show();
                break;
            case R.id.rl_persion_safecard://保险记录
//                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                MyApplication.openActivity(mActivity, SafeRecorderActivity.class);
//                }else {
//                    MyApplication.showToast("请先登录");
//                MyApplication.openActivity(mActivity, LoginActivity.class);
//                }
                break;
            case R.id.rl_persion_message://我的消息
//                if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
                    MyApplication.openActivity(mActivity, MineMessageActivity.class);
//                }else {
//                    MyApplication.showToast("请先登录");
//                    MyApplication.openActivity(mActivity, LoginActivity.class);
//                }
                break;
            case R.id.rl_persion_set://设置
                MyApplication.openActivity(mContext, MineSetActivity.class);
                break;
            case R.id.iv_persion_share://分享
//                MyApplication.openActivity(mContext, DetailSalfRecoderActivity.class);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (preferencesHelper.getBoolean(Constants.LOGIN_SUCCESS)){
            userAvatar =  PreferencesUtils.getString(mContext,Constants.USER_AVATAR);
            userName =  PreferencesUtils.getString(mContext,Constants.USER_NAME);
            if (StringUtils.isNotBlank(userAvatar)){
                PicassoUtils.loadHeadImage(mContext,userAvatar,imgeViewHead);
            }
            if (StringUtils.isNotBlank(userName)){
                textViewName.setText(userName);
            }
        }else {
            PicassoUtils.loadHeadImage(mContext,"",imgeViewHead);
            textViewName.setText("用户");
        }
    }


}
