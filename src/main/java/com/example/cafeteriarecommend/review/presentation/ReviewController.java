package com.example.cafeteriarecommend.review.presentation;

import com.example.cafeteriarecommend.auth.presentation.AuthenticationPrincipal;
import com.example.cafeteriarecommend.member.domain.Member;
import com.example.cafeteriarecommend.review.application.ReviewService;
import com.example.cafeteriarecommend.review.application.dto.ReviewCreateDto;
import com.example.cafeteriarecommend.review.presentation.dto.request.ReviewCreateRequest;
import com.example.cafeteriarecommend.review.presentation.dto.response.ReviewInfoResponse;
import com.example.cafeteriarecommend.review.presentation.dto.response.ReviewInfoResponses;
import com.example.cafeteriarecommend.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ModelMapper mapper;

    @PostMapping()
    ResponseEntity<ApiResponse<ReviewInfoResponse>> create (
            @AuthenticationPrincipal Member member,
            @RequestBody ReviewCreateRequest request
    ){
        String memberUUID = member.getMemberUUID();

        ReviewCreateDto dto = mapper.map(request, ReviewCreateDto.class);
        dto.setMemberUUID(memberUUID);

        ReviewInfoResponse reviewInfoResponse = reviewService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(reviewInfoResponse));
    }

    @GetMapping("/{foodUUID}")
    ResponseEntity<ApiResponse<ReviewInfoResponses>> findAllByFoodUUID(
            @PathVariable String foodUUID
    ){
        ReviewInfoResponses reviewInfoResponses = reviewService.findAllByFoodUUID(foodUUID);
        return ResponseEntity.ok(ApiResponse.success(reviewInfoResponses));
    }
}
