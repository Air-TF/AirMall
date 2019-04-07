package com.example.airmall.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("user/signIn")
    Call<ResponseBody> signIn(@Query("account") String account,@Query("password") String password);

    @POST("user/signUp")
    Call<ResponseBody> signUp(@Query("account") String account,@Query("password") String password);

}
