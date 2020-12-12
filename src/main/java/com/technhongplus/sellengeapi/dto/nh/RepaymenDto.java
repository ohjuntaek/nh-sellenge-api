package com.technhongplus.sellengeapi.dto.nh;

import lombok.Builder;

import java.util.List;

public class RepaymenDto {
    private NhApiHeader Header;
    @Builder.Default
    private String P2pCmtmNo = "0000000000"; // P2P약정번호
    @Builder.Default
    private String ChidSqno = "0000000000"; // 자회사일련번호
    private Long LoanNo; // 대출번호
    private String Vran; // 계좌 번호
    private Long RpaySumAmt; // 상환 합계금액
    private String RpayYmd; //상환 일자
    @Builder.Default
    private String DractOtlt = "출금"; // 출금계좌인자내용
    @Builder.Default
    private String MractOtlt = "입금"; // 입금계좌인자내용
    private List<InvestmentDto.Rec> rec;



}
