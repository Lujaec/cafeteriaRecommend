package com.example.cafeteriarecommend.auth.presentation.dto.reqeust;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String name;
    private String oauthId;
    private String email;
}
