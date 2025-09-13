package com.example.finalyearproject.common;

public class DishModel {
    private String name;
    private String imageUrl;

    public DishModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
