package com.lishijia.my.liwushuo.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lishijia.my.liwushuo.R;
import com.lishijia.my.liwushuo.view.adapter.HomeViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by my on 2017/2/7.
 */

public class HomeFrag extends Fragment {

    @BindView(R.id.home_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.home_view_pager)
    ViewPager viewpager;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_frag, container, false);
        ButterKnife.bind(this, view);
        initFragment();
        initView();
        return view;
    }

    private void initView() {
        fragmentManager = getFragmentManager();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        HomeViewPagerAdapter adapter =
                new HomeViewPagerAdapter(fragmentManager, fragments);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

    }

    private void initFragment() {
        fragments.add(HomeSelectionFrag.newInstance());
        fragments.add(HomeCommonFrag.newInstance("送女票"));
        fragments.add(HomeCommonFrag.newInstance("海淘"));
        fragments.add(HomeCommonFrag.newInstance("创意生活"));
        fragments.add(HomeCommonFrag.newInstance("科技范"));
        fragments.add(HomeCommonFrag.newInstance("送爸妈"));
        fragments.add(HomeCommonFrag.newInstance("送基友"));
        fragments.add(HomeCommonFrag.newInstance("送闺蜜"));
        fragments.add(HomeCommonFrag.newInstance("送同事"));
        fragments.add(HomeCommonFrag.newInstance("送宝贝"));
        fragments.add(HomeCommonFrag.newInstance("设计感"));
        fragments.add(HomeCommonFrag.newInstance("文艺风"));
        fragments.add(HomeCommonFrag.newInstance("奇葩搞怪"));
        fragments.add(HomeCommonFrag.newInstance("萌萌哒"));
    }
}
