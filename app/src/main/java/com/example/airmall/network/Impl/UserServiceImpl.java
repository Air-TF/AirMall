package com.example.airmall.network.Impl;

import com.example.airmall.network.UserService;
import com.example.airmall.utils.HttpUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UserServiceImpl {
    private volatile static UserServiceImpl userService;

    public UserService getService() {
        return service;
    }

    private UserService service = HttpUtils.initService(UserService.class);

    public static UserServiceImpl getUserService() {
        if (userService == null) {
            synchronized (UserServiceImpl.class) {
                if (userService == null) {
                    userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    public void signIn(String account, String password, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.signIn(account, password);
        call.enqueue(callback);
    }

    public void signUp(String account, String password, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.signUp(account, password);
        call.enqueue(callback);
    }

    public void listStar(int page, int size, String userId, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.listStar(page, size, userId);
        call.enqueue(callback);
    }

    public void setFavorite(String itemId, String userId, boolean star, boolean favorite, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.setFavorite(itemId, userId, star, favorite);
        call.enqueue(callback);
    }
}
