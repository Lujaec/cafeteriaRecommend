package com.example.cafeteriarecommend.review.application;

import com.example.cafeteriarecommend.food.application.FoodService;
import com.example.cafeteriarecommend.food.domain.Food;
import com.example.cafeteriarecommend.food.domain.FoodRepository;
import com.example.cafeteriarecommend.food.presentation.dto.response.FoodInfoResponse;
import com.example.cafeteriarecommend.global.error.ErrorCode;
import com.example.cafeteriarecommend.global.exception.CustomException;
import com.example.cafeteriarecommend.member.domain.Member;
import com.example.cafeteriarecommend.member.domain.MemberRepository;
import com.example.cafeteriarecommend.review.application.dto.ReviewCreateDto;
import com.example.cafeteriarecommend.review.domain.Review;
import com.example.cafeteriarecommend.review.domain.ReviewRepository;
import com.example.cafeteriarecommend.review.presentation.dto.response.ReviewInfoResponse;
import com.example.cafeteriarecommend.review.presentation.dto.response.ReviewInfoResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    public ReviewInfoResponse create(ReviewCreateDto dto) {
        Review review = mapper.map(dto, Review.class);
        review.setReviewUUID(UUID.randomUUID().toString());

        final String memberUUID = dto.getMemberUUID();
        final String foodUUID = dto.getFoodUUID();

        Optional<Food> foodOptional = foodRepository.findByFoodUUID(foodUUID);
        if(foodOptional.isEmpty()){
            log.info("foodUUID = {}인 음식은 존재하지 않습니다.", foodUUID);
            throw new CustomException(ErrorCode.FOOD_NOT_FOUND);
        }

        Optional<Member> memberOptional = memberRepository.findByMemberUUID(memberUUID);
        if(memberOptional.isEmpty()){
            log.info("memberUUID = {}인 회원은 존재하지 않습니다.", memberUUID);
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        foodService.updateFoodReview(dto);

        review.setFood(foodOptional.get());
        review.setMember(memberOptional.get());

        Review saved = reviewRepository.save(review);
        ReviewInfoResponse reviewInfoResponse = mapper.map(saved, ReviewInfoResponse.class);

        reviewInfoResponse.setReviewUUID(review.getReviewUUID());
        reviewInfoResponse.setFoodUUID(foodUUID);
        reviewInfoResponse.setMemberUUID(memberUUID);

        return reviewInfoResponse;
    }

    public ReviewInfoResponses findAllByFoodUUID(final String foodUUID){
        Optional<Food> foodOptional = foodRepository.findByFoodUUID(foodUUID);

        if(foodOptional.isEmpty()){
            log.info("foodUUID = {}인 음식은 존재하지 않습니다.", foodUUID);
            throw new CustomException(ErrorCode.FOOD_NOT_FOUND);
        }

        Food food = foodOptional.get();
        List<Review> reviewList = reviewRepository.findAllByFoodOrderByCreatedAtDesc(food);

        List<ReviewInfoResponse> reviewInfoResponseList = reviewList.stream().map(
                (e) -> {
                    ReviewInfoResponse reviewInfoResponse = mapper.map(e, ReviewInfoResponse.class);
                    setUUID(reviewInfoResponse, e);
                    return reviewInfoResponse;
                }
        ).collect(Collectors.toList());

        return new ReviewInfoResponses(reviewInfoResponseList);
    }

    private void setUUID(ReviewInfoResponse reviewInfoResponse, Review review){
        String memberUUID = review.getMember().getMemberUUID();
        String foodUUID = review.getFood().getFoodUUID();

        reviewInfoResponse.setMemberUUID(memberUUID);
        reviewInfoResponse.setFoodUUID(foodUUID);
    }
}
