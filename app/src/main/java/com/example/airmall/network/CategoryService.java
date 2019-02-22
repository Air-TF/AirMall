package com.example.airmall.network;

import com.example.airmall.bean.ResultData;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("category")
    Call<ResponseBody> getCategoryList();
}
