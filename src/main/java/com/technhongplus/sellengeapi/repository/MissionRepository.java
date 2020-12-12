package com.technhongplus.sellengeapi.repository;

import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findAllByJoinChallenge_ChallengeAndProofDate(Challenge challenge, LocalDateTime proofDate);
    Mission findByJoinChallenge_Id(Long joinChallengeOId);
}
