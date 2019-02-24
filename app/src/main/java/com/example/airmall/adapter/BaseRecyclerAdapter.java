package com.example.airmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context context;
    protected List<T> data;
    protected final int layoutResId;
    private OnItemClickListener itemClickListener = null;

    public BaseRecyclerAdapter(Context context, int layoutResId) {
        this.context = context;
        this.data = new ArrayList<T>();
        this.layoutResId = layoutResId;
    }


    public BaseRecyclerAdapter(Context context, int layoutResId, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : data;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        T item = getItem(position);
        convert((H) holder, item);
    }

    protected abstract void convert(H viewHolder, T item);

    private T getItem(int position) {
        if (position >= data.size()) return null;
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() <= 0) return 0;
        return data.size();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * 下拉刷新，清除原有数据，添加新数据
     *
     * @param newData
     */
    public void refreshData(List<T> newData) {
        data.clear();
        data.addAll(newData);
//        notifyItemRangeChanged(0, data.size());
        notifyDataSetChanged();
    }

    /**
     * 在原来数据的末尾追加新数据
     *
     * @param moreData
     */
    public void loadMoreData(List<T> moreData) {
        int lastPosition = data.size();
        data.addAll(lastPosition, moreData);
        notifyItemRangeInserted(lastPosition, moreData.size());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;

    }
}
