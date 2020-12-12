package com.technhongplus.sellengeapi.repository;


import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.JoinChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinChallengeRepository extends JpaRepository<JoinChallenge, Long> {
    JoinChallenge findByChallenge_IdAndMember_Id(Long challengeId, Long memberId);
    List<JoinChallenge> findAllByChallenge_Id(Long challengeId);

    List<JoinChallenge> findAllByChallengeIn(List<Challenge> challenges);

}
