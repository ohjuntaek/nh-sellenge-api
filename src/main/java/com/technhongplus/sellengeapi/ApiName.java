package com.technhongplus.sellengeapi;

public enum ApiName {

    P2PNVirtualAccountNumberRequest; // 투자금관리용가상계좌발급

    public static final String BASE_URL = "https://developers.nonghyup.com";

    public String getRequestUrl() {
        return BASE_URL + "/" + name() + ".nh";
    }
}
