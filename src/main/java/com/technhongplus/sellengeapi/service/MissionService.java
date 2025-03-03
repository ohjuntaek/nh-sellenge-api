package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.dto.MissionDto;
import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.JoinChallenge;
import com.technhongplus.sellengeapi.entity.Mission;
import com.technhongplus.sellengeapi.repository.ChallengeRepository;
import com.technhongplus.sellengeapi.repository.JoinChallengeRepository;
import com.technhongplus.sellengeapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final ChallengeRepository challengeRepository;
    private final MissionRepository missionRepository;
    private final JoinChallengeRepository joinChallengeRepository;

    @Transactional
    public List<MissionDto> getMission(Long challengeId, LocalDate date){
        Challenge challenge = challengeRepository.findById(challengeId).get();
        List<Mission> missionList = missionRepository.findAllByJoinChallenge_ChallengeAndProofDate(challenge,date);
        return missionList.stream().map(MissionDto::from).collect(Collectors.toList());
    }

    @Transactional
    public Long registerMission(Long loginMemberId, MissionDto missionDto){
        JoinChallenge joinChallenge = joinChallengeRepository.findByChallenge_IdAndMember_Id(missionDto.getChallengeId(),loginMemberId);
        if (joinChallenge == null) throw new IllegalArgumentException("등록하지 않은 챌린지 입니다.");

        return missionRepository.save(Mission.of(joinChallenge,missionDto.getImageUrl(),missionDto.getProofDate())).getId();
    }

    @Transactional
    public Long certifiedMission(Long loginMemberId, Long challengeId, boolean success, LocalDate date){
        JoinChallenge joinChallenge = joinChallengeRepository.findByChallenge_IdAndMember_Id(challengeId,loginMemberId);
        if (joinChallenge == null) throw new IllegalArgumentException("등록하지 않은 챌린지 입니다.");

        Mission mission = missionRepository.findByJoinChallenge_IdAndProofDate(joinChallenge.getId(),date);
        mission.setSuccess(success);
        return missionRepository.save(mission).getId();
    }
}
