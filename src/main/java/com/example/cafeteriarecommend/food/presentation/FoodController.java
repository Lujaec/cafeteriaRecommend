package com.example.cafeteriarecommend.food.presentation;

import com.example.cafeteriarecommend.food.application.FoodService;
import com.example.cafeteriarecommend.food.application.dto.FoodCreateDto;
import com.example.cafeteriarecommend.food.application.dto.FoodReadCondDto;
import com.example.cafeteriarecommend.food.presentation.dto.request.FoodCreateRequest;
import com.example.cafeteriarecommend.food.presentation.dto.request.FoodReadRequest;
import com.example.cafeteriarecommend.food.presentation.dto.response.FoodInfoResponse;
import com.example.cafeteriarecommend.food.presentation.dto.response.FoodInfoResponses;
import com.example.cafeteriarecommend.utill.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final ModelMapper mapper;

    @PostMapping()
    ResponseEntity<ApiResponse<FoodInfoResponse>> create(
            @Valid @ModelAttribute FoodCreateRequest request
            ){
        FoodCreateDto dto = mapper.map(request, FoodCreateDto.class);
        FoodInfoResponse foodInfoResponse = foodService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(foodInfoResponse));
    }

    @GetMapping("{foodUUID}")
    ResponseEntity<ApiResponse<FoodInfoResponse>> readByUUID(
            @PathVariable final String foodUUID
    ){
        FoodInfoResponse foodInfoResponse = foodService.findByFoodUUID(foodUUID);
        return ResponseEntity.ok(ApiResponse.success(foodInfoResponse));
    }

    @GetMapping()
    ResponseEntity<ApiResponse<FoodInfoResponses>> readByCategory(
            @ModelAttribute FoodReadRequest request,
            Pageable pageable
    ){
        FoodReadCondDto dto = mapper.map(request, FoodReadCondDto.class);
        FoodInfoResponses foodInfoResponses = foodService.findAllByCategoryAndPlace(dto, pageable);
        return ResponseEntity.ok(ApiResponse.success(foodInfoResponses));
    }

    //pagenation
}
