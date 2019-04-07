package com.example.airmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airmall.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<String> categoryList;
    private CategoryListener listener;
    private int selectedPosition;

    public CategoryAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    /**
     * 获取被选中的位置，将选中项移动到中间，并刷新
     *
     * @param selectedPosition
     */
    public void getSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    /**
     * 获取listener,将listener传入ViewHolder中
     *
     * @param listener
     */
    public void setItemClickListener(CategoryListener listener) {
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(categoryList.get(position));
        if (position == selectedPosition) {
            holder.tvName.setBackgroundResource(R.color.color_107);
            holder.view.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_002));
        } else {
            holder.view.setVisibility(View.GONE);
            holder.tvName.setBackgroundResource(R.color.color_109);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_100));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private View view;

        public ViewHolder(@NonNull View itemView, final CategoryListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_category);
            view = itemView.findViewById(R.id.view);
            itemView.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }
    }

    public interface CategoryListener {
        void onItemClick(int position);
    }
}
