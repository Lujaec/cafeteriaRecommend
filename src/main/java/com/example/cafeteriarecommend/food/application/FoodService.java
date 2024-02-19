package com.example.cafeteriarecommend.food.application;

import com.example.cafeteriarecommend.food.application.dto.FoodCreateDto;
import com.example.cafeteriarecommend.food.application.dto.FoodReadCondDto;
import com.example.cafeteriarecommend.food.domain.Food;
import com.example.cafeteriarecommend.food.domain.FoodCategory;
import com.example.cafeteriarecommend.food.domain.FoodRepository;
import com.example.cafeteriarecommend.food.presentation.dto.response.FoodInfoResponse;
import com.example.cafeteriarecommend.food.presentation.dto.response.FoodInfoResponses;
import com.example.cafeteriarecommend.global.exception.CustomException;
import com.example.cafeteriarecommend.global.error.ErrorCode;
import com.example.cafeteriarecommend.review.application.dto.ReviewCreateDto;
import com.example.cafeteriarecommend.utill.FileStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FileStore fileStore;
    private final ModelMapper mapper;

    public FoodInfoResponse create(FoodCreateDto dto){
        Food food = mapper.map(dto, Food.class);

        String imageUrl = null;
        if(dto.getCoverImage() != null) {
            imageUrl = fileStore.storeFile(dto.getCoverImage());
            food.setImageUrl(imageUrl);
        }

        String foodUUID = UUID.randomUUID().toString();
        food.setFoodUUID(foodUUID);

        Food savedFood = foodRepository.save(food);

        return mapper.map(savedFood, FoodInfoResponse.class);
    }

    public FoodInfoResponse findByFoodUUID(final String foodUUID){
        Optional<Food> foodOptional = foodRepository.findByFoodUUID(foodUUID);

        if(foodOptional.isEmpty()){
            throw new CustomException(ErrorCode.FOOD_NOT_FOUND);
        }

        Food food = foodOptional.get();
        return mapper.map(food, FoodInfoResponse.class);
    }

    public FoodInfoResponses findAllByCategoryAndPlace(final FoodReadCondDto dto, Pageable pageable){
        final FoodCategory foodCategory = FoodCategory.valueOf(dto.getCategory().toUpperCase());
        final String place = dto.getPlace();

        Page<Food> retPage = foodRepository.findAllByCategoryAndPlace(foodCategory, place, pageable);
        return convertFoodInfoResponses(retPage.getContent());
    }

    public FoodInfoResponse updateFoodReview(ReviewCreateDto dto){
        Double score = dto.getScore();

        Optional<Food> foodOptional = foodRepository.findByFoodUUID(dto.getFoodUUID());
        if(foodOptional.isEmpty()){
            throw new CustomException(ErrorCode.FOOD_NOT_FOUND);
        }

        Food food = foodOptional.get();

        Long reviewCnt = food.getReviewCnt();
        food.setReviewCnt(++reviewCnt);
        food.setRating((food.getRating() * (reviewCnt - 1) + score) / reviewCnt);

        FoodInfoResponse foodInfoResponse = mapper.map(food, FoodInfoResponse.class);
        return foodInfoResponse;
    }

    public FoodInfoResponses findTopRatingFood(){
        List<FoodCategory> foodCategories = new ArrayList<>(EnumSet.allOf(FoodCategory.class));
        List<Food> topFood = new ArrayList<>();

        for (FoodCategory foodCategory : foodCategories) {
            List<Food> foodOrderByRatingDesc = foodRepository.findAllByCategoryOrderByRatingDesc(foodCategory);

            if (!foodOrderByRatingDesc.isEmpty()){
                topFood.add(foodOrderByRatingDesc.get(0));
            }
        }

        return convertFoodInfoResponses(topFood);
    }

    private FoodInfoResponses findByCategory(final String category){
        FoodCategory foodCategory = FoodCategory.valueOf(category.toUpperCase());

        List<Food> foodList = foodRepository.findAllByCategory(foodCategory);
        return convertFoodInfoResponses(foodList);
    }
    private FoodInfoResponses convertFoodInfoResponses(final List<Food> foodList){
        List<FoodInfoResponse> foodInfoResponseList = foodList.stream().map(
                (e) -> mapper.map(e, FoodInfoResponse.class)
        ).collect(Collectors.toList());

        return new FoodInfoResponses(foodInfoResponseList);
    }
}
