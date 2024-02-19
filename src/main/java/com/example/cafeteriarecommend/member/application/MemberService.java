package com.example.cafeteriarecommend.member.application;

import com.example.cafeteriarecommend.global.error.ErrorCode;
import com.example.cafeteriarecommend.global.exception.CustomException;
import com.example.cafeteriarecommend.member.application.dto.MemberCreateDto;
import com.example.cafeteriarecommend.member.domain.Member;
import com.example.cafeteriarecommend.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    public Member create(MemberCreateDto dto){
        Member member = mapper.map(dto, Member.class);
        member.setMemberUUID(UUID.randomUUID().toString());

        return memberRepository.save(member);
    }
    public Member getById(Long id){
        Optional<Member> memberOptional = memberRepository.findById(id);

        if(memberOptional.isEmpty()){
            log.info("해당 id를 가진 회원이 없습니다. id = {}", id);
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return memberOptional.get();
    }

    public Optional<Member> findByOauthId(String oauthId){
        return memberRepository.findByOauthId(oauthId);
    }
}
