package com.example.airmall.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {

    @GET("item/search/{keyword}")
    Call<ResponseBody> getItemListByKeyword(@Path("keyword") String keyword,@Query("page")Integer page);
}
