package com.example.cafeteriarecommend.auth.presentation;

import com.example.cafeteriarecommend.auth.application.AuthService;
import com.example.cafeteriarecommend.auth.presentation.dto.reqeust.LoginRequest;
import com.example.cafeteriarecommend.auth.presentation.dto.response.LoginResponse;
import com.example.cafeteriarecommend.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService oauthService;
    @PostMapping("/login")
    ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request
    ){
        LoginResponse loginResponse = oauthService.socialLogin(request);
        return ResponseEntity.ok(ApiResponse.success(loginResponse));
    }
}
