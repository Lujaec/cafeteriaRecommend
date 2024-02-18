package com.example.cafeteriarecommend.review.domain;

import com.example.cafeteriarecommend.food.domain.Food;
import com.example.cafeteriarecommend.global.BaseEntity;
import com.example.cafeteriarecommend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String reviewUUID;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="food_id")
    private Food food;
}
