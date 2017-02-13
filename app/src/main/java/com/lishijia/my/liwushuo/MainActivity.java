package com.lishijia.my.liwushuo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.lishijia.my.liwushuo.dagger.AppModule;
import com.lishijia.my.liwushuo.dagger.DaggerAppComponent;
import com.lishijia.my.liwushuo.dagger.FragModule;
import com.lishijia.my.liwushuo.view.clazz.ClazzFrag;
import com.lishijia.my.liwushuo.view.home.HomeFrag;
import com.lishijia.my.liwushuo.view.hot.HotFrag;
import com.lishijia.my.liwushuo.view.mine.MineFrag;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Fragment defaultFrag;
    private FragmentManager fragmentManager;

    @Inject
    HomeFrag homeFrag;
    @Inject
    HotFrag hotFrag;
    @Inject
    ClazzFrag clazzFrag;
    @Inject
    MineFrag mineFrag;
    @BindView(R.id.home_radio_group)
    RadioGroup radioGroup ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build()
                .inject(this);
        initView();
        initFragments();
    }

    private void initView(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_radio_btn_guide:
                        switchFragment(homeFrag);
                        break;
                    case R.id.home_radio_btn_hot:
                        switchFragment(hotFrag);
                        break;
                    case R.id.home_radio_btn_class:
                        switchFragment(clazzFrag);
                        break;
                    case R.id.home_radio_btn_mine:
                        switchFragment(mineFrag);
                        break;
                }
            }
        });
    }

    private void initFragments(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.home_fragment,homeFrag)
                .commit();
        defaultFrag = homeFrag;
    }

    public void switchFragment(Fragment newFrag){
        if(newFrag != defaultFrag){
            //判断新的Fragment是否已添加
            if(newFrag.isAdded()){
                //已经添加，隐藏原来的Fragment，显示新的Fragment
                fragmentManager.beginTransaction()
                        .hide(defaultFrag)
                        .show(newFrag)
                        .commit();
            }else{
                //没有添加，隐藏原来的Fragment，添加并且显示新的Fragment
                fragmentManager.beginTransaction()
                        .hide(defaultFrag)
                        .add(R.id.home_fragment,newFrag)
                        .commit();
            }
            //设置新的Fragment为当前的默认Frag
            defaultFrag = newFrag;
        }
    }
}
