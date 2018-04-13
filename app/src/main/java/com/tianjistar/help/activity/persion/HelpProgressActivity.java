package com.tianjistar.help.activity.persion;

import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianjistar.help.R;
import com.tianjistar.help.adapter.DetailSalfRecoderAdapter;
import com.tianjistar.help.adapter.QuickAdapter;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.ContactsBean;
import com.tianjistar.help.utils.CollectionUtils;
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.utils.FlockUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 救援进展
 * */
public class HelpProgressActivity extends Base1Activity {
    @Bind(R.id.tv_detail_time)
    TextView textViewTime;//呼救时间
    @Bind(R.id.tv_detail_no)
    TextView textViewNo;//订单编号 顶部
    @Bind(R.id.tv_detail_type)
    TextView textViewType;//救援类型
    @Bind(R.id.tv_cancle)
    TextView textViewCancle;//取消订单
    @Bind(R.id.rl_progress)
    RecyclerView recyclerView;
    HelpProgressAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<ContactsBean> mDatas = new ArrayList<>();


    @Override
    public int getContentView() {
        return R.layout.activity_help_progress;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("救援进展");
        recyclerView.setLayoutManager(mManager = new LinearLayoutManager(mActivity));//设置布局管理器
        mAdapter = new HelpProgressAdapter(R.layout.item_progress, mDatas);
        recyclerView.setAdapter(mAdapter);
        getData();
        textViewCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(HelpProgressActivity.this).inflate(R.layout.dialog_delet, null);
                final PopupWindow popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                DisplayUtil.setAlpha(mActivity, 0.5f);
                popupWindow.showAtLocation(view1, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        DisplayUtil.setAlpha(mActivity, 1f);
                    }
                });
                TextView textViewCancle = (TextView) view1.findViewById(R.id.tv_cancel);
                TextView textViewSure = (TextView) view1.findViewById(R.id.ok);
                textViewCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                textViewSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        finish();
//                        MyParams params = new MyParams();
//                        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
//                        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
//                        params.put("id", item.getId());
//                        VictorHttpUtil.doPost(false, mContext, Define.URL_detect_wishes, params, true, "正在删除寄语...", new BaseHttpCallbackListener<Element>() {
//                            @Override
//                            public void callbackSuccess(String url, Element element) {
//                                super.callbackSuccess(url, element);
//                                MyApplication.showToast(element.msg);
//
//
//                            }
//                        });
                    }
                });
            }
        });
    }

    private void getData() {
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
    public class HelpProgressAdapter extends QuickAdapter<ContactsBean> {
        private List<ContactsBean> data=new ArrayList<>();

        public HelpProgressAdapter(int layoutResId, List<ContactsBean> data) {
            super(layoutResId, data);
            this.data=data;
        }
        @Override
        public void onBindViewHolder(BaseViewHolder helper, int positions) {
            final ContactsBean cityBean = data.get(positions);
            if (cityBean != null) {
                TextView textView=helper.getView(R.id.tv_item_xian1);
                TextView textView1=helper.getView(R.id.tv_item_xian2);
                ImageView imageView=helper.getView(R.id.iv_item_dian);
                if (helper.getPosition()==0){
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_dian);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    lp.setMargins(25,35,0,0);
                    imageView.setLayoutParams(lp);
                }
                if (helper.getPosition()==data.size()-1){
                    textView1.setVisibility(View.GONE);
                }
                helper.setText(R.id.tv_now, FlockUtil.jiemi(cityBean.getUsername()));
                helper.setText(R.id.tv_time, FlockUtil.jiemi(cityBean.getUserid()));


            }
        }
        @Override
        protected void convert(BaseViewHolder helper, ContactsBean item) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
