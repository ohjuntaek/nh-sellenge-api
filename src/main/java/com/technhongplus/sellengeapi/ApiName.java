package com.technhongplus.sellengeapi;

import java.net.URI;

public enum ApiName {

    P2PNVirtualAccountNumberRequest, // 투자금관리용가상계좌발급
    OpenFinAccountDirect, // 핀-어카운트 직접발급
    CheckOpenFinAccountDirect, // 핀어카운트 직접발급 확인
    P2PNInvestmentPaymentOrder // 투자금지급지시
    ;


    public static final String BASE_URL = "https://developers.nonghyup.com";

    public String getRequestUrl() {
        return BASE_URL + "/" + name() + ".nh";
    }

    public URI getUri() {
        return URI.create(getRequestUrl());
    }
}
