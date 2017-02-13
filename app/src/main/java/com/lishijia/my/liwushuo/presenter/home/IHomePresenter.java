package com.lishijia.my.liwushuo.presenter.home;

import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;

/**
 * Created by lsj on 2017/2/8.
 */

public interface IHomePresenter {

    void querySelectionList(int pageno);

    void queryBanner();

    public interface IHomePresenterCallBack{

        void selectionDatas(SelectionBean bean);

        void bannerDatas(SelectionBannerBean bean);
    }
}
