package com.example.cafeteriarecommend.member.domain;

import com.example.cafeteriarecommend.review.domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String oauthId;
    private String memberUUID;
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();
}
