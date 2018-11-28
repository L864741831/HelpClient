package com.tianjistar.help.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.tianjistar.help.R;
import com.tianjistar.help.activity.WebviewActivity;
import com.tianjistar.help.adapter.QuickAdapter;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.RefreshLoadmoreCallbackListener;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.BaseFragment;
import com.tianjistar.help.bean.FirstAidBean;
import com.tianjistar.help.utils.CollectionUtils;
import com.tianjistar.help.utils.PtrHelper;
import com.tianjistar.help.utils.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


/**
 * 急救小常识
 */
public class CommonSenseFragment extends BaseFragment {

    private CommonSenseAdapter mAdapter;

    @Bind(R.id.pcfl)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.rv_wishes_list)
    RecyclerView mRecyclerView;

    private List<FirstAidBean> mData;
    private PtrHelper<FirstAidBean> mPtrHelper;
    private String type;

    @Override
    public int getContentView() {
        return R.layout.fragment_commonsense;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle!=null){
            type = bundle.getString("type");
        }

        /****************** 设置XRecyclerView属性 **************************/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器

        mAdapter = new CommonSenseAdapter(R.layout.item_commensense, mData);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mData);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mPtrHelper.enablePullToRefresh(false);

        mPtrHelper.autoRefresh(true);
    }

    @Override
    public void initListener() {
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("pageNo", curpage);
        params.put("pageSize", pageSize);
        params.put("state",type);

        VictorHttpUtil.doPost(false, mContext, Define.FIRST_AID_COMMON_SENSE, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper)  {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<FirstAidBean> temp = JSON.parseArray(element.rows, FirstAidBean.class);
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

    /**
     * 列表适配器
     */
    private class CommonSenseAdapter extends QuickAdapter<FirstAidBean> {

        public CommonSenseAdapter(int layoutResId, List<FirstAidBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final FirstAidBean item) {
            TextView textView = helper.getView(R.id.tv_fristitem_type);
            ImageView imageView = helper.getView(R.id.iv_first_aid_img);
            RelativeLayout relativeLayout = helper.getView(R.id.ll_whole_layout);

            if (type.equals("系统公告")){
                textView.setVisibility(View.GONE);
            }
            helper.setText(R.id.tv_fristitem_title, item.getTitle() + "");
            if (StringUtils.isNotBlank(item.getCreateDate())){
                String date = item.getCreateDate().substring(5,10);
                helper.setText(R.id.tv_fristitem_type, "道路救援 | "+date);
            }
            helper.setText(R.id.tv_fristitem_look, item.getClickhit()+ "人阅读");
            if (StringUtils.isNotBlank(item.getTitleimg())){
                String imgUrl = Define.BASE_URL + item.getTitleimg();
                Picasso.with(mContext).load(imgUrl)
                        .error(R.drawable.ic_salf_head)
                        .placeholder(R.drawable.ic_salf_head)
                        .into(imageView);
            }

            //详情
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("info","---详情---"+item.getId());

                    Bundle bundleHttp=new Bundle();
                    bundleHttp.putString("mUrl", Define.BASE_URL+"/"+item.getH5());
                    MyApplication.openActivity(mActivity, WebviewActivity.class,bundleHttp);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("info","---onResume---");
//        mPtrHelper.autoRefresh(true);
    }


}
