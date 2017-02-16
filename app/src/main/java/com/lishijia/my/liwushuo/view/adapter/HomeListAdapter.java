package com.lishijia.my.liwushuo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lishijia.my.liwushuo.R;
import com.lishijia.my.liwushuo.model.home.bean.HomeListBean;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TT on 2017/2/17.
 */

public class HomeListAdapter extends BaseExpandableListAdapter {


    private List<String> keys;
    private Map<String,List<HomeListBean.DataBean.ItemsBean>> datas;
    private Context context;
    private LayoutInflater inflater;

    public HomeListAdapter(List<String> keys, Map<String, List<HomeListBean.DataBean.ItemsBean>> datas, Context context) {
        this.keys = keys;
        this.datas = datas;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return keys == null ? 0 : keys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<HomeListBean.DataBean.ItemsBean> itemsBeen = datas.get(keys.get(groupPosition));
        return itemsBeen == null ? 0 : itemsBeen.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<HomeListBean.DataBean.ItemsBean> itemsBeen = datas.get(keys.get(groupPosition));
        HomeListBean.DataBean.ItemsBean bean = itemsBeen.get(childPosition);
        if (null != bean){
            return bean;
        }
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
        View view = convertView;
        if (null == view){
            view = inflater.inflate(R.layout.item_selection_group, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.item_selection_group_title);
        String title = keys.get(groupPosition);
        if (null != title){
            textView.setText(title);
        }else {
            textView.setText("数据混乱请尝试重新加载");
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (null == view){
            view = inflater.inflate(R.layout.item_home, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
            holder.imageView.setImageResource(R.drawable.ic_tab_category_normal);
            holder.textNum.setText("");
        }
        List<HomeListBean.DataBean.ItemsBean> itemsBeens = datas.get(keys.get(groupPosition));
        final HomeListBean.DataBean.ItemsBean bean = itemsBeens.get(childPosition);

        if (null != bean){
            Picasso.with(context).load(bean.getCover_image_url()).into(holder.imageView);
            holder.textNum.setText(String.valueOf(bean.getLikes_count()));
            holder.textTitle.setText(bean.getTitle());
            if (bean.isHidden_cover_image()){
                holder.imageNew.setImageBitmap(null);
            }
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public class ViewHolder{
        @BindView(R.id.item_home_image_view)
        public ImageView imageView;
        @BindView(R.id.item_home_new)
        public ImageView imageNew;
        @BindView(R.id.item_home_text_favorite)
        TextView textNum;
        @BindView(R.id.item_home_text_title)
        TextView textTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
