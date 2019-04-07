package com.example.airmall.network.Impl;

import com.example.airmall.network.RecommendService;
import com.example.airmall.utils.HttpUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RecommendServiceImpl {
    private volatile static RecommendServiceImpl recommendService;
    private RecommendService service = HttpUtils.initService(RecommendService.class);

    public static RecommendServiceImpl getRecommendService(){
        if (recommendService == null){
            synchronized (RecommendService.class){
                if (recommendService == null){
                    recommendService = new RecommendServiceImpl();
                }
            }
        }
        return recommendService;
    }

    public void listRecommendByItem(String id, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.listRecommendByItem(id);
        call.enqueue(callback);
    }

    public void listRecommendByUser(String userId, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.listRecommendByUser(userId);
        call.enqueue(callback);
    }

    public void getRecommendByHot(String CategoryId, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.getRecommendByHot(CategoryId);
        call.enqueue(callback);
    }

    public void listRecommendByHot(Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.listRecommendByHot();
        call.enqueue(callback);
    }
}
