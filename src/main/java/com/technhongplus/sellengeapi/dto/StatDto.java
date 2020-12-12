package com.technhongplus.sellengeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatDto {
    private BigDecimal totalInvestMoney; // 현재까지 투자한 금액
    private Integer totalChallengeCount; // 전체 챌린지
    private Integer doingChallengeCount; // 진행중
    private Integer successCount; // 완료
    private Integer successRate; // 평균 성공률
}
