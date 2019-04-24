package com.example.airmall.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("user/{id}")
    Call<ResponseBody> getUser(@Path("id") String id);

    @GET("user/signIn")
    Call<ResponseBody> signIn(@Query("account") String account, @Query("password") String password);

    @POST("user/signUp")
    Call<ResponseBody> signUp(@Query("account") String account, @Query("password") String password);

    @GET("user/star")
    Call<ResponseBody> listStar(@Query("page") int page, @Query("size") int size, @Query("userId") String userId);

    @GET("user/favorite")
    Call<ResponseBody> setFavorite(@Query("itemId") String itemId, @Query("userId") String userId, @Query("star") boolean star, @Query("favorite") boolean favorite);
}
