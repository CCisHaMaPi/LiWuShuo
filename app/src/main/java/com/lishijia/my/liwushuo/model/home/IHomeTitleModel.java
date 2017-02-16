package com.lishijia.my.liwushuo.model.home;

import com.lishijia.my.liwushuo.model.home.bean.HomeBean;

/**
 * Created by TT on 2017/2/16.
 */

public interface IHomeTitleModel {

    /**
     * 指南界面分类查询
     * @param callBack
     */
    void queryHomeBean(final IHomeTitleModel.IHomeModelCallBack callBack);

    public interface IHomeModelCallBack{

        void homeBeanDatas(HomeBean bean);
    }
}
