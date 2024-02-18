package com.example.cafeteriarecommend.review.presentation.dto.response;

import lombok.Getter;
import java.util.List;

@Getter
public class ReviewInfoResponses {
    private List<ReviewInfoResponse> reviewInfoList;
    public ReviewInfoResponses(final List<ReviewInfoResponse> list){
        reviewInfoList = list;
    }
}
