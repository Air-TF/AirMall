package com.example.airmall.bean;

public class Item {
    private String name;
    private String title;
    private String image;
    private String price;

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
}
