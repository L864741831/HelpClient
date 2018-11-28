package com.tianjistar.help.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianjistar.help.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class InsuranceDialog extends Dialog {

    private Context mContext;
    private Dialog dialog;

    public InsuranceDialog(Context context){
        super(context);
        mContext = context;
        dialog = new Dialog(context, R.style.common_dialog_style);

        dialog.setContentView(R.layout.dialog_insurance);

        //获取rv控件
        RecyclerView rv = dialog.findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(context));
        //设置Adapter
        MyAdapter adapter = new MyAdapter();
        rv.setAdapter(adapter);

        dialog.setCanceledOnTouchOutside(true);
    }

    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

     class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyHolder>{
        private final List<String> list;

        public MyAdapter(){
            list =  new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("商品记录" + i);
            }
        }

        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_claim_record,parent,false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        public void onBindViewHolder(MyHolder holder, int position) {
//            String item = list.get(position);
//            holder.textView.setText(item);
        }

        public int getItemCount() {
            return list.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            public ImageView icon;
            public TextView textView;

            //实现的方法
            public MyHolder(View itemView) {
                super(itemView);
//                icon= (ImageView) itemView.findViewById(R.id.item_iv_icon);
//                textView= (TextView) itemView.findViewById(R.id.item_tv_title);
            }
        }
    }


}
