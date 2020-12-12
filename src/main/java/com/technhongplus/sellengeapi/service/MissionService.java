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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final ChallengeRepository challengeRepository;
    private final MissionRepository missionRepository;
    private final JoinChallengeRepository joinChallengeRepository;

    @Transactional
    public List<MissionDto> getMission(Long challengeId, LocalDateTime date){
        Challenge challenge = challengeRepository.findById(challengeId).get();
        List<Mission> missionList = missionRepository.findAllByJoinChallenge_ChallengeAndProofDate(challenge,date);
        return missionList.stream().map(MissionDto::from).collect(Collectors.toList());
    }

    @Transactional
    public void registerMission(Long loginMemberId, MissionDto missionDto){
        JoinChallenge joinChallenge = joinChallengeRepository.findById(missionDto.getChallengeId()).get();
        missionRepository.save(Mission.of(joinChallenge,missionDto.getImageUrl(),missionDto.getProofDate()));
    }

    @Transactional
    public void certifiedMission(Long loginMemberId, Long challengeId, boolean success){
        JoinChallenge joinChallenge = joinChallengeRepository.findByChallenge_IdAndMember_Id(challengeId,loginMemberId);
        Mission mission = missionRepository.findByJoinChallenge_Id(joinChallenge.getId());
        mission.setSuccess(success);
        missionRepository.save(mission);
    }
}
