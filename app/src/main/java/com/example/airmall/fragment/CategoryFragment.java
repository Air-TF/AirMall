package com.example.airmall.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airmall.R;
import com.example.airmall.adapter.CategoryAdapter;
import com.example.airmall.adapter.GridViewAdapter;
import com.example.airmall.bean.Category;
import com.example.airmall.bean.ResultData;
import com.example.airmall.bean.Subcategory;
import com.example.airmall.network.Impl.CategoryServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private RecyclerView.LayoutManager categoryLayoutManager;
    private CategoryAdapter categoryAdapter;

    private TextView tvTitle;
    private GridView gridView;
    GridViewAdapter gridViewAdapter;
    private Map<String, List<String>> listMap = new HashMap<>();
    private List<String> categoryList = new ArrayList<>();
    private List<String> subcategoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.tv_title);
        gridView = view.findViewById(R.id.gridView);
        categoryRecyclerView = view.findViewById(R.id.rv_category);

        gridViewAdapter = new GridViewAdapter(getActivity(), subcategoryList);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.setGridViewHeight(gridView);

        categoryLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        categoryAdapter = new CategoryAdapter(this.getContext(), categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        //左侧列表的点击事件
        categoryAdapter.setItemClickListener(new CategoryAdapter.CategoryListener() {
            @Override
            public void onItemClick(int position) {
                //向适配器中返回点击的位置，在适配器中进行操作
                categoryAdapter.getSelectedPosition(position);
                initGridView(position);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), subcategoryList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initGridView(int position) {
        tvTitle.setText(categoryList.get(position));
        subcategoryList.clear();
        subcategoryList.addAll(listMap.get(categoryList.get(position)));
        gridViewAdapter.setData(subcategoryList);
        gridViewAdapter.setGridViewHeight(gridView);
    }

    private void initData() {
        CategoryServiceImpl.getCategoryService().getCategoryList(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray categoryArray = jsonObject.getJSONArray("data");
                    Category[] fromJson = new Gson().fromJson(categoryArray.toString(), Category[].class);
                    List<Category> data = Arrays.asList(fromJson);
                    categoryList.clear();
                    for (Category category : data) {
                        categoryList.add(category.getName());
                        List<String> stringList = new ArrayList<>();
                        for (Subcategory subcategory : category.getSubcategoryList()) {
                            stringList.add(subcategory.getName());
                        }
                        listMap.put(category.getName(), stringList);
                    }
                    initGridView(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
