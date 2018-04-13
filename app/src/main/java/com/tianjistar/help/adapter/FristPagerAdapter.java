package com.tianjistar.help.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tianjistar.help.fragment.CommonSenseFragment;
import com.tianjistar.help.fragment.HelpRecoderFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/21.
 */

public class FristPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<CommonSenseFragment> list;
    private String[] title;//标题的名称

    public FristPagerAdapter(FragmentManager fm, ArrayList<CommonSenseFragment> list, String[] title) {
        super(fm);
        this.list = list;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
