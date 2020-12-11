package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.dto.ChallengeDto;
import com.technhongplus.sellengeapi.dto.JoinChallengeDto;
import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.JoinChallenge;
import com.technhongplus.sellengeapi.entity.Member;
import com.technhongplus.sellengeapi.repository.ChallengeRepository;
import com.technhongplus.sellengeapi.repository.JoinChallengeRepository;
import com.technhongplus.sellengeapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final JoinChallengeRepository joinChallengeRepository;

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

        // TODO 결제 by joinDto
        JoinChallenge save = joinChallengeRepository.save(JoinChallenge.of(member, challenge));
        return save.getId();
    }

    /**
     * 전체 챌린지 조회
     */
    public List<ChallengeDto> findChallenge() {
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(ChallengeDto::from)
                .collect(Collectors.toList());
    }
}
