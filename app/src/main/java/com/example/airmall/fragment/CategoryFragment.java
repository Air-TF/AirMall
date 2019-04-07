package com.example.airmall.fragment;


import android.content.Intent;
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
import com.example.airmall.activity.SearchActivity;
import com.example.airmall.adapter.CategoryAdapter;
import com.example.airmall.adapter.GridViewAdapter;
import com.example.airmall.bean.Category;
import com.example.airmall.bean.ResultData;
import com.example.airmall.bean.Subcategory;
import com.example.airmall.network.Impl.CategoryServiceImpl;
import com.example.airmall.network.Impl.RecommendServiceImpl;
import com.example.airmall.utils.JsonUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
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

    private SimpleDraweeView iv_image;
    private TextView tvTitle;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private volatile List<Category> categories;
    private int LeftPosition;
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
        iv_image = view.findViewById(R.id.item_right_image);
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
        categoryAdapter.setItemClickListener(position -> {
            //向适配器中返回点击的位置，在适配器中进行操作
            categoryAdapter.getSelectedPosition(position);
            LeftPosition = position;
            initGridView(position);
        });

        gridView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("subcategoryId", categories.get(LeftPosition).getSubcategoryList().get(i).getId());
            startActivity(intent);
        });
    }

    private void initGridView(int position) {
        tvTitle.setText(categoryList.get(position));
        subcategoryList.clear();
        subcategoryList.addAll(listMap.get(categoryList.get(position)));
        gridViewAdapter.setData(subcategoryList);
        gridViewAdapter.setGridViewHeight(gridView);
        RecommendServiceImpl.getRecommendService().getRecommendByHot(categories.get(position).getId().toString(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String image = data.getString("image");
                    iv_image.setImageURI(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void initData() {
        CategoryServiceImpl.getCategoryService().getCategoryList(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray categoryArray = jsonObject.getJSONArray("data");
                    Category[] fromJson = JsonUtils.getGson().fromJson(categoryArray.toString(), Category[].class);
                    categories = Arrays.asList(fromJson);
                    categoryList.clear();
                    for (Category category : categories) {
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
