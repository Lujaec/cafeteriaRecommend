package com.example.cafeteriarecommend.food.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodReadRequest {
    @NotEmpty
    private String category;

    @NotEmpty
    private String place;
}
