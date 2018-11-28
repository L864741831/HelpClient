package com.tianjistar.help.activity.persion;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.activity.WebviewActivity;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.AppUtil;

import butterknife.Bind;

/**
 * 关于天佑
 * */
public class MineAboutActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.rl_version)
    RelativeLayout relativeLayoutVersion;
    @Bind(R.id.rl_http)
    RelativeLayout relativeLayoutHttp;//服务协议
    @Bind(R.id.rl_jieshao)
    RelativeLayout relativeLayoutCode;//去评分
    @Bind(R.id.tv_about_version)
    TextView textViewVersion;
    int version;
    @Override
    public int getContentView() {
        return R.layout.activity_mine_about;
    }

    @Override
    public void initView() {
        setTitle("关于天佑");
        textViewVersion.setText("天佑  "+AppUtil.getVersionName(mContext)+"");
        getData();
        setListener();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void getData() {
        //获取版本看是否有新版本 如果有版本显示 有新版本，有新版本更新对话框 没有则显示版本号
        version=1;//有新版本
    }

    private void setListener() {
        relativeLayoutVersion.setOnClickListener(this);
        relativeLayoutHttp.setOnClickListener(this);
        relativeLayoutCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_version://版本更新
                if (version==1){
                    // TODO: 2018/3/21  
                    showUpdataDialog("","",false);
                }
                break;
            case R.id.rl_http://服务协议
                Bundle bundleHttp=new Bundle();
                bundleHttp.putString("mUrl", Define.API_DOMAIN+"");
                MyApplication.openActivity(mContext, WebviewActivity.class,bundleHttp);
                break;
            case R.id.rl_jieshao://去评分

                break;
            default:

                break;
        }

    }
    protected void showUpdataDialog(String msg, final String loadurl,boolean isUpdate) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this,R.style.Dialog_Alert_Self) ;
        builer.setTitle("版本升级");
        builer.setMessage(msg);
        builer.setCancelable(!isUpdate);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(loadurl);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        if(!isUpdate){
            //当点取消按钮时进行登录
            builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        AlertDialog dialog = builer.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff8200"));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ff8200"));
    }
}
