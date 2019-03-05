package com.example.airmall.bean;

import java.util.List;

public class Item {
    private String id;

    private String name;

    private String title;

    private String image;

    private String price;

    private List<ParamCategory> paramCategoryList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return String.format("¥%.2f", Double.valueOf(price) / 100);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<ParamCategory> getParamCategoryList() {
        return paramCategoryList;
    }

    public void setParamCategoryList(List<ParamCategory> paramCategoryList) {
        this.paramCategoryList = paramCategoryList;
    }
}
