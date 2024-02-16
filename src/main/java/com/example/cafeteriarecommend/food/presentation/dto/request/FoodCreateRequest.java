package com.example.cafeteriarecommend.food.presentation.dto.request;

import com.example.cafeteriarecommend.food.domain.FoodCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FoodCreateRequest {
    private MultipartFile coverImage;
    @NotEmpty
    private String name;
    @NotNull
    private Integer price;
    private String place;
    @NotNull
    private FoodCategory category;
}
