package com.technhongplus.sellengeapi.dto.nh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see <a href="https://developers.nonghyup.com/guide/GU_1010">핀어카운트 직접발급</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinAccountDto {
    private NhApiHeader Header;
    @Builder.Default
    private String DrtrRgyn = "Y"; // 출금이체 등록여부
    private String BrdtBrno; // 생년월일/사업자번호
    @Builder.Default
    private String Bncd = "011"; // 은행코드 (농협 011, 상호은행 012)
    private String Acno; // 계좌번호

    private String Rgno; // 핀어카운트 조회용 등록 번호

    private String FinAcno; // 핀어카운트 번호

    public static FinAccountDto ofForCreate(NhApiHeader header, String birthdayOrBusinessNo, String accountNo) {
        return FinAccountDto.builder()
                .Header(header)
                .BrdtBrno(birthdayOrBusinessNo)
                .Acno(accountNo)
                .build();
    }

    public static FinAccountDto ofForGet(NhApiHeader header, String birthdayOrBusinessNo, String regNo) {
        return FinAccountDto.builder()
                .Header(header)
                .BrdtBrno(birthdayOrBusinessNo)
                .Rgno(regNo)
                .build();
    }
}
