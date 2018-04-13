package com.tianjistar.help.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tianjistar.help.R;
import com.tianjistar.help.bean.ContactsBean;


import java.util.ArrayList;
import java.util.List;




/**
 * Created by lichaofan .
 * Date: 2017-9-20
 */
public class DetailSalfRecoderAdapter extends QuickAdapter<ContactsBean> {
    private List<ContactsBean> data=new ArrayList<>();

    public DetailSalfRecoderAdapter(int layoutResId, List<ContactsBean> data) {
        super(layoutResId, data);
        this.data=data;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder helper, int positions) {
        final ContactsBean cityBean = data.get(positions);
        if (cityBean != null) {
            helper.setText(R.id.item_no, cityBean.getUsername());


        }
    }
    @Override
    protected void convert(BaseViewHolder helper, ContactsBean item) {

    }



}
