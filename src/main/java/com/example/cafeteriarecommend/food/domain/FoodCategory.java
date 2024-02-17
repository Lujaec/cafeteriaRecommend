package com.example.cafeteriarecommend.food.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum FoodCategory {
    RICE("rice"),
    NOODLE("noodle"),
    MEET("meet"),
    STEW("stew");

    private String category;

    FoodCategory(String category) {this.category = category;}

    @JsonCreator
    public static FoodCategory from(String category) {
        return FoodCategory.valueOf(category.toUpperCase());
    }

    public String getCategory(){
        return category;
    }
}
