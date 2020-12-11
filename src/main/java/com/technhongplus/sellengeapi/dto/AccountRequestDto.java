package com.technhongplus.sellengeapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountApiDto {
    ApiHeaderDto Header;
    String P2PCmtmNo =  "0000000000";
    String ChidSqno = "0000000000";
    int P2PVractUsg = 1;
    String InvstBrwNm = "이예린";

    public AccountApiDto(){
        Header = new ApiHeaderDto();
    }
}
