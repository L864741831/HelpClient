package com.tianjistar.help.activity.persion;

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
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.WishesBean;
import com.tianjistar.help.utils.CollectionUtils;
import com.tianjistar.help.utils.PtrHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 钱包明细（交易记录）
 * **/
public class MineWalletDetailActivity extends Base1Activity {

    private WishesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    @Bind(R.id.pcfl)
    PtrClassicFrameLayout mPtrFrame;
    private List<WishesBean> mData;
    private PtrHelper<WishesBean> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_mine_wallet_detail;
    }

    @Override
    public void initView() {
        setTitle("交易记录");
        mData = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_wishes_list);
        /****************** 设置XRecyclerView属性 **************************/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
//        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
//                .sizeResId(R.dimen.common_divider_dp)
//                .colorResId(R.color.divider)
//                .build());//添加分隔线
        mAdapter = new WishesAdapter(R.layout.item_mine_wallt, mData);
        mRecyclerView.setAdapter(mAdapter);
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mData);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
//                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 寄语列表适配器
     */
    private class WishesAdapter extends QuickAdapter<WishesBean> {

        public WishesAdapter(int layoutResId, List<WishesBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final WishesBean item) {
//            helper.setText(R.id.tv_item_jiyu_title, item.getTitle() + "");
//            helper.setText(R.id.tv_item_jiyu_content, item.getWord() + "");
//            helper.setText(R.id.tv_item_jiyu_time, item.getTs() + "");


        }
    }
    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("page", curpage);
        params.put("userid","kGHgFzTHLyA%2B%2BZDqR4gcJXXYQWTDkqq%2B3%2FZZC9s9Q9GZxTfZE5etjLLlgX0wt8FKKJ9gNN2cqW6C%0AZjpJOBAo5Bp%2FwjnVXeaiCn%2BF6Fbs0MkogKcboq48rA13RS4JamOanx2qcgB5K%2Fh8FJGFNPKERPnk%0Aalw728kOZdWbXs1NzqHTbw4gUZ3Us0nSpewnpe9eQQ0222TvbcbZfY6lVMP3hm5KO%2FkXsT7sDTWY%0A5Ow%2FUf7UqlHzYBpAPlsCX1bF1WLBxDNlVaUdgzNqYpWs%2Bn1IaZGqgq8VuJg4Z1yCUjceMxWDMWTr%0A57tkUhP4ezj71HOf7oKBcO09%2BovsDBzanmEf7A%3D%3D%0A");
        params.put("imei","867007034124682");
        // TODO: 2017/11/16
        VictorHttpUtil.doPost(false, this, Define.URL_wishes, params, false, null,
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
                                TextView textView=common_no_data.findViewById(R.id.tv_tip);
                                textView.setText("您还没有相关交易");
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
}
