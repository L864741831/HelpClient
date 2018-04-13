package com.tianjistar.help.fragment;



import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.activity.persion.DetailSalfRecoderActivity;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.BaseFragment;

import butterknife.Bind;


/**
 * 保险记录
 *
 */

public class SafeRecoderFragment extends BaseFragment {



    @Override
    public int getContentView() {
        return R.layout.fragment_helprecoder;
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getArguments();
        if (bundle!=null){

        }


    }
}
