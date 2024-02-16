package com.example.cafeteriarecommend.food.application;

import com.example.cafeteriarecommend.food.application.dto.FoodCreateDto;
import com.example.cafeteriarecommend.food.domain.Food;
import com.example.cafeteriarecommend.food.domain.FoodRepository;
import com.example.cafeteriarecommend.food.presentation.dto.response.FoodInfoResponse;
import com.example.cafeteriarecommend.global.exception.CustomException;
import com.example.cafeteriarecommend.global.error.ErrorCode;
import com.example.cafeteriarecommend.utill.FileStore;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
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
}