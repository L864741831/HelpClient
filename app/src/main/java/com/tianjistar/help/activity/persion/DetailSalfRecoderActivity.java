package com.tianjistar.help.activity.persion;

import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.tianjistar.help.R;
import com.tianjistar.help.adapter.DetailSalfRecoderAdapter;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.ContactsBean;
import com.tianjistar.help.utils.CollectionUtils;
import com.tianjistar.help.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class DetailSalfRecoderActivity extends Base1Activity {
    @Bind(R.id.item_know)
    TextView textViewKnow;
    @Bind(R.id.rv)
    MyRecyclerView recyclerView;
    private View headerView;
    private DetailSalfRecoderAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<ContactsBean> mDatas = new ArrayList<>();


    @Override
    public int getContentView() {
        return R.layout.activity_detail_salf_recoder;
    }

    @Override
    public void initView() {
        initHeaderView();
        recyclerView.addHeaderView(headerView);
        recyclerView.setLayoutManager(mManager = new LinearLayoutManager(mActivity));//设置布局管理器
        mAdapter = new DetailSalfRecoderAdapter(R.layout.item_city, mDatas);
        recyclerView.setAdapter(mAdapter);
        getfrientslist();
        textViewKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();  //finish当前activity
                overridePendingTransition(R.anim.push_up_in,
                        R.anim.push_down_out);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    //处理后退键的情况
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.push_up_in,
                    R.anim.push_down_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //设置头部数据
    private void initHeaderView() {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_detal_salf_head, null);
        // contacts_search = (ImageView)headerView.findViewById(R.id.edit_search);
    }
    /**
     * 获取好友列表
     */
    private void getfrientslist() {
        MyParams params = new MyParams();
        params.put("imei", "867007034124682");
        params.put("userid","kGHgFzTHLyA%2B%2BZDqR4gcJXXYQWTDkqq%2B3%2FZZC9s9Q9GZxTfZE5etjLLlgX0wt8FKKJ9gNN2cqW6C%0AZjpJOBAo5Bp%2FwjnVXeaiCn%2BF6Fbs0MkogKcboq48rA13RS4JamOanx2qcgB5K%2Fh8FJGFNPKERPnk%0Aalw728kOZdWbXs1NzqHTbw4gUZ3Us0nSpewnpe9eQQ0222TvbcbZfY6lVMP3hm5KO%2FkXsT7sDTWY%0A5Ow%2FUf7UqlHzYBpAPlsCX1bF1WLBxDNlVaUdgzNqYpWs%2Bn1IaZGqgq8VuJg4Z1yCUjceMxWDMWTr%0A57tkUhP4ezj71HOf7oKBcO09%2BovsDBzanmEf7A%3D%3D%0A");
        VictorHttpUtil.doPost(false, mContext, Define.URL_friend_list, params, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                List<ContactsBean> temp = JSON.parseArray(element.data, ContactsBean.class);
                mDatas.clear();//清空数据
                if (!CollectionUtils.isEmpty(temp)) {
//                     有数据
                    mDatas.addAll(temp);
                    mAdapter.setNewData(mDatas);
                    mAdapter.notifyDataSetChanged();

                }

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getfrientslist();
    }
}
