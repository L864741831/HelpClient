package com.tianjistar.help.activity;

import android.icu.util.Calendar;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianjistar.help.R;
import com.tianjistar.help.aaa.aActivity;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.utils.LogUtil;
import com.tianjistar.help.view.dialog.StarInfoDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/6/6.
 * 申请退保
 */
public class ApplyRefundActivity extends Base1Activity {

    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.ll)
    LinearLayout ll;

    @Override
    public int getContentView() {
        return R.layout.activity_apply_refund;
    }

    @Override
    public void initView() {
    }

    int heigth,width;
    @Override
    public void initData() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        heigth = dm.heightPixels;
        width = dm.widthPixels;

        LogUtil.i("info","---width---"+width);
        LogUtil.i("info","---heigth---"+heigth);


        final int height = getStatusHeight();

//        int x = imageView.getWidth();
//        int y = imageView.getHeight();

//        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
//                x = imageView.getWidth();
//                y = imageView.getHeight();
//
//                LogUtil.i("info","---x-1--"+x);
//                LogUtil.i("info","---y-1--"+y);
//                return false;
//            }
//        });

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else {
                    imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                x = imageView.getWidth();
                y = imageView.getHeight();

                xx = width/2;
                yy = (heigth - height)/2;

                btnTranslate2(imageView);

//                getPush();


            }
        });




//    String URL = "http://nlcs.tianjistar.com/index.php/Home/celebrity/celebrity";
//    private void getPush() {
//        MyParams params = new MyParams();
//        VictorHttpUtil.doPost(false, mContext, URL, params, false, "加载中...",
//                new BaseHttpCallbackListener<Element>() {
//                    @Override
//                    public void callbackSuccess(String url, Element element) {
//                        super.callbackSuccess(url, element);
//                        if (StringUtils.isNotBlank(element.data)){
////                            List<StarListBean> list = JSON.parseArray(element.data, StarListBean.class);
////                            if (list!=null && list.size()>0){
////
////                            }else {
////
////                            }
//                        }else {
//                        }
//                    }
//                });

        boolean nowStr =  compareVersions("1.0.3","1.0");
        boolean nowStr2 = compareVersions("1.1.3","2.0.5");
        boolean nowStr3=   compareVersions("1.2.0","1.0.5");
        LogUtil.i("info","---nowStr--"+nowStr);
        LogUtil.i("info","---nowStr2--"+nowStr2);
        LogUtil.i("info","---nowStr3--"+nowStr3);

    }

    int xx,yy;
    int x,y;
    @Override
    public void initListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getCurrentTime();
            }
        });

    }

    private void getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
//        int year = calendar.get(Calendar.YEAR);
//        //月
//        int month = calendar.get(Calendar.MONTH)+1;
//        //日
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //获取系统时间
        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);

        int totalTime = hour*3600 + minute*60 + second;
    }

    public static int getTimeCompareSize(String startTime, String endTime){
        int i=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                i= 1;
            }else if (date2.getTime()==date1.getTime()){
                i= 2;
            }else if (date2.getTime()>date1.getTime()){
                //正常情况下的逻辑操作.
                i= 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  i;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void btnTranslate2(final View view) {

        //平移动画
        //自身中点做坐标原点（0,0） 向右移动200，向下移动300
        TranslateAnimation translateAnimation = new TranslateAnimation(0, -xx, 0, yy);
        ScaleAnimation alphaAnimation = new ScaleAnimation(1f, 0.3f, 1f, 0.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        //动画集
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translateAnimation);
        set.addAnimation(alphaAnimation);


        set.setDuration(1000);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtil.i("info","---onAnimationStart---");
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(set);
    }

    public void btnTranslate(final View view) {
        //平移动画
        //自身中点做坐标原点（0,0） 向右移动200，向下移动300
        TranslateAnimation ta = new TranslateAnimation(0, -xx, 0, yy);
        ta.setDuration(1000);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtil.i("info","---onAnimationStart---");
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtil.i("info","---onAnimationEnd---");
                imageView.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                btnScale(ll);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtil.i("info","---onAnimationRepeat---");
            }
        });
        view.startAnimation(ta);
    }

    public void btnScale(View view) {
        //缩放动画 public ScaleAnimation(float fromX, float toX, float fromY, float toY){}
//        向右放大2，向下放大2 ，后恢复
//        ScaleAnimation sa = new ScaleAnimation(0, 2, 0, 2);
        ScaleAnimation sa = new ScaleAnimation(1f, 3.0f, 1f, 3f,
//        ScaleAnimation sa = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        sa.setDuration(2000);
        sa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll.setVisibility(View.GONE);
                StarInfoDialog dialog = new StarInfoDialog(mContext);
                dialog.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(sa);
    }

    /**
     * 获取状态栏高度——方法2
     * */
    private int getStatusHeight(){
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2;
    }


    /**
     * 如果版本1 大于 版本2 返回true 否则返回fasle 支持 2.2 2.2.1 比较
     * 支持不同位数的比较  2.0.0.0.0.1  2.0 对比
     *
     * @param v1 版本服务器版本 " 1.1.2 "
     * @param v2 版本 当前版本 " 1.2.1 "
     * @return ture ：需要更新 false ： 不需要更新
     */
    public static boolean compareVersions(String v1, String v2) {
        //判断是否为空数据
        if (TextUtils.equals(v1, "") || TextUtils.equals(v2, "")) {
            return false;
        }
        String[] str1 = v1.split("\\.");
        String[] str2 = v2.split("\\.");

        if (str1.length == str2.length) {
            for (int i = 0; i < str1.length; i++) {
                if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                    return true;
                } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                    return false;
                } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {

                }
            }
        } else {
            if (str1.length > str2.length) {
                for (int i = 0; i < str2.length; i++) {
                    if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                        return true;
                    } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                        return false;

                    } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {
                        if (str2.length == 1) {
                            continue;
                        }
                        if (i == str2.length - 1) {

                            for (int j = i; j < str1.length; j++) {
                                if (Integer.parseInt(str1[j]) != 0) {
                                    return true;
                                }
                                if (j == str1.length - 1) {
                                    return false;
                                }

                            }
                            return true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < str1.length; i++) {
                    if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                        return true;
                    } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                        return false;

                    } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {
                        if (str1.length == 1) {
                            continue;
                        }
                        if (i == str1.length - 1) {
                            return false;

                        }
                    }

                }
            }
        }
        return false;
    }




}
