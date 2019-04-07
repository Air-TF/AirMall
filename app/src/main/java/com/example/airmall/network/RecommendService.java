package com.example.airmall.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecommendService {

    @GET("recommend/item/{id}")
    Call<ResponseBody> listRecommendByItem(@Path("id") String id);

    @GET("recommend/user/{id}")
    Call<ResponseBody> listRecommendByUser(@Path("id") String id);

    @GET("recommend/category/{id}")
    Call<ResponseBody> getRecommendByHot(@Path("id") String id);

    @GET("recommend/category")
    Call<ResponseBody> listRecommendByHot();
}
