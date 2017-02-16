package com.lishijia.my.liwushuo.dagger;


import com.lishijia.my.liwushuo.MainActivity;
import com.lishijia.my.liwushuo.view.home.HomeCommonFrag;
import com.lishijia.my.liwushuo.view.home.HomeFrag;
import com.lishijia.my.liwushuo.view.home.HomeSelectionFrag;

import dagger.Component;

/**
 * Created by lsj on 2017/2/8.
 */
@Component(modules = {AppModule.class, FragModule.class})
public interface AppComponent {

    void inject(HomeSelectionFrag frag);

    void inject(MainActivity activity);

    void inject(HomeFrag frag);

    void inject(HomeCommonFrag frag);
}

