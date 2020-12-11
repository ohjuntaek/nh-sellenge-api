package com.technhongplus.sellengeapi.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "CHALLENGE")
public class Challenge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String repImageUrl; // 대표이미지url
    private String itemName; // 상품명
    private String challengeName; // 챌린지명
    private LocalDate startDate; // 챌린지 시작일
    private LocalDate endDate; // 챌린지 종료일

    @Enumerated(EnumType.STRING)
    private ChallengeCycle challengeCycle; // 미션주기
    private String missionContent; // 미션 내용
    private BigDecimal minJoinMoney; // 참여최소금액
    private Integer minMemberCount; // 최소인원
    private Integer maxMemberCount; // 최대인원
    private String challengeDetailContent; // 챌린지상세내용

    @Convert(converter = ImageUrlsConverter.class)
    private List<String> attachUrls; // 첨부이미지url

    private String successStandard; // 인증성공기준설명

    @Convert(converter = ImageUrlsConverter.class)
    private List<String> successStandardImageUrls; // 인증성공기준이미지url
    private String failStandard; // 인증실패기준설명

    @Convert(converter = ImageUrlsConverter.class)
    private List<String> failStandardImageUrls; // 인증성공기준이미지url
}
