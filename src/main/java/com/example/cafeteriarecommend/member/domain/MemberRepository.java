package com.example.cafeteriarecommend.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberUUID(final String memberUUID);
    Optional<Member> findById(final Long id);

    Optional<Member> findByOauthId(final String oauthId);
}
