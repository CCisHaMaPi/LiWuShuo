package com.lishijia.my.liwushuo.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by lsj on 2017/2/12.
 */

public class HomeSelectionExpandAdapter extends BaseExpandableListAdapter{

    private List<String> keys;
    private Map<String,List<SelectionBean.DataBean.ItemsBean>> datas;
    private Context context;

    public HomeSelectionExpandAdapter(List<String> keys, Map<String, List<SelectionBean.DataBean.ItemsBean>> datas, Context context) {
        this.keys = keys;
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return keys == null ? 0 : keys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<SelectionBean.DataBean.ItemsBean> itemsBeen = datas.get(keys.get(groupPosition));
        return itemsBeen == null ? 0 : itemsBeen.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView = (TextView) convertView;
        if (textView == null) {
            textView = new TextView(context);
        }
        textView.setText("["+keys.get(groupPosition)+"]");
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = (TextView) convertView;
        if (textView == null) {
            textView = new TextView(context);
        }
        List<SelectionBean.DataBean.ItemsBean> itemsList= datas.get(keys.get(groupPosition));
        SelectionBean.DataBean.ItemsBean itemsBean = itemsList.get(childPosition);

        textView.setText(itemsBean.getTitle());
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
