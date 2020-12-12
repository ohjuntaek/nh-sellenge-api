package com.technhongplus.sellengeapi.dto.nh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepaymenDto {
    private NhApiHeader Header;
    @Builder.Default
    private String P2pCmtmNo = "0000000000"; // P2P약정번호
    @Builder.Default
    private String ChidSqno = "0000000000"; // 자회사일련번호
    private Long LoanNo; // 대출번호
    private String Vran; // 계좌 번호
    private String RpaySumAmt; // 상환 합계금액
    @Builder.Default
    private String RpayYmd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); //상환 일자
    @Builder.Default
    private String DractOtlt = "출금"; // 출금계좌인자내용
    @Builder.Default
    private String MractOtlt = "입금"; // 입금계좌인자내용
    private List<InvestmentDto.Rec> rec;

    public static RepaymenDto of(
        Long LoanNo, // 대출번호
        String Vran, // (출금을 할) 가상계좌번호
        String RpaySumAmt, // 상환 합계금액
        String RpayAmt, // 상환 합계금액
        NhApiHeader header
    ) {
        return RepaymenDto.builder()
            .Header(header)
            .Vran(Vran)
            .LoanNo(LoanNo)
            .RpaySumAmt(RpaySumAmt)
            .rec(Collections.singletonList(new InvestmentDto.Rec(Vran, RpayAmt)))
            .build();
    }

}
