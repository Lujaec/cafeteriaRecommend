package com.example.cafeteriarecommend.auth.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String memberUUID;
    private String authToken;

    public LoginResponse(String memberUUID, String authToken) {
        this.memberUUID = memberUUID;
        this.authToken = authToken;
    }
}
