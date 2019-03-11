package com.example.airmall.network.Impl;

import com.example.airmall.utils.HttpUtils;
import com.example.airmall.network.ItemService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ItemServiceImpl {
    private volatile static ItemServiceImpl itemService;
    private ItemService service = HttpUtils.initService(ItemService.class);

    public static ItemServiceImpl getItemService() {
        if (itemService == null) {
            synchronized (ItemServiceImpl.class) {
                if (itemService == null) {
                    itemService = new ItemServiceImpl();
                }
            }
        }
        return itemService;
    }

    public void getItemListByKeyword(Integer page, Integer size, String keyword, String subcategoryId, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.getItemListByKeyword(keyword, page, subcategoryId);
        call.enqueue(callback);
    }

    public void getItem(String id,String userId, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.getItem(id,userId);
        call.enqueue(callback);
    }
}
