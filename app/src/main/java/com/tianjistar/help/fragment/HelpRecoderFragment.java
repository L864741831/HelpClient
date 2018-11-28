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
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.RefreshLoadmoreCallbackListener;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
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
 * 救援记录
 */
public class HelpRecoderFragment extends BaseFragment {

    private RescueRecordAdapter mAdapter;
    private RecyclerView mRecyclerView;
    @Bind(R.id.pcfl)
    PtrClassicFrameLayout mPtrFrame;
    private List<RescueRecordBean> mData;
    private PtrHelper<RescueRecordBean> mPtrHelper;
    private String mState;
    private String uuid;


//    private boolean IS_LOADED = false;
//    private boolean isFirst = true;
//    private String mTemp = "";
//
//    private Handler handler = new Handler(){
//        public void handleMessage(android.os.Message msg) {
//            Log.i("info", "---IS_LOADED="+IS_LOADED);
//            if(!IS_LOADED){
//                IS_LOADED = true;
//                //这里执行加载数据的操作
////                Log.e("tag", "我是page"+(mTabPos+1));
//                mPtrHelper.autoRefresh(true);
//            }else {
//                mPtrHelper.autoRefresh(true);
//            }
//            return;
//        }
//    };
//
//    public void sendMessage(){
//        Message message = handler.obtainMessage();
//        message.sendToTarget();
//    }


    @Override
    public int getContentView() {
        return R.layout.fragment_helprecoder;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_wishes_list);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        uuid =  PreferencesUtils.getString(mContext, Constants.USER_UUID);
        Bundle bundle = getArguments();
        if (bundle!=null){
            mState = bundle.getString("state");

            /****************** 设置XRecyclerView属性 **************************/
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
//        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
//                .sizeResId(R.dimen.common_divider_dp)
//                .colorResId(R.color.divider)
//                .build());//添加分隔线
            mAdapter = new RescueRecordAdapter(R.layout.item_rescue_record, mData);
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

//            //设置页和当前页一致时加载，防止预加载
//            if (isFirst && mState.equals(mTemp)) {
//                isFirst = false;
//                sendMessage();
//            }

        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("app","yes");
        params.put("uuid", uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        params.put("pageNo", curpage);
        params.put("pageSize",pageSize);
        if (!"10".equals(mState)){
            params.put("state",mState);
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
    private class RescueRecordAdapter extends QuickAdapter<RescueRecordBean> {

        public RescueRecordAdapter(int layoutResId, List<RescueRecordBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final RescueRecordBean item) {
            //救援单号
            TextView tv_no = helper.getView(R.id.tv_no1);
            //救援状态
            TextView tv_state = helper.getView(R.id.tv_type1);
            //救援机构
            TextView tv_mechanism = helper.getView(R.id.tv_tianyou);
            //救援类型
            TextView tv_rescue_tyoe = helper.getView(R.id.tv_tianyou);
            //时间
            TextView tv_rescue_time = helper.getView(R.id.tv_day);
            //备注
            TextView tv_rescue_detail = helper.getView(R.id.tv_detail);
            //救援地点
            TextView tv_rescue_address = helper.getView(R.id.tv_position);
            //查看地图
            TextView tv_map = helper.getView(R.id.tv_map);

            //
            TextView textViewType1= helper.getView(R.id.tv_tv1);

            final TextView textViewType2 = helper.getView(R.id.tv_tv2);

//            if ("10".equals(mState)){
////                textView.setText("等待出发");
////                textViewType1.setVisibility(View.GONE);
////                textViewType2.setVisibility(View.VISIBLE);
////                textViewType2.setText("确认出发");
//            }else if ("0".equals(mState)){
////                textView.setText("等待接单");
//                textViewType1.setVisibility(View.GONE);
//                textViewType2.setVisibility(View.VISIBLE);
//                textViewType2.setText("确认接单");
//            }else if ("5".equals(mState)){
////                textView.setText("施救中");
//            }else if ("4".equals(mState)){
////                textView.setText("完成");
////                textView.setText("已到达救援地点");
//                textViewType1.setVisibility(View.VISIBLE);
//                textViewType2.setVisibility(View.VISIBLE);
//                textViewType1.setText("上传报告");
//                textViewType2.setText("上传回访");
//            }else if ("99".equals(mState)){
////                textView.setText("已到达救援地点");
//                textViewType1.setVisibility(View.VISIBLE);
//                textViewType2.setVisibility(View.VISIBLE);
//                textViewType1.setText("拍摄");
//                textViewType2.setText("确认到达");
//            }

            tv_no.setText("救援单号:"+item.getUuid());
            //订单状态 ，0 未接单 ，1已经接单，2已到达,3施救中,4完成,99取消
            if ("0".equals(item.getStateId())){
                tv_state.setText("未接单");
                textViewType1.setVisibility(View.VISIBLE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType1.setText("救援进展");
                textViewType2.setText("取消订单");
            }else  if ("1".equals(item.getStateId())){
                tv_state.setText("已经接单");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("救援进展");
            }else  if ("2".equals(item.getStateId())){
                tv_state.setText("已到达");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("救援进展");
            }else  if ("3".equals(item.getStateId())){
                tv_state.setText("施救中");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("救援进展");
            }else  if ("4".equals(item.getStateId())){
                tv_state.setText("已完成");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("救援进展");
            }else  if ("99".equals(item.getStateId())){
                tv_state.setText("已取消");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("救援进展");
            }

//            if (StringUtils.isNotBlank(item.getOffice().getName())){
//                tv_mechanism.setText(item.getOffice().getName());
//                tv_rescue_tyoe.setText(item.getOffice().getName());
//                tv_rescue_time.setText(item.getCreateDate());
//            }

            tv_rescue_detail.setText("备注阿凡达大");
            tv_rescue_address.setText("郑州金水区");

            tv_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyApplication.showToast("地图查看");
                }
            });

            textViewType2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("取消订单".equals(textViewType2.getText().toString())){
                        MyApplication.showToast("取消订单");
                        cancleOrder(item.getId(),"发达的发生");
                    }
                }
            });

        }

    }

    private void cancleOrder(String id,String remarks) {
        MyParams params = new MyParams();
        params.put("id", id);
        params.put("remarks", remarks);

        VictorHttpUtil.doPost(false, mContext, Define.CANCLE_ORDER, params, false, "",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        mPtrHelper.autoRefresh(true);
                    }
                });
    }


}
