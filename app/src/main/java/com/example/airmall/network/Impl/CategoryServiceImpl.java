package com.example.airmall.network.Impl;

import com.example.airmall.network.CategoryService;
import com.example.airmall.network.HttpUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CategoryServiceImpl {
    private volatile static CategoryServiceImpl categoryService;
    private CategoryService service = HttpUtils.initService(CategoryService.class);

    public static CategoryServiceImpl getCategoryService(){
        if (categoryService == null) {
            synchronized (CategoryServiceImpl.class) {
                if (categoryService == null) {
                    categoryService = new CategoryServiceImpl();
                }
            }
        }
        return categoryService;
    }


    public void getCategoryList(Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.getCategoryList();
        call.enqueue(callback);
    }

}
