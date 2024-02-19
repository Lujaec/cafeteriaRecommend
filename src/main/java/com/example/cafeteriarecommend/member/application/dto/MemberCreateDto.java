package com.example.cafeteriarecommend.member.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {
    private String name;
    private String email;
    private String oauthId;
}
