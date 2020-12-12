package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.repository.ChallengeRepository;
import com.technhongplus.sellengeapi.repository.JoinChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ChallengeScheduler {
    private final ChallengeRepository challengeRepository;
    private final JoinChallengeRepository joinChallengeRepository;
    @Scheduled(cron="0 0 0 * * ?")
    public void checkChallenge(){
        //날짜가 지난 챌린지 검색
        List<Challenge> challenges = challengeRepository.findAll().stream()
            .filter(challenge -> LocalDate.now().isBefore(challenge.getEndDate()))
            .collect(Collectors.toList());

        challenges.stream().forEach(challenge -> {
            //TODO 유저 별 성공 여부
        });
    }
}
