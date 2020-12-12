package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.ApiName;
import com.technhongplus.sellengeapi.SellengeApiApplication;
import com.technhongplus.sellengeapi.dto.ChallengeDto;
import com.technhongplus.sellengeapi.dto.JoinChallengeDto;
import com.technhongplus.sellengeapi.dto.exception.JoinAlreadyException;
import com.technhongplus.sellengeapi.dto.nh.InvestmentDto;
import com.technhongplus.sellengeapi.dto.nh.NhApiHeader;
import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.JoinChallenge;
import com.technhongplus.sellengeapi.entity.Member;
import com.technhongplus.sellengeapi.repository.ChallengeRepository;
import com.technhongplus.sellengeapi.repository.JoinChallengeRepository;
import com.technhongplus.sellengeapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.technhongplus.sellengeapi.service.NhTransactionService.NH_API_SUCCESS;
import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {
    private final NhTransactionService nhTransactionService;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final JoinChallengeRepository joinChallengeRepository;
    private final RestTemplate restTemplate;

    /**
     * 판매자가 챌린지를 등록한다.
     * @return
     */
    @Transactional
    public Long registerChallenge(Long loginMemberId, ChallengeDto challengeDto) {
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(EntityNotFoundException::new);
        if (!member.getIsSeller()) {
            throw new IllegalArgumentException("판매자만 챌린지 등록 가능");
        }
        Challenge challenge = challengeDto.to(member);

        Challenge save = challengeRepository.save(challenge);
        return save.getId();
    }

    /**
     * 회원이 챌린지에 참가한다.
     */
    @Transactional
    public Long joinChallenge(Long loginMemberId, Long challengeId, JoinChallengeDto joinChallengeDto) {
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(EntityNotFoundException::new);
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(EntityNotFoundException::new);

        JoinChallenge joinChallenge = JoinChallenge.of(member, challenge);
        JoinChallenge find = joinChallengeRepository.findByChallenge_IdAndMember_Id(challengeId, loginMemberId);
        if (find != null) {
            throw new JoinAlreadyException();
        }
        JoinChallenge save = joinChallengeRepository.save(joinChallenge);

        // 투자금 지급 지시
        String sellerAccountNo = challenge.getSeller().getAccountNo();
        String memberVirtualAccountNo = member.getVirtualAccount();
        String invAmt = joinChallengeDto.getJoinMoney().toString();

        HttpHeaders httpHeaders = SellengeApiApplication.getHttpHeaders();
        NhApiHeader apiHeader = nhTransactionService.buildApiHeader(ApiName.P2PNInvestmentPaymentOrder.name());

        Long loanNo = nhTransactionService.getCountNhTx();
        InvestmentDto investmentDto = InvestmentDto.of(
                loanNo,
                sellerAccountNo,
                memberVirtualAccountNo,
                invAmt,
                apiHeader
        );
        HttpEntity<InvestmentDto> request = new HttpEntity<>(investmentDto, httpHeaders);
        InvestmentDto response = restTemplate.postForObject(ApiName.P2PNInvestmentPaymentOrder.getUri(), request, InvestmentDto.class);

        if (!response.getHeader().getRpcd().equals(NH_API_SUCCESS)) {
            throw new IllegalStateException(response.getHeader().getRsms());
        }

        return save.getId();
    }

    /**
     * 전체 챌린지 조회
     */
    public List<ChallengeDto> findChallenge() {
        List<Challenge> challenges = challengeRepository.findAll();
        List<JoinChallenge> joinChallenges = joinChallengeRepository.findAllByChallengeIn(challenges);
        Map<Challenge, List<JoinChallenge>> map = joinChallenges.stream()
                .collect(groupingBy(JoinChallenge::getChallenge));

        challenges.forEach(e ->
                e.setCount(map.getOrDefault(e, Collections.emptyList()).size()));
        return challenges.stream()
                .map(ChallengeDto::from)
                .collect(Collectors.toList());
    }
}
