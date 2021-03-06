package com.lishijia.my.liwushuo.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;
import com.lishijia.my.liwushuo.presenter.home.IHomePresenter;
import com.lishijia.my.liwushuo.view.adapter.HomeListAdapter;
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

    public static final String PARAM_TYPE = "TYPE";

    public int pageNo = 1;
    private int pageId;
    private String name;
    private Context context;
    @BindView(R.id.home_selection_list_view)
    PullToRefreshExpandableListView refreshListView;
    private ExpandableListView expandableListView;
    private ConvenientBanner convenientBanner;
    private HomeListAdapter homeListAdapter;
    private List<SelectionBannerBean.DataBean.BannersBean> banners = new ArrayList<>();
    private Map<String,List<HomeListBean.DataBean.ItemsBean>> datas = new HashMap<>();
    private List<String> keys = new ArrayList<>();

    @Inject
    IHomePresenter homePresenter;



    public static HomeSelectionFrag newInstance(int pageId, String name) {
        HomeSelectionFrag homeSelectionFrag = new HomeSelectionFrag();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TYPE,name);
        bundle.putInt(PARAM_TYPE,pageId);
        homeSelectionFrag.setArguments(bundle);
        return homeSelectionFrag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        name = bundle.getString(PARAM_TYPE);
        pageId = bundle.getInt(PARAM_TYPE);
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
        homePresenter.querySelectionList(pageId , pageNo);

        return view;
    }

    private void setupListView() {
        expandableListView = refreshListView.getRefreshableView();
        homeListAdapter = new HomeListAdapter(keys,datas,context);
        expandableListView.setAdapter(homeListAdapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setDivider(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //指南界面下方ListView点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //获取当前item数据源检查是否左上角New标志已显示
                HomeListBean.DataBean.ItemsBean bean = (HomeListBean.DataBean.ItemsBean)
                        parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                HomeListAdapter.ViewHolder viewHolder = (HomeListAdapter.ViewHolder) v.getTag();
                //若数据源不为空,且New标志正在显示,则隐藏
                if (null != bean && !bean.isHidden_cover_image()){
                    viewHolder.imageNew.setImageBitmap(null);
                    bean.setHidden_cover_image(true);
                }
                //跳转到详情界面
                Intent intent = new Intent(context, InfoWebActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url", bean.getUrl());
//                intent.putExtra("精选", bundle);
                intent.putExtra("url", bean.getContent_url());
                startActivity(intent);

                return false;
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
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
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
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
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
    public void selectionDatas(HomeListBean bean) {
        List<HomeListBean.DataBean.ItemsBean> itemsBeen = bean.getData().getItems();
        int size = itemsBeen.size();
        for (int i = 0; i < size; i++) {
            HomeListBean.DataBean.ItemsBean itemsBean = itemsBeen.get(i);
            long time = itemsBean.getCreated_at();
            String formatTime = formatTime(time);
            if (!datas.containsKey(formatTime)) {
                keys.add(formatTime);
                datas.put(formatTime,new ArrayList<HomeListBean.DataBean.ItemsBean>());
            }
            datas.get(formatTime).add(itemsBean);

        }
        homeListAdapter.notifyDataSetChanged();
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
