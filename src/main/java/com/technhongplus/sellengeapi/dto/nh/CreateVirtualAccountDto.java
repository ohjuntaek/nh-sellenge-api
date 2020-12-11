package com.technhongplus.sellengeapi.dto.nh;

import lombok.*;

/**
 * @see <a href="https://developers.nonghyup.com/guide/GU_4010">투자금관리용 가상계좌발급</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateVirtualAccountDto {
    private NhApiHeader Header;
    private String P2PCmtmNo; // P2P약정번호
    private String ChidSqno; // 자회사일련번호
    private String P2PVractUsg; // P2P가상계좌 구분코드 (1:투자자용, 2:차입자용)
    private String InvstBrwNm; // 투자자및차입자명
    // 응답
    private String Vran; // 가상계좌번호
}