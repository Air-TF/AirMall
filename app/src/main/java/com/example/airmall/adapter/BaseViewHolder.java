package com.example.airmall.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> views;

    private BaseRecyclerAdapter.OnItemClickListener itemClickListener;

    public BaseViewHolder(@NonNull View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);

        this.itemClickListener = itemClickListener;
        this.views = new SparseArray<>();
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }

    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    public View getView(int viewId) {
        return retrieveView(viewId);
    }

    protected  <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T)view;
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view,getLayoutPosition());
        }
    }
}