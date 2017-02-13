package com.lishijia.my.liwushuo.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import com.lishijia.my.liwushuo.R;


import com.lishijia.my.liwushuo.dagger.AppModule;
import com.lishijia.my.liwushuo.dagger.DaggerAppComponent;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;
import com.lishijia.my.liwushuo.presenter.home.IHomePresenter;
import com.lishijia.my.liwushuo.view.adapter.HomeSelectionExpandAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lsj on 2017/2/7.
 */

public class HomeSelectionFrag extends Fragment implements IHomePresenter.IHomePresenterCallBack{

    private Context context;
    @BindView(R.id.home_selection_list_view)
    PullToRefreshExpandableListView refreshListView;
    private ExpandableListView expandableListView;
    private ConvenientBanner convenientBanner;
    private List<SelectionBannerBean.DataBean.BannersBean> banners = new ArrayList<>();
    private Map<String,List<SelectionBean.DataBean.ItemsBean>> datas = new HashMap<>();
    private List<String> keys = new ArrayList<>();
    @Inject
    IHomePresenter homePresenter;
    private HomeSelectionExpandAdapter homeSelectionExpandAdapter;

    public static HomeSelectionFrag newInstance(){
        return new HomeSelectionFrag();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_selection_frag, container, false);
        ButterKnife.bind(this, view);
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build()
                .inject(this);
        initHeaderView();
        setupListView();

        homePresenter.queryBanner();
        homePresenter.querySelectionList(1);

        return view;
    }

    private void setupListView() {
        expandableListView = refreshListView.getRefreshableView();
        homeSelectionExpandAdapter = new HomeSelectionExpandAdapter(keys,datas,context);
        expandableListView.setAdapter(homeSelectionExpandAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    /**
     * 添加头部的banner
     */
    private void initHeaderView(){
        View view = LayoutInflater.from(context).inflate(R.layout.home_selection_header, null);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
        expandableListView = refreshListView.getRefreshableView();
        expandableListView.addHeaderView(view);
        convenientBanner = headerViewHolder.banner;
    }

    class HeaderViewHolder{
        @BindView(R.id.home_selection_list_header)
        ConvenientBanner banner;

        public HeaderViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 配置头部Banner的各项属性
     * @param convenientBanner
     */
    private void setupBanner(ConvenientBanner convenientBanner) {
        convenientBanner.setPages(new CBViewHolderCreator<MyBannerCreater>() {
            @Override
            public MyBannerCreater createHolder() {
                return new MyBannerCreater();
            }
        },banners)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.banner_dot_grey, R.drawable.banner_dot_red})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    class MyBannerCreater implements Holder<SelectionBannerBean.DataBean.BannersBean> {

        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, SelectionBannerBean.DataBean.BannersBean data) {
            Picasso.with(context).load(data.getImage_url()).into(imageView);
        }
    }

    @Override
    public void selectionDatas(SelectionBean bean) {
        List<SelectionBean.DataBean.ItemsBean> itemsBeen = bean.getData().getItems();
        int size = itemsBeen.size();
        for (int i = 0; i < size; i++) {
            SelectionBean.DataBean.ItemsBean itemsBean = itemsBeen.get(i);
            long time = itemsBean.getCreated_at();
            String formatTime = formatTime(time);
            if (!datas.containsKey(formatTime)) {
                keys.add(formatTime);
                datas.put(formatTime,new ArrayList<SelectionBean.DataBean.ItemsBean>());
            }
            datas.get(formatTime).add(itemsBean);

        }
        homeSelectionExpandAdapter.notifyDataSetChanged();
        expandListView();
    }

    private void expandListView() {
        int size = keys.size();
        for (int i = 0; i < size; i++) {
            ExpandableListView refreshableView = refreshListView.getRefreshableView();
            refreshableView.expandGroup(i);
        }
    }

    private String formatTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date(time*1000));
        return format;
    }

    @Override
    public void bannerDatas(SelectionBannerBean bean) {
        banners.addAll(bean.getData().getBanners());
        Log.i("android_LSJ", "bannerDatas: "+ bean.getData().getBanners().get(0).getImage_url());
        setupBanner(convenientBanner);
    }

    @Override
    public void onStart() {
        super.onStart();
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        convenientBanner.stopTurning();
    }
}
