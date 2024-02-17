package com.example.cafeteriarecommend.food.presentation.dto.response;

import com.example.cafeteriarecommend.food.domain.FoodCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodInfoResponse {
    private String imageUrl;
    private String name;
    private String price;
    private String place;
    private FoodCategory category;
    private Double rating;
    private String foodUUID;
    private Integer reviewCnt;
}
