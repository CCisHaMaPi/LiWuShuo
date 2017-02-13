package com.lishijia.my.liwushuo.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by my on 2017/2/10.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter{

    private String[] pageName =
            {"精选", "送女票", "海淘", "创意生活", "科技范", "送爸妈", "送基友","" +
                    "送闺蜜", "送同事", "送宝贝", "设计感", "文艺风", "奇葩搞怪", "萌萌哒"};
    private List<Fragment> fragments;

    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageName[position];
    }
}
