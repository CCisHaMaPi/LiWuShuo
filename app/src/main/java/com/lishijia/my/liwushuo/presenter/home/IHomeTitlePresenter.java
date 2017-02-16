package com.lishijia.my.liwushuo.presenter.home;

import com.lishijia.my.liwushuo.model.home.bean.HomeBean;

/**
 * Created by TT on 2017/2/16.
 */

public interface IHomeTitlePresenter {

    void queryHomeBean();

    public interface IHomeTitlePresenterCallBack{

        void homeBeanDatas(HomeBean bean);
    }
}
