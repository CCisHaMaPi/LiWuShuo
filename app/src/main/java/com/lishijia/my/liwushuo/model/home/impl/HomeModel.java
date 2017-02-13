package com.lishijia.my.liwushuo.model.home.impl;


import com.lishijia.my.liwushuo.model.home.IHomeModel;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;
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

public class HomeModel implements IHomeModel {

    private Retrofit retrofit;

    public HomeModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Override
    public void querySelectionList(int pageno, final IHomeModelCallBack callBack) {
        AppHttpService appHttpService = retrofit.create(AppHttpService.class);
        appHttpService.querySelectionBeanDatas().enqueue(new Callback<SelectionBean>() {
            @Override
            public void onResponse(Call<SelectionBean> call, Response<SelectionBean> response) {
                callBack.selectionDatas(response.body());
            }

            @Override
            public void onFailure(Call<SelectionBean> call, Throwable t) {

            }
        });

    }

    @Override
    public void queryBanner(final IHomeModelCallBack callBack) {
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
}
