package com.technhongplus.sellengeapi.repository;

import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findAllByJoinChallenge_ChallengeAndProofDate(Challenge challenge, LocalDate proofDate);
    Mission findByJoinChallenge_IdAndProofDate(Long joinChallengeOId,LocalDate date);
    List<Mission> findAllByJoinChallenge_idAndSuccess(Long joinChallengeOId, boolean success);
}
