package com.tianjistar.help.activity.persion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianjistar.help.R;
import com.tianjistar.help.adapter.HelpRecoderViewPagerAdapter;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.fragment.HelpRecoderFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.tianjistar.help.utils.Tools.dip2px;

/**
 * 救援记录
 * **/
public class HelpRecoderActivity extends Base1Activity {

    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private String[] title;
    private String[] title_code = {"10","0","5","4","99"};
    private ArrayList<HelpRecoderFragment> list;
    private HelpRecoderViewPagerAdapter adapter;

    @Override
    public int getContentView() {
        return R.layout.activity_help_recoder;
    }

    @Override
    public void initView() {
        setTitle("救援记录");
        tabLayout = (TabLayout)findViewById(R.id.tb_layout);
        mViewPager = (ViewPager)findViewById(R.id.vp);
    }

    @Override
    public void initData() {
        title = getResources().getStringArray(R.array.tab_title);
        list = new ArrayList<>();

        for (int i = 0; i < title_code.length; i++) {
            HelpRecoderFragment fragment=new HelpRecoderFragment();
            Bundle bundle = new Bundle();
            bundle.putString("state",title_code[i]);
            fragment.setArguments(bundle);
            list.add(fragment);
        }

        adapter = new HelpRecoderViewPagerAdapter(getSupportFragmentManager(),list,title);
        mViewPager.setAdapter(adapter);

        //设置TabLayout与ViewPager的联动
        tabLayout.setupWithViewPager(mViewPager);
        reflex(tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(8);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        //设置标题的正常颜色和选择颜色
        tabLayout.setTabTextColors(Color.parseColor("#686868"),getResources().getColor(R.color.main_color));
        //设置标题的背景色
        tabLayout.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    if(tabLayout.getTabAt(i)==tab){
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.i("info","---onPageSelected---"+position);
//                //滑动监听加载数据，一次只加载一个标签页
//                ((HelpRecoderFragment)adapter.getItem(position)).sendMessage();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    int dp10 = dip2px(tabLayout.getContext(), 10);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
