package com.tianjistar.help.activity.persion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.tianjistar.help.R;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;

import butterknife.Bind;

/**
 * 我的消息
 * */
public class MineMessageActivity extends Base1Activity {

    @Bind(R.id.rl_message)
    RelativeLayout relativeLayoutMessage;

    @Override
    public int getContentView() {
        return R.layout.activity_mine_message;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的消息");
        relativeLayoutMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.openActivity(mContext,MineWalletDetailActivity.class);

            }
        });
    }
}
