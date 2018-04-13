package com.tianjistar.help.activity.persion;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ViewPager vp;
    private String[] title;
    private ArrayList<HelpRecoderFragment> list;


    @Override
    public int getContentView() {
        return R.layout.activity_help_recoder;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("救援记录");
        tabLayout=(TabLayout)findViewById(R.id.tb_layout);
        vp=(ViewPager)findViewById(R.id.vp);
        title=getResources().getStringArray(R.array.tab_title);
        list=new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            HelpRecoderFragment fragment=new HelpRecoderFragment();
            Bundle bundle=new Bundle();
            bundle.putString("type",title[i]);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        HelpRecoderViewPagerAdapter adapter=new HelpRecoderViewPagerAdapter(getSupportFragmentManager(),list,title);
        vp.setAdapter(adapter);
        //设置TabLayout与ViewPager的联动
        tabLayout.setupWithViewPager(vp);
        reflex(tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(8);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        //设置标题的正常颜色和选择颜色
        tabLayout.setTabTextColors(Color.parseColor("#686868"),getResources().getColor(R.color.main_color));
        //设置标题的背景色
        tabLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    if(tabLayout.getTabAt(i)==tab)
                    {
                        vp.setCurrentItem(i);
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
