package com.technhongplus.sellengeapi.repository;


import com.technhongplus.sellengeapi.entity.JoinChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinChallengeRepository extends JpaRepository<JoinChallenge, Long> {
    JoinChallenge findByChallenge_IdAndMember_Id(Long challengeId, Long memberId);
}
