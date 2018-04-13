package com.tianjistar.help.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.activity.persion.DetailSalfRecoderActivity;
import com.tianjistar.help.activity.persion.HelpCardActivity;
import com.tianjistar.help.activity.persion.HelpRecoderActivity;
import com.tianjistar.help.activity.persion.MineMessageActivity;
import com.tianjistar.help.activity.persion.MineSetActivity;
import com.tianjistar.help.activity.persion.MineWalletctivity;
import com.tianjistar.help.activity.persion.PersionInfoActivity;
import com.tianjistar.help.activity.persion.SafeRecorderActivity;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.BaseFragment;

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
    @Override
    public int getContentView() {
        return R.layout.fragment_persion;
    }

    @Override
    protected void initView() {
        super.initView();
        getData();
        setListener();
    }
    //获取网络数据
    private void getData() {

    }

    private void setListener() {
        relativeLayoutHelpCard.setOnClickListener(this);
        relativeLayoutHelpRecoder.setOnClickListener(this);
        relativeLayoutMessage.setOnClickListener(this);
        relativeLayoutMoney.setOnClickListener(this);
        relativeLayoutSafeCard.setOnClickListener(this);
        relativeLayoutSet.setOnClickListener(this);
        imgeViewShare.setOnClickListener(this);
        textViewInfo.setOnClickListener(this);
        relativeLayoutHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_persion_helpcard://救援卡
                MyApplication.openActivity(mContext, HelpCardActivity.class);

                break;
            case R.id.rl_persion_helprecoder://救援记录
                MyApplication.openActivity(mContext, HelpRecoderActivity.class);

                break;
            case R.id.rl_persion_message://我的消息
                MyApplication.openActivity(mContext, MineMessageActivity.class);
                break;
            case R.id.rl_head://查看个人资料
                MyApplication.openActivity(mContext, PersionInfoActivity.class);

                break;
            case R.id.rl_persion_money://我的钱包
                MyApplication.openActivity(mContext, MineWalletctivity.class);

                break;
            case R.id.rl_persion_safecard://保险记录
                MyApplication.openActivity(mContext, SafeRecorderActivity.class);
                break;
            case R.id.rl_persion_set://设置
                MyApplication.openActivity(mContext, MineSetActivity.class);
                break;
            case R.id.tv_persion_info://查看个人资料
                MyApplication.openActivity(mContext, PersionInfoActivity.class);
                break;
            case R.id.iv_persion_share://分享
//                MyApplication.openActivity(mContext, DetailSalfRecoderActivity.class);

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
