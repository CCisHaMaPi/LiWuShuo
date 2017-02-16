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

import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.lishijia.my.liwushuo.R;
import com.lishijia.my.liwushuo.dagger.AppModule;
import com.lishijia.my.liwushuo.dagger.DaggerAppComponent;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;
import com.lishijia.my.liwushuo.presenter.home.IHomePresenter;
import com.lishijia.my.liwushuo.view.adapter.HomeListAdapter;

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
 * Created by lsj on 2017/2/10.
 */

public class HomeCommonFrag extends Fragment implements IHomePresenter.IHomePresenterCallBack{

    public static final String PARAM_TYPE = "TYPE";
    private int pageNo = 1;

    private Context context;
    private String name;
    private int pageId;

    @BindView(R.id.home_selection_list_view)
    PullToRefreshExpandableListView refreshListView;
    private ExpandableListView expandableListView;
    private HomeListAdapter homeListAdapter;

    private Map<String,List<HomeListBean.DataBean.ItemsBean>> datas = new HashMap<>();
    private List<String> keys = new ArrayList<>();

    @Inject
    IHomePresenter homePresenter;

    public static HomeCommonFrag newInstance(int pageId, String name) {
        HomeCommonFrag homeCommonFrag = new HomeCommonFrag();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TYPE,name);
        bundle.putInt(PARAM_TYPE,pageId);
        homeCommonFrag.setArguments(bundle);
        return homeCommonFrag;
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


    /**
     * 其他页面与精选页面的格式大同小异，复用除了头部视图外的代码实现功能，重复部分很多，有待优化。
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_selection_frag, container, false);
        ButterKnife.bind(this,view);
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build()
                .inject(this);
        setupListView();
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

    }
}
