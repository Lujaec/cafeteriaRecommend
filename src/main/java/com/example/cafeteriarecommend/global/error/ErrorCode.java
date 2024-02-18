package com.example.cafeteriarecommend.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "1404", "해당 회원을 찾을 수 없습니다"),
    FOOD_NOT_FOUND(HttpStatus.NOT_FOUND, "2404", "해당 음식을 찾을 수 없습니다"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "3404", "해당 리뷰를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
