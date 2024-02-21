package com.example.cafeteriarecommend.auth.presentation;

import com.example.cafeteriarecommend.auth.exception.EmptyAuthorizationHeaderException;
import com.example.cafeteriarecommend.jwt.application.JwtService;
import com.example.cafeteriarecommend.jwt.exception.InvalidTokenException;
import com.example.cafeteriarecommend.member.application.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(request.getHeader(HttpHeaders.AUTHORIZATION) == null)
            throw new EmptyAuthorizationHeaderException();

        String accessToken = AuthorizationExtractor.extract(request);

        jwtService.validateToken(accessToken);
        Long id = jwtService.extractId(accessToken).orElseThrow(
                () -> {
                    throw new InvalidTokenException("토큰에 ID가 없습니다");
                }
        );

        return memberService.getById(id);
    }
}
