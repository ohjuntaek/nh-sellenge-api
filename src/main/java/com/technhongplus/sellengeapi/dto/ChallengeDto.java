package com.technhongplus.sellengeapi.dto;

import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.entity.ChallengeCategory;
import com.technhongplus.sellengeapi.entity.ChallengeCycle;
import com.technhongplus.sellengeapi.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDto {
    private Long id;
    private ChallengeCategory category; // 카테고리
    private String repImageUrl; // 대표이미지url
    private String itemName; // 상품명
    private String challengeName; // 챌린지명
    private LocalDate startDate; // 챌린지 시작일
    private LocalDate endDate; // 챌린지 종료일
    private ChallengeCycle challengeCycle; // 미션주기
    private String missionContent; // 미션 내용
    private BigDecimal minJoinMoney; // 참여최소금액
    private Integer minMemberCount; // 최소인원
    private Integer maxMemberCount; // 최대인원
    private String challengeDetailContent; // 챌린지상세내용
    private List<String> attachUrls; // 첨부이미지url
    private String successStandard; // 인증성공기준설명
    private List<String> successStandardImageUrls; // 인증성공기준이미지url
    private String failStandard; // 인증실패기준설명
    private List<String> failStandardImageUrls; // 인증성공기준이미지url
    private Integer count; // 참여자수

    public static ChallengeDto from(Challenge challenge) {
        return ChallengeDto.builder()
                .id(challenge.getId())
                .category(challenge.getCategory())
//                .member(challenge.getMember())
                .repImageUrl(challenge.getRepImageUrl())
                .itemName(challenge.getItemName())
                .challengeName(challenge.getChallengeName())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .challengeCycle(challenge.getChallengeCycle())
                .missionContent(challenge.getMissionContent())
                .minJoinMoney(challenge.getMinJoinMoney())
                .minMemberCount(challenge.getMinMemberCount())
                .maxMemberCount(challenge.getMaxMemberCount())
                .challengeDetailContent(challenge.getChallengeDetailContent())
                .attachUrls(challenge.getAttachUrls())
                .successStandard(challenge.getSuccessStandard())
                .successStandardImageUrls(challenge.getSuccessStandardImageUrls())
                .failStandard(challenge.getFailStandard())
                .failStandardImageUrls(challenge.getFailStandardImageUrls())
                .count(challenge.getCount())
                .build();
    }

    public Challenge to(Member member) {
        return Challenge.builder()
                .id(id)
                .category(category)
                .seller(member)
                .repImageUrl(repImageUrl)
                .itemName(itemName)
                .challengeName(challengeName)
                .startDate(startDate)
                .endDate(endDate)
                .challengeCycle(challengeCycle)
                .missionContent(missionContent)
                .minJoinMoney(minJoinMoney)
                .minMemberCount(minMemberCount)
                .maxMemberCount(maxMemberCount)
                .challengeDetailContent(challengeDetailContent)
                .attachUrls(attachUrls)
                .successStandard(successStandard)
                .successStandardImageUrls(successStandardImageUrls)
                .failStandard(failStandard)
                .failStandardImageUrls(failStandardImageUrls)
                .build();
    }
}
