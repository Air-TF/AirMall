package com.example.airmall.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.airmall.R;
import com.example.airmall.adapter.CategoryAdapter;
import com.example.airmall.adapter.SubcategoryAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    RecyclerView subcategoryRecyclerView;

    private RecyclerView.LayoutManager categoryLayoutManager;
    private RecyclerView.LayoutManager subcategoryLayoutManager;
    private CategoryAdapter categoryAdapter;
    private SubcategoryAdapter subcategoryAdapter;
    private List<String> categoryList = new ArrayList<>();
    private List<String> subcategoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryRecyclerView = view.findViewById(R.id.rv_category);
        subcategoryRecyclerView = view.findViewById(R.id.rv_subcategory);

        initData();
        initView();
        return view;
    }

    private void initView() {
        categoryLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        categoryAdapter = new CategoryAdapter(this.getContext(), categoryList);
        //左侧列表的点击事件
        categoryAdapter.setItemClickListener(new CategoryAdapter.CategoryListener() {
            @Override
            public void onItemClick(int position) {
                //向适配器中返回点击的位置，在适配器中进行操作
                categoryAdapter.getSelectedPosition(position);
                subcategoryAdapter.getSelectedPosition(position);
            }
        });
        categoryRecyclerView.setAdapter(categoryAdapter);


        subcategoryLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        subcategoryRecyclerView.setLayoutManager(subcategoryLayoutManager);
        subcategoryAdapter = new SubcategoryAdapter(getActivity(), categoryList, subcategoryList, subcategoryRecyclerView);
        //右侧列表的点击事件
        subcategoryAdapter.setItemClickListener(new SubcategoryAdapter.SubcategoryListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), categoryList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //右侧列表的滚动事件
        subcategoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取右侧列表的第一个可见Item的position
                int TopPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //左侧得到这个position
                categoryAdapter.getSelectedPosition(TopPosition);
            }
        });
        subcategoryRecyclerView.setAdapter(subcategoryAdapter);

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            categoryList.add("商品" + i);
        }
        for (int i = 0; i < 10; i++) {
            subcategoryList.add("标签" + i);
        }
    }

}
