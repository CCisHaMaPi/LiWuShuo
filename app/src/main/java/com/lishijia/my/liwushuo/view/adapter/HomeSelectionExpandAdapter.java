package com.lishijia.my.liwushuo.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lishijia.my.liwushuo.R;
import com.lishijia.my.liwushuo.model.home.bean.SelectionBean;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by lsj on 2017/2/12.
 */

public class HomeSelectionExpandAdapter extends BaseExpandableListAdapter{

    private List<String> keys;
    private Map<String,List<SelectionBean.DataBean.ItemsBean>> datas;
    private Context context;
    private LayoutInflater inflater;

    public HomeSelectionExpandAdapter(List<String> keys, Map<String, List<SelectionBean.DataBean.ItemsBean>> datas, Context context) {
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
        View view = convertView;
        final ViewHolder holder;
        if (null == view){
            view = inflater.inflate(R.layout.item_home, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.textNum.setText("");
        }
        List<SelectionBean.DataBean.ItemsBean> itemsBeens = datas.get(keys.get(groupPosition));
        final SelectionBean.DataBean.ItemsBean bean = itemsBeens.get(childPosition);

        Picasso.with(context).load(bean.getCover_image_url()).into(holder.imageView);
        holder.textNum.setText(String.valueOf(bean.getLikes_count()));
        holder.textTitle.setText(bean.getTitle());
        if (bean.isHidden_cover_image()){
            holder.imageNew.setImageBitmap(null);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setHidden_cover_image(true);
                holder.imageNew.setImageBitmap(null);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class ViewHolder{
        @BindView(R.id.item_home_image_view)
        ImageView imageView;
        @BindView(R.id.item_home_new)
        ImageView imageNew;
        @BindView(R.id.item_home_text_favorite)
        TextView textNum;
        @BindView(R.id.item_home_text_title)
        TextView textTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
