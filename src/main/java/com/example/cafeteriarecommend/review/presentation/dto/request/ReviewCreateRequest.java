package com.example.cafeteriarecommend.review.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
    private String content;
    private Double score;
    private String foodUUID;
}
