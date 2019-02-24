package com.example.airmall.adapter;

import android.content.Context;

import java.util.List;

public abstract class SimpleAdapter<T> extends BaseRecyclerAdapter<T, BaseViewHolder> {

    public SimpleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public SimpleAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }
}
