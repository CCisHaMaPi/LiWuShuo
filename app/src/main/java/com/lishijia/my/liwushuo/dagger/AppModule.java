package com.lishijia.my.liwushuo.dagger;

import com.lishijia.my.liwushuo.model.home.IHomeModel;
import com.lishijia.my.liwushuo.model.home.impl.HomeModel;
import com.lishijia.my.liwushuo.presenter.home.IHomePresenter;
import com.lishijia.my.liwushuo.presenter.home.impl.HomePresenter;
import com.lishijia.my.liwushuo.view.clazz.ClazzFrag;
import com.lishijia.my.liwushuo.view.home.HomeFrag;
import com.lishijia.my.liwushuo.view.hot.HotFrag;
import com.lishijia.my.liwushuo.view.mine.MineFrag;

import dagger.Module;
import dagger.Provides;

/**
 * Created by my on 2017/2/8.
 */
@Module
public class AppModule {

    private IHomePresenter.IHomePresenterCallBack callback;

    public AppModule(){

    }

    public AppModule(IHomePresenter.IHomePresenterCallBack callback) {
        this.callback = callback;
    }

    @Provides
    public IHomeModel provideHomeModel() {
        return new HomeModel();
    }
    @Provides
    public IHomePresenter provideHomePresenter(IHomeModel model) {
        return new HomePresenter(callback,model);
    }
}
