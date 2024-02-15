package com.example.cafeteriarecommend.food.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;
    private String name;
    private String price;
    private String place;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;
    private Double rating;
    private String foodUUID;
}
