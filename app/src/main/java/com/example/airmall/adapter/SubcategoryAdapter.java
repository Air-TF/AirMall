package com.example.airmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airmall.R;
import com.example.airmall.weight.SubcategoryGridView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    private Context context;
    private List<String> categoryList;
    private List<String> subcategoryList;
    private RecyclerView recyclerView;
    private SubcategoryListener listener;

    public SubcategoryAdapter(Context context, List<String> categoryList, List<String> subcategoryList, RecyclerView recyclerView) {
        this.context = context;
        this.categoryList = categoryList;
        this.subcategoryList = subcategoryList;
        this.recyclerView = recyclerView;
    }

    /**
     * 获取被选中的位置，将选中项移动到顶部，并刷新
     *
     * @param selectedPosition
     */
    public void getSelectedPosition(int selectedPosition) {
//        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(selectedPosition, 0);
        recyclerView.smoothScrollToPosition(selectedPosition);
        notifyDataSetChanged();
    }

    /**
     * RecyclerView的点击方法
     *
     * @param listener
     */
    public void setItemClickListener(SubcategoryListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(categoryList.get(position));
        GridViewAdapter adapter = new GridViewAdapter(context, subcategoryList);
        holder.gridView.setAdapter(adapter);
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, subcategoryList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTitleHole;

        RelativeLayout rlWhole;
        SubcategoryGridView gridView;

        public ViewHolder(@NonNull View itemView, final SubcategoryListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTitleHole = itemView.findViewById(R.id.tv_title_whole);
            rlWhole = itemView.findViewById(R.id.rl_whole);
            gridView = itemView.findViewById(R.id.gridView);
            rlWhole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface SubcategoryListener {
        void onItemClick(int position);
    }
}
