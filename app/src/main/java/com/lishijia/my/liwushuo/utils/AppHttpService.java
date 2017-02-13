package com.lishijia.my.liwushuo.utils;


import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by my on 2017/2/9.
 */

public interface AppHttpService {

    @GET("v2/banners")
    Observable<SelectionBannerBean> querySelectionBannerDatas();

    @GET("v2/channels/101/items?ad=2&gender=1&generation=2&limit=20&offset=0")
    Call<SelectionBean> querySelectionBeanDatas();
}
