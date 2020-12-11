package com.technhongplus.sellengeapi.dto;

import com.technhongplus.sellengeapi.entity.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinChallengeDto {
    private BigDecimal joinMoney;
    private PayMethod payMethod;
}
