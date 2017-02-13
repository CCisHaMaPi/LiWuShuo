package com.lishijia.my.liwushuo.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lishijia.my.liwushuo.R;

/**
 * Created by my on 2017/2/10.
 */

public class HomeCommonFrag extends Fragment{

    public static final String PARAM_TYPE = "TYPE";
    private Context context;
    private String name;

    public static HomeCommonFrag newInstance(String name) {
        HomeCommonFrag homeCommonFrag = new HomeCommonFrag();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TYPE,name);
        homeCommonFrag.setArguments(bundle);
        return homeCommonFrag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_common_frag, container, false);
        TextView textView = (TextView) view.findViewById(R.id.common_text);
        textView.setText(name);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        name = bundle.getString(PARAM_TYPE);
    }
}
