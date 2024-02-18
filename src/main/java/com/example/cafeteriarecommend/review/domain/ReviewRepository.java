package com.example.cafeteriarecommend.review.domain;

import com.example.cafeteriarecommend.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByFood(Food food);
    List<Review> findAllByFoodOrderByCreatedAtDesc(Food food);
}
