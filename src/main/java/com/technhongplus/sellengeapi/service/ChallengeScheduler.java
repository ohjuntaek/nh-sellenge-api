package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.ApiName;
import com.technhongplus.sellengeapi.SellengeApiApplication;
import com.technhongplus.sellengeapi.dto.nh.NhApiHeader;
import com.technhongplus.sellengeapi.dto.nh.RepaymenDto;
import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.JoinChallenge;
import com.technhongplus.sellengeapi.entity.Mission;
import com.technhongplus.sellengeapi.repository.ChallengeRepository;
import com.technhongplus.sellengeapi.repository.JoinChallengeRepository;
import com.technhongplus.sellengeapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ChallengeScheduler {
    private final NhTransactionService nhTransactionService;
    private final ChallengeRepository challengeRepository;
    private final JoinChallengeRepository joinChallengeRepository;
    private final MissionRepository missionRepository;
    private final RestTemplate restTemplate;

    @Scheduled(cron="0 0 0 * * ?")
    public void checkChallenge(){
        //날짜가 지난 챌린지 검색
        List<Challenge> challenges = challengeRepository.findAll().stream()
            .filter(challenge -> LocalDate.now().isBefore(challenge.getEndDate()))
            .collect(Collectors.toList());

        challenges.stream().forEach(challenge -> {
            long days = ChronoUnit.DAYS.between(challenge.getStartDate(), challenge.getEndDate());
            List<JoinChallenge> joinChallenges = joinChallengeRepository.findAllByChallenge_Id(challenge.getId());
            //해당 challenge의 참여자
            joinChallenges.stream().forEach(joinChallenge -> {
                List<Mission> missions = missionRepository.findAllByJoinChallenge_idAndSuccess(joinChallenge.getId(),true);
                String sellerAccountNo = challenge.getSeller().getAccountNo();
                String invAmt = joinChallenge.getJoinAmount().toString();

                HttpHeaders httpHeaders = SellengeApiApplication.getHttpHeaders();
                NhApiHeader apiHeader = nhTransactionService.buildApiHeader(ApiName.P2PNInterestRepayment.name());
                Long loanNo = nhTransactionService.getCountNhTx();
                if(missions.size() >= days){
                    //100% 미션 성공금 지급
                    RepaymenDto repaymenDto = RepaymenDto.of(
                        loanNo,
                        sellerAccountNo,
                        invAmt,
                        invAmt,
                        apiHeader
                    );
                    HttpEntity<RepaymenDto> request = new HttpEntity<>(repaymenDto, httpHeaders);
                    restTemplate.postForObject(ApiName.P2PNInterestRepayment.getUri(), request, RepaymenDto.class);
                }else if(missions.size() == days*0.8){
                    //80% 미션 성공금 지급
                    double inv = Integer.parseInt(invAmt)*0.8;
                    RepaymenDto repaymenDto = RepaymenDto.of(
                        loanNo,
                        sellerAccountNo,
                        invAmt,
                        Double.toString(inv),
                        apiHeader
                    );
                    HttpEntity<RepaymenDto> request = new HttpEntity<>(repaymenDto, httpHeaders);
                    restTemplate.postForObject(ApiName.P2PNInterestRepayment.getUri(), request, RepaymenDto.class);
                }
            });

        });
    }
}
