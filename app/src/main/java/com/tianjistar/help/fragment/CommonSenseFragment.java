package com.tianjistar.help.fragment;



import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianjistar.help.R;

import com.tianjistar.help.adapter.QuickAdapter;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.RefreshLoadmoreCallbackListener;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.AppSpContact;
import com.tianjistar.help.base.BaseFragment;
import com.tianjistar.help.bean.WishesBean;
import com.tianjistar.help.utils.CollectionUtils;
import com.tianjistar.help.utils.PtrHelper;
import com.tianjistar.help.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


/**
 * 急救小常识
 *
 */

public class CommonSenseFragment extends BaseFragment {
    private CommonSenseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    @Bind(R.id.pcfl)
    PtrClassicFrameLayout mPtrFrame;
    private List<WishesBean> mData;
    private PtrHelper<WishesBean> mPtrHelper;
    private String type;


    @Override
    public int getContentView() {
        return R.layout.fragment_commonsense;
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getArguments();
        if (bundle!=null){
            type=bundle.getString("type");
        }
        mData = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_wishes_list);
        /****************** 设置XRecyclerView属性 **************************/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器

        mAdapter = new CommonSenseAdapter(R.layout.item_commensense, mData);
        mRecyclerView.setAdapter(mAdapter);
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mData);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);


    }

    /**
     * 列表适配器
     */
    private class CommonSenseAdapter extends QuickAdapter<WishesBean> {

        public CommonSenseAdapter(int layoutResId, List<WishesBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final WishesBean item) {
            TextView textView=helper.getView(R.id.tv_fristitem_type);

            if (type.equals("系统公告")){
                textView.setVisibility(View.GONE);
            }
            helper.setText(R.id.tv_fristitem_title, item.getTitle() + "");
            helper.setText(R.id.tv_fristitem_look, item.getId()+ "人阅读");
            helper.setText(R.id.tv_fristitem_type, "道路救援 |    "+item.getTs() + "");
        }
    }
    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("page", curpage);
        params.put("userid","kGHgFzTHLyA%2B%2BZDqR4gcJXXYQWTDkqq%2B3%2FZZC9s9Q9GZxTfZE5etjLLlgX0wt8FKKJ9gNN2cqW6C%0AZjpJOBAo5Bp%2FwjnVXeaiCn%2BF6Fbs0MkogKcboq48rA13RS4JamOanx2qcgB5K%2Fh8FJGFNPKERPnk%0Aalw728kOZdWbXs1NzqHTbw4gUZ3Us0nSpewnpe9eQQ0222TvbcbZfY6lVMP3hm5KO%2FkXsT7sDTWY%0A5Ow%2FUf7UqlHzYBpAPlsCX1bF1WLBxDNlVaUdgzNqYpWs%2Bn1IaZGqgq8VuJg4Z1yCUjceMxWDMWTr%0A57tkUhP4ezj71HOf7oKBcO09%2BovsDBzanmEf7A%3D%3D%0A");
        params.put("imei","867007034124682");
        // TODO: 2017/11/16
        VictorHttpUtil.doPost(false, mContext, Define.URL_wishes, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper)  {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<WishesBean> temp = JSON.parseArray(element.data, WishesBean.class);
                        if (pullToRefresh) {// 下拉刷新
                            mData.clear();//清空数据
                            mAdapter.setNewData(mData);
                            mAdapter.notifyDataSetChanged();
                            if (CollectionUtils.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mData.addAll(temp);
                                mAdapter.setNewData(mData);
                                mAdapter.notifyDataSetChanged();
                                if (CollectionUtils.getSize(temp) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {// 加载更多
                            mPtrHelper.processLoadMore(temp);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPtrHelper.autoRefresh(true);
    }
}
