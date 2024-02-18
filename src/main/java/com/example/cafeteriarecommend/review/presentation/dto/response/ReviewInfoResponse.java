package com.example.cafeteriarecommend.review.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfoResponse {
    private String content;
    private String reviewUUID;
    private String memberUUID;
    private String foodUUID;
}
