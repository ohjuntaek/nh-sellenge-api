package com.technhongplus.sellengeapi.dto.nh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @see <a href="https://developers.nonghyup.com/guide/GU_1000">공통부</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NhApiHeader {
    private String ApiNm; // api 명
    @Builder.Default
    private String Tsymd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 전송일자
    @Builder.Default
    private String Trtm = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")); // 전송시각
    private String Iscd; // 기관코드
    @Builder.Default
    private String FintechApsno = "001"; // 핀테크 앱 일련번호 (고정)
    @Builder.Default
    private String ApiSvcCd = "DrawingTransferA"; // API서비스코드 (고정)
    private Long IsTuno; // 기관거래고유번호
    private String AccessToken; // 인증키
    // 응답
    private String Rsms; // 응답메세지
    private String Rpcd; // 응답코드

    public static NhApiHeader of(
            String apiNm, // api 명
            String iscd, // 기관코드
            Long isTuno, // 기관거래고유번호
            String accessToken // 인증키
    ) {
        return NhApiHeader.builder()
                .ApiNm(apiNm)
//                .Tsymd()
//                .Trtm()
                .Iscd(iscd)
//                .FintechApsno()
//                .ApiSvcCd()
                .IsTuno(isTuno)
                .AccessToken(accessToken)
                .build();
    }

}