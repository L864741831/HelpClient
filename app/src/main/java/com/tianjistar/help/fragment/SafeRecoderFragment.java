package com.tianjistar.help.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.base.BaseFragment;
import com.tianjistar.help.bean.RescueRecordBean;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.CollectionUtils;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.PtrHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * 保险记录 Fragment
 */
public class SafeRecoderFragment extends BaseFragment {

    @Bind(R.id.rv_wishes_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pcfl)
    PtrClassicFrameLayout mPtrFrame;

    private List<RescueRecordBean> mData;
    private InsuranceRecordAdapter mAdapter;
    private PtrHelper<RescueRecordBean> mPtrHelper;

    private String mType;
    private String uuid;

    @Override
    public int getContentView() {
        return R.layout.fragment_helprecoder;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        uuid =  PreferencesUtils.getString(mContext, Constants.USER_UUID);
        Bundle bundle = getArguments();
        if (bundle!=null){
            mType = bundle.getString("type");
        }

        //设置XRecyclerView属性
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mAdapter = new InsuranceRecordAdapter(R.layout.item_sale_recoder, mData);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mData);
        //允许加载更多
        mPtrHelper.enableLoadMore(true, mRecyclerView);
//        mPtrHelper.autoRefresh(true);
    }

    @Override
    public void initListener() {
        mPtrHelper.setOnRequestDataListener(onRequestDataListener);
    }

    PtrHelper.OnRequestDataListener onRequestDataListener = new PtrHelper.OnRequestDataListener() {
        @Override
        public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
//            _reqData(pullToRefresh, curpage, pageSize);
        }
    };

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("app","yes");
        params.put("uuid", uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        params.put("pageNo", curpage);
        params.put("pageSize",pageSize);
        if (!"10".equals(mType)){
            params.put("state",mType);
        }
        VictorHttpUtil.doPost(false, getActivity(), Define.RESCUE_RECORD, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper)  {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<RescueRecordBean> temp = JSON.parseArray(element.rows, RescueRecordBean.class);
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

    /**
     * 列表适配器
     */
    private class InsuranceRecordAdapter extends QuickAdapter<RescueRecordBean> {

        public InsuranceRecordAdapter(int layoutResId, List<RescueRecordBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final RescueRecordBean item) {
            ImageView iv_item_salf_head = helper.getView(R.id.iv_item_salf_head);
            //
            TextView tv_item_salf_title = helper.getView(R.id.tv_item_salf_title);
            //时间
            TextView tv_item_salf_time = helper.getView(R.id.tv_item_salf_time);
            //
            ImageView iv_protecting = helper.getView(R.id.iv_protecting);


//            if ("0".equals(item.getStateId())){
//                tv_state.setText("未接单");
//                textViewType1.setVisibility(View.VISIBLE);
//                textViewType2.setVisibility(View.VISIBLE);
//                textViewType1.setText("救援进展");
//                textViewType2.setText("取消订单");
//            }
//
//            tv_rescue_detail.setText("备注阿凡达大");
//            tv_rescue_address.setText("郑州金水区");
//
//            tv_map.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MyApplication.showToast("地图查看");
//                }
//            });

        }

    }


}
