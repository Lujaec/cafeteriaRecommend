package com.example.cafeteriarecommend.auth.application;

import com.example.cafeteriarecommend.auth.presentation.dto.reqeust.LoginRequest;
import com.example.cafeteriarecommend.auth.presentation.dto.response.LoginResponse;
import com.example.cafeteriarecommend.jwt.application.JwtService;
import com.example.cafeteriarecommend.member.application.MemberService;
import com.example.cafeteriarecommend.member.application.dto.MemberCreateDto;
import com.example.cafeteriarecommend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final MemberService memberService;
    private final ModelMapper mapper;

    public LoginResponse socialLogin(final LoginRequest request){
        final String oauthId = request.getOauthId();
        Optional<Member> memberOptional = memberService.findByOauthId(oauthId);

        Member member = null;

        if (memberOptional.isEmpty())
            member = signUp(request);
        else
            member = memberOptional.get();

        String accessToken = jwtService.createAccessToken(member.getId());
        String memberUUID = member.getMemberUUID();

        return new LoginResponse(memberUUID, accessToken);
    }

    private Member signUp(final LoginRequest request){
        MemberCreateDto dto = mapper.map(request, MemberCreateDto.class);
        return memberService.create(dto);
    }
}
