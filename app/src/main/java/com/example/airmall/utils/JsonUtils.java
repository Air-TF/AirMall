package com.example.airmall.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson() {
        return gson;
    }


    public <T> T fromJson(String json, Class<T> tClass) {

        return gson.fromJson(json, tClass);
    }

    public String toJson(Object object) {

        return gson.toJson(object);
    }
}
