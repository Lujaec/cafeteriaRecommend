package com.example.cafeteriarecommend.food.presentation.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FoodInfoResponses {
    private List<FoodInfoResponse> foodInfoResponseList;
    public FoodInfoResponses(final List<FoodInfoResponse> list){
        foodInfoResponseList = list;
    }
}
