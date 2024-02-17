package com.example.cafeteriarecommend.food.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByFoodUUID(final String foodUUID);
    List<Food> findAllByCategory(final FoodCategory category);
}
