package com.example.airmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.airmall.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> subcategoryList;

    public GridViewAdapter(Context context, List<String> subcategoryList) {
        this.context = context;
        this.subcategoryList = subcategoryList;
    }

    public void setData(List<String> subcategoryList){
        this.subcategoryList = subcategoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return subcategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return subcategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
            holder = new ViewHolder();
            holder.textView = view.findViewById(R.id.tv_small);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(subcategoryList.get(position));
        return view;
    }

    static class ViewHolder {
        TextView textView;
    }

    public void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
//        int numColumns= gridview.getNumColumns();
        int numColumns = 3;
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置参数
        gridview.setLayoutParams(params);
        notifyDataSetChanged();
    }
}
