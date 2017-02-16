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
import com.lishijia.my.liwushuo.dagger.AppModule;
import com.lishijia.my.liwushuo.dagger.DaggerAppComponent;
import com.lishijia.my.liwushuo.model.home.bean.HomeBean;
import com.lishijia.my.liwushuo.presenter.home.IHomeTitlePresenter;
import com.lishijia.my.liwushuo.view.adapter.HomeViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lsj on 2017/2/7.
 */

public class HomeFrag extends Fragment implements IHomeTitlePresenter.IHomeTitlePresenterCallBack{

    @BindView(R.id.home_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.home_view_pager)
    ViewPager viewpager;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    @Inject
    IHomeTitlePresenter homeTitlePresenter;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_frag, container, false);
        ButterKnife.bind(this, view);
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build()
                .inject(this);
        homeTitlePresenter.queryHomeBean();
        initView();
        return view;
    }

    private void initView() {
        fragmentManager = getFragmentManager();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeViewPagerAdapter = new HomeViewPagerAdapter(fragmentManager, fragments);
        viewpager.setAdapter(homeViewPagerAdapter);
        tabLayout.setupWithViewPager(viewpager);

    }

    /**
     * 动态获取指南页面顶部分类数据，并将获取的数据更新到viewpager中
     * @param bean
     */
    @Override
    public void homeBeanDatas(HomeBean bean) {
        List<HomeBean.DataBean.ChannelsBean> channels = bean.getData().getChannels();
        for (int i = 0; i < channels.size(); i++) {
            HomeBean.DataBean.ChannelsBean channelsBean = channels.get(i);
            if (null != channelsBean){
                int id = channelsBean.getId();
                String name = channelsBean.getName();
                if (channelsBean.isEditable()){
                    fragments.add(HomeCommonFrag.newInstance(id, name));
                }else {
                    fragments.add(HomeSelectionFrag.newInstance(id, name));
                }
            }
        }
        homeViewPagerAdapter.notifyDataSetChanged();
    }
}
