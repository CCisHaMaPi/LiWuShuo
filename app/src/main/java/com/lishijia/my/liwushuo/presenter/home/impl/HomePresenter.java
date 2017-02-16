package com.lishijia.my.liwushuo.presenter.home.impl;

import com.lishijia.my.liwushuo.model.home.IHomeModel;
import com.lishijia.my.liwushuo.model.home.IHomeTitleModel;
import com.lishijia.my.liwushuo.model.home.bean.HomeBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;
import com.lishijia.my.liwushuo.presenter.home.IHomePresenter;
import com.lishijia.my.liwushuo.presenter.home.IHomeTitlePresenter;

import javax.inject.Inject;

/**
 * Created by lsj on 2017/2/8.
 */

public class HomePresenter implements IHomePresenter,IHomeTitlePresenter,IHomeTitleModel.IHomeModelCallBack, IHomeModel.IHomeModelCallBack{

    private IHomeModel homeModel;
    private IHomeTitleModel homeTitleModel;
    @Inject
    IHomeTitlePresenterCallBack titleCallback;
    @Inject
    IHomePresenterCallBack callback;

    public HomePresenter(IHomePresenterCallBack callBack, IHomeModel model){
        this.callback = callBack;
        this.homeModel = model;
    }

    public HomePresenter(IHomeTitlePresenterCallBack callBack, IHomeTitleModel model){
        this.titleCallback = callBack;
        this.homeTitleModel = model;
    }

    @Override
    public void querySelectionList(int pageId, int pageno) {
        homeModel.querySelectionList(pageId, pageno, this);
    }

    @Override
    public void queryBanner() {
        homeModel.queryBanner(this);
    }

    @Override
    public void queryHomeBean() {
        homeTitleModel.queryHomeBean(this);
    }

    @Override
    public void selectionDatas(HomeListBean bean) {
        callback.selectionDatas(bean);
    }

    @Override
    public void bannerDatas(SelectionBannerBean bean) {
        callback.bannerDatas(bean);
    }

    @Override
    public void homeBeanDatas(HomeBean bean) {
        titleCallback.homeBeanDatas(bean);
    }
}
