package com.example.developers.repository;

import com.example.developers.domain.Member;
import com.example.developers.domain.MemberChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {
    List<Member> findAllByMember(Member member);
}
