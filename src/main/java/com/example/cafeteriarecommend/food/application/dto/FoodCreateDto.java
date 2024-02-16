package com.example.cafeteriarecommend.food.application.dto;

import com.example.cafeteriarecommend.food.domain.FoodCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FoodCreateDto {
    private MultipartFile coverImage;
    private String name;
    private Integer price;
    private String place;
    private FoodCategory category;
    private String foodUUID;
}
