package com.lishijia.my.liwushuo.model.home.impl;


import com.lishijia.my.liwushuo.model.home.IHomeModel;
import com.lishijia.my.liwushuo.model.home.IHomeTitleModel;
import com.lishijia.my.liwushuo.model.home.bean.HomeBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;
import com.lishijia.my.liwushuo.utils.AppHttpService;
import com.lishijia.my.liwushuo.utils.UrlConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lsj on 2017/2/8.
 */

public class HomeModel implements IHomeModel , IHomeTitleModel {

    private Retrofit retrofit;

    public HomeModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Override
    public void querySelectionList(int pageId, int pageno, final IHomeModel.IHomeModelCallBack callBack) {
        AppHttpService appHttpService = retrofit.create(AppHttpService.class);
        appHttpService.querySelectionBeanDatas(pageId).enqueue(new Callback<HomeListBean>() {
            @Override
            public void onResponse(Call<HomeListBean> call, Response<HomeListBean> response) {
                callBack.selectionDatas(response.body());
            }

            @Override
            public void onFailure(Call<HomeListBean> call, Throwable t) {

            }
        });

    }

    @Override
    public void queryBanner(final IHomeModel.IHomeModelCallBack callBack) {
        AppHttpService appHttpService = retrofit.create(AppHttpService.class);

        appHttpService.querySelectionBannerDatas()
                .map(new Func1<SelectionBannerBean, SelectionBannerBean>() {
                    @Override
                    public SelectionBannerBean call(SelectionBannerBean bean) {
                        return bean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SelectionBannerBean>() {
                    @Override
                    public void call(SelectionBannerBean bean) {
                        callBack.bannerDatas(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                    }
                });
    }

    @Override
    public void queryHomeBean(final IHomeTitleModel.IHomeModelCallBack callBack) {

        AppHttpService appHttpService = retrofit.create(AppHttpService.class);
        appHttpService.queryHomeBeanDates().enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                callBack.homeBeanDatas(response.body());
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }

}
