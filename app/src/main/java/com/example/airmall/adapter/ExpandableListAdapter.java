package com.example.airmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.bean.Item;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Item item;

    public ExpandableListAdapter(Context context, Item item) {
        this.context = context;
        this.item = item;
    }

    public void setItem(Item item) {
        this.item = item;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (item == null) return 0;
        return item.getParamCategoryList().size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (item == null) return 0;
        return item.getParamCategoryList().get(i).getParamDescList().size();
    }

    @Override
    public Object getGroup(int i) {
        return item.getParamCategoryList().get(i);
    }

    @Override
    public Object getChild(int i, int j) {
        return item.getParamCategoryList().get(i).getParamDescList().get(j);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
     *
     * @param i
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderGroup groupHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.item_detail_group, viewGroup, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = view.findViewById(R.id.tv_group_name);
            view.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolderGroup) view.getTag();
        }
        groupHolder.tv_group_name.setText(item.getParamCategoryList().get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int j, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderItem itemHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.item_detail_item, viewGroup, false);
            itemHolder = new ViewHolderItem();
            itemHolder.tv_name = view.findViewById(R.id.tv_name);
            itemHolder.tv_value = view.findViewById(R.id.tv_value);
            view.setTag(itemHolder);
        } else {
            itemHolder = (ViewHolderItem) view.getTag();
        }
        itemHolder.tv_name.setText(item.getParamCategoryList().get(i).getParamDescList().get(j).getName());
        itemHolder.tv_value.setText(item.getParamCategoryList().get(i).getParamDescList().get(j).getDescribe());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private static class ViewHolderGroup {
        private TextView tv_group_name;
    }

    private static class ViewHolderItem {
        private TextView tv_name;
        private TextView tv_value;
    }
}
