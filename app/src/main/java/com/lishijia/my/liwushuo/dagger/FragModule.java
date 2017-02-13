package com.lishijia.my.liwushuo.dagger;

import com.lishijia.my.liwushuo.view.clazz.ClazzFrag;
import com.lishijia.my.liwushuo.view.home.HomeFrag;
import com.lishijia.my.liwushuo.view.hot.HotFrag;
import com.lishijia.my.liwushuo.view.mine.MineFrag;

import dagger.Module;
import dagger.Provides;

/**
 * Created by my on 2017/2/10.
 */
@Module
public class FragModule {


    @Provides
    public HomeFrag provideHomeFrag(){
        return new HomeFrag();
    }

    @Provides
    public HotFrag provideHotFrag(){
        return new HotFrag();
    }

    @Provides
    public ClazzFrag provideClazzFrag(){
        return new ClazzFrag();
    }

    @Provides
    public MineFrag provideMineFrag(){
        return new MineFrag();
    }
}
