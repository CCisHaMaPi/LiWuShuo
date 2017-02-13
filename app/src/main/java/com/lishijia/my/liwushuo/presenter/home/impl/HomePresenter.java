package com.lishijia.my.liwushuo.presenter.home.impl;

import android.util.Log;

import com.lishijia.my.liwushuo.model.home.IHomeModel;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;
import com.lishijia.my.liwushuo.presenter.home.IHomePresenter;

import javax.inject.Inject;

/**
 * Created by lsj on 2017/2/8.
 */

public class HomePresenter implements IHomePresenter, IHomeModel.IHomeModelCallBack{

    private IHomeModel homeModel;
    @Inject
    IHomePresenterCallBack callback;

    public HomePresenter(IHomePresenterCallBack callBack, IHomeModel model){
        this.callback = callBack;
        this.homeModel = model;
    }

    @Override
    public void querySelectionList(int pageno) {
        homeModel.querySelectionList(pageno, this);
    }

    @Override
    public void queryBanner() {
        homeModel.queryBanner(this);
        Log.i("android_LSJ", "posenter: queryBanner ");
    }

    @Override
    public void selectionDatas(SelectionBean bean) {
        callback.selectionDatas(bean);
    }

    @Override
    public void bannerDatas(SelectionBannerBean bean) {
        callback.bannerDatas(bean);
        Log.i("android_LSJ", "posenter: callback "+ bean.toString());
    }
}
