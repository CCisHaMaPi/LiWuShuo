package com.lishijia.my.liwushuo.presenter.home;

import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;

/**
 * Created by lsj on 2017/2/8.
 */

public interface IHomePresenter {

    void querySelectionList(int pageId, int pageno);

    void queryBanner();



    public interface IHomePresenterCallBack{

        void selectionDatas(HomeListBean bean);

        void bannerDatas(SelectionBannerBean bean);

    }
}
