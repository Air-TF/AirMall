package com.example.airmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
            holder = new ViewHolder();
            holder.textView = view.findViewById(R.id.tv_small);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(subcategoryList.get(position));
        return view;
    }
    class ViewHolder {
        TextView textView;
    }
}
