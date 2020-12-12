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
public class InvestmentDto {
    private NhApiHeader Header;
    @Builder.Default
    private String P2pCmtmNo = "0000000000"; // P2P약정번호
    @Builder.Default
    private String ChidSqno = "0000000000"; // 자회사일련번호
    private String SlctAmt; // 모집 금액
    @Builder.Default
    private Integer LonTrm = 12; // 대출기간
    private String InvSumAmt; // 투자합계금액
    @Builder.Default
    private String NewTrnsYn = "Y"; // 신규거래여부 'Y'
    private Long LoanNo; // 대출번호 (NOTE : 채번!)
    @Builder.Default
    private String Bncd = "011"; // 은행코드 (농협 011)
    private String BrwAcno; // 차입자계좌번호 (판매자 계좌번호)
    @Builder.Default
    private String Dpnm = "오준택"; // 예금주명
    @Builder.Default
    private String LonApcYmd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    @Builder.Default
    private String DractOtlt = "출금"; // 출금계좌인자내용
    @Builder.Default
    private String MractOtlt = "입금"; // 입금계좌인자내용
    private List<Rec> rec;

    private String InvAccAmt;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Rec {
        private String Vran; // 가상계좌번호
        private String InvAmt; // 투자금액
    }

    public static InvestmentDto of(
            Long LoanNo, // 대출번호
            String BrwAcno, // 판매자 계좌번호
            String Vran, // (출금을 할) 가상계좌번호
            String InvAmt, // 투자금액
            NhApiHeader header
    ) {
        return InvestmentDto.builder()
                .Header(header)
                .SlctAmt(InvAmt)
                .InvSumAmt(InvAmt)
                .LoanNo(LoanNo)
                .BrwAcno(BrwAcno)
                .rec(Collections.singletonList(new Rec(Vran, InvAmt)))
                .build();
    }
}
