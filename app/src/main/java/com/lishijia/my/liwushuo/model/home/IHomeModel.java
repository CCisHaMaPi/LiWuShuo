package com.lishijia.my.liwushuo.model.home;

import com.lishijia.my.liwushuo.model.home.bean.SelectionBannerBean;
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;

/**
 * Created by lsj on 2017/2/8.
 */

public interface IHomeModel {

    /**
     * 精选列表数据接口访问方法
     * @param pageId
     * @param pageno
     * @param callBack
     */
    void querySelectionList(int pageId, int pageno, IHomeModelCallBack callBack);

    /**
     * 精选列表头部广告借口访问方法
     */
    void queryBanner(final IHomeModelCallBack callBack);

    public interface IHomeModelCallBack{

        void selectionDatas(HomeListBean bean);

        void bannerDatas(SelectionBannerBean bean);
    }
}
