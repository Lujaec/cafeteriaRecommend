package com.example.cafeteriarecommend.review.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDto {
    private String content;
    private Double score;
    private String foodUUID;
    private String memberUUID;
}
